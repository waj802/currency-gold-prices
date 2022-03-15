package com.example.currencyexchange.currency;

public class Currency {

    private Double price;

    public Currency(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
