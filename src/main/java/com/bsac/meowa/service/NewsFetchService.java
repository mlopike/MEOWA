package com.bsac.meowa.service;

import com.bsac.meowa.dto.NewsDataResponse;
import com.bsac.meowa.model.Country;
import com.bsac.meowa.model.Images;
import com.bsac.meowa.model.News;
import com.bsac.meowa.model.Source;
import com.bsac.meowa.repo.CountryRepository;
import com.bsac.meowa.repo.ImagesRepository;
import com.bsac.meowa.repo.NewsRepository;
import com.bsac.meowa.repo.SourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NewsFetchService {

    private static final Logger log = LoggerFactory.getLogger(NewsFetchService.class);
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final WebClient webClient;
    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final CountryRepository countryRepository;
    private final ImagesRepository imagesRepository;

    @Value("${newsdata.api.key}")
    private String apiKey;

    public NewsFetchService(NewsRepository newsRepository,
                            SourceRepository sourceRepository,
                            CountryRepository countryRepository,
                            ImagesRepository imagesRepository) {
        this.newsRepository = newsRepository;
        this.sourceRepository = sourceRepository;
        this.countryRepository = countryRepository;
        this.imagesRepository = imagesRepository;
        this.webClient = WebClient.builder()
            .baseUrl("https://newsdata.io")
            .build();
    }

    public void fetchAndSave() {
    log.info("Fetching news...");
    try {
        // Берём все страны из БД
        List<Country> countries = countryRepository.findAll();
        
        for (Country country : countries) {
            fetchForCountry(country);
        }
    } catch (Exception e) {
        log.error("Error fetching news", e);
    }
}

private void fetchForCountry(Country country) {
    try {
        NewsDataResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/1/latest")
                .queryParam("apikey", apiKey)
                .queryParam("country", country.getCountryCode())
                .queryParam("language", "ru")
                .queryParam("category", "business,top")
                .build())
            .retrieve()
            .bodyToMono(NewsDataResponse.class)
            .block();

        if (response == null || response.getResults() == null) {
            log.warn("Empty response for country {}", country.getCountryCode());
            return;
        }

        for (NewsDataResponse.Article article : response.getResults()) {
            if (newsRepository.existsByExternalId(article.getArticleId())) {
                continue;
            }

            // Источник
            Source source = sourceRepository
                .findBySourceName(article.getSourceId())
                .orElseGet(() -> {
                    Source s = new Source();
                    s.setSourceName(article.getSourceId());
                    s.setSourceUrl(article.getSourceUrl());
                    s.setSourceIcon(article.getSourceIcon());
                    return sourceRepository.save(s);
                });

            // Новость
            News news = new News();
            news.setSourceId(source.getSourceId());
            news.setCountryId(country.getCountryId()); // берём из БД
            news.setTitle(article.getTitle());
            news.setContent(article.getDescription());
            news.setArticleUrl(article.getLink());
            news.setExternalId(article.getArticleId());
            news.setPubT(article.getPubDate() != null
                ? LocalDateTime.parse(article.getPubDate(), FORMATTER)
                : null);
            news.setAddT(LocalDateTime.now());
            news.setCategory(article.getCategory() != null && !article.getCategory().isEmpty()
                ? article.getCategory().get(0) : null);
            News savedNews = newsRepository.save(news);

            // Картинка
            if (article.getImageUrl() != null) {
                Images img = new Images();
                img.setNewsId(savedNews.getNewsId());
                img.setImageUrl(article.getImageUrl());
                img.setAddT(LocalDateTime.now());
                imagesRepository.save(img);
            }
        }

        log.info("Fetched news for country {}", country.getCountryCode());

    } catch (Exception e) {
        log.error("Error fetching news for country {}", 
            country.getCountryCode(), e);
    }
}
}