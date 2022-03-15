package com.example.currencyexchange.currency;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(path = "/api/exchange-rates/{currencyCode}")
    @GetMapping
    public Double getCurrencyPrice(@PathVariable String currencyCode) {
        return currencyService.getCurrencyPrice(currencyCode);
    }
}
