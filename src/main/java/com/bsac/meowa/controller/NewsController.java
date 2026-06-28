package com.bsac.meowa.controller;

import com.bsac.meowa.model.Images;
import com.bsac.meowa.model.News;
import com.bsac.meowa.repo.ImagesRepository;
import com.bsac.meowa.repo.NewsRepository;
import com.bsac.meowa.repo.SourceRepository;
import com.bsac.meowa.repo.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/news")
@CrossOrigin
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final ImagesRepository imagesRepository;
    private final CountryRepository countryRepository; // добавить

    @GetMapping
    public List<Map<String, Object>> getNews() {
        List<News> newsList = newsRepository.findAllByOrderByPubTDesc();
        List<Map<String, Object>> result = new ArrayList<>();

        for (News news : newsList) {
            Map<String, Object> item = new HashMap<>();
            item.put("newsId", news.getNewsId());
            item.put("title", news.getTitle());
            item.put("snippet", news.getContent());
            item.put("pubT", news.getPubT());
            item.put("articleUrl", news.getArticleUrl());
            item.put("category", news.getCategory());

            sourceRepository.findById(news.getSourceId()).ifPresent(source -> {
                item.put("sourceName", source.getSourceName());
            });

            // Добавить страну
            countryRepository.findById(news.getCountryId()).ifPresent(country -> {
                item.put("countryCode", country.getCountryCode());
                item.put("countryName", country.getCountryName());
            });

            List<Images> images = imagesRepository.findByNewsId(news.getNewsId());
            if (!images.isEmpty()) {
                item.put("imageUrl", images.get(0).getImageUrl());
            }

            result.add(item);
        }
        return result;
    }
}