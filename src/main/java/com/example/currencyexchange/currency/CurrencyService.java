package com.example.currencyexchange.currency;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyService {

    public Double getCurrencyPrice(String currencyCode) {
        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode.toLowerCase() + "/last/5/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();


            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String jsonLine;
                String json = "";
                while ((jsonLine = in.readLine()) != null) {
                    json = json.concat(jsonLine);
                }
                in.close();
                conn.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject jsonObj = (JSONObject) parser.parse(json);

                JSONArray currencyRates = (JSONArray) jsonObj.get("rates");
                Double avgPrinces = 0.0;
                List<Double> avgPrincesList = new ArrayList<Double>();
                System.out.println("Exchange rates for the last 5 business days:");
                for (var rate : currencyRates) {
                    JSONObject tempData = (JSONObject) rate;
                    avgPrinces += Double.parseDouble(tempData.get("mid").toString());
                    avgPrincesList.add(Double.parseDouble(tempData.get("mid").toString()));
                }
                System.out.println(avgPrincesList);
                avgPrinces = avgPrinces / avgPrincesList.size();

                return avgPrinces;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
