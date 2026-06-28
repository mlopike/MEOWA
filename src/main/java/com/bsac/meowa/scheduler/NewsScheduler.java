package com.bsac.meowa.scheduler;

import com.bsac.meowa.repo.NewsRepository;
import com.bsac.meowa.service.NewsFetchService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsFetchService service;
    private final NewsRepository newsRepository;

    @PostConstruct
    public void init() { service.fetchAndSave(); }

    @Scheduled(fixedDelay = 3_600_000)
    public void fetchNews() { service.fetchAndSave(); }

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanup() {
        newsRepository.deleteByPubTBefore(LocalDateTime.now().minusDays(30));
    }
}