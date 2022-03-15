package com.example.currencyexchange.gold;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoldController {

    private final GoldService goldService;

    public GoldController(GoldService goldService) {
        this.goldService = goldService;
    }

    @RequestMapping(path = "/api/gold-price/average")
    @GetMapping
    public Double getGoldPrice() {
        return goldService.getGoldPrice();
    }
}
