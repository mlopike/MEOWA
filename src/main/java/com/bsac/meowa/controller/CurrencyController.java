package com.bsac.meowa.controller;

import com.bsac.meowa.model.BranchRate;
import com.bsac.meowa.service.CurrencyService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/kurs")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public String fetch() {
        return currencyService.fetchRates();
    }

    @GetMapping("/api")
    @ResponseBody
    public List<BranchRate> api() {
        return currencyService.getAllRates();
    }

    @GetMapping
    public String page(Model model) {
        List<BranchRate> rates = currencyService.getAllRates();

        Double avgUsdIn = rates.stream()
                .filter(r -> r.getUsdIn() != null)
                .mapToDouble(BranchRate::getUsdIn)
                .average()
                .orElse(0);

        Double avgUsdOut = rates.stream()
                .filter(r -> r.getUsdOut() != null)
                .mapToDouble(BranchRate::getUsdOut)
                .average()
                .orElse(0);

        Double avgEurIn = rates.stream()
                .filter(r -> r.getEurIn() != null)
                .mapToDouble(BranchRate::getEurIn)
                .average()
                .orElse(0);

        Double avgEurOut = rates.stream()
                .filter(r -> r.getEurOut() != null)
                .mapToDouble(BranchRate::getEurOut)
                .average()
                .orElse(0);

        Double avgRubIn = rates.stream()
                .filter(r -> r.getRubIn() != null)
                .mapToDouble(BranchRate::getRubIn)
                .average()
                .orElse(0);

        Double avgRubOut = rates.stream()
                .filter(r -> r.getRubOut() != null)
                .mapToDouble(BranchRate::getRubOut)
                .average()
                .orElse(0);

        LocalDateTime lastUpdate = rates.stream()
                .map(BranchRate::getTime)
                .filter(t -> t != null)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        model.addAttribute("totalBranches", rates.size());
        model.addAttribute("avgUsdIn", String.format("%.4f", avgUsdIn));
        model.addAttribute("avgUsdOut", String.format("%.4f", avgUsdOut));
        model.addAttribute("avgEurIn", String.format("%.4f", avgEurIn));
        model.addAttribute("avgEurOut", String.format("%.4f", avgEurOut));
        model.addAttribute("avgRubIn", String.format("%.4f", avgRubIn));
        model.addAttribute("avgRubOut", String.format("%.4f", avgRubOut));
        model.addAttribute("lastUpdate", lastUpdate);

        return "kurs";
    }
}