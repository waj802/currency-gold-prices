package com.example.currencyexchange.gold;

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
public class GoldService {

    public Double getGoldPrice() {
        try {
            URL url = new URL("https://api.nbp.pl/api/cenyzlota/last/14/?format=json");
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

                JSONArray goldPrices = (JSONArray) parser.parse(json);
                Double avgPrinces = 0.0;
                List<Double> avgPrincesList = new ArrayList<Double>();
                System.out.println("Prices for the last 14 business days:");
                for (var price : goldPrices) {
                    JSONObject tempData = (JSONObject) price;
                    avgPrinces += Double.parseDouble(tempData.get("cena").toString());
                    avgPrincesList.add(Double.parseDouble(tempData.get("cena").toString()));
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
