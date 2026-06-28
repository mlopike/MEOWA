package com.bsac.meowa.service;

import com.bsac.meowa.model.BranchRate;
import com.bsac.meowa.repo.BranchRateRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {

    private final BranchRateRepository repo;
    private final RestTemplate restTemplate;

    public CurrencyService(BranchRateRepository repo) {
        this.repo = repo;
        this.restTemplate = new RestTemplate();  // ← Создаем прямо здесь
    }

    public String fetchRates() {
        String url = "https://belarusbank.by/api/kursExchange";
        
        BranchRate[] response = restTemplate.getForObject(url, BranchRate[].class);
        
        if (response == null) {
            return "No data received";
        }

        List<BranchRate> list = Arrays.asList(response);
        LocalDateTime now = LocalDateTime.now();
        
        list.forEach(r -> r.setTime(now));

        repo.deleteAll();
        repo.saveAll(list);

        return "Saved: " + list.size() + " rates";
    }

    public List<BranchRate> getAllRates() {
        return repo.findAll();
    }
}