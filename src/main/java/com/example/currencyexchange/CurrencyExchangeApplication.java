package com.example.currencyexchange;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@RestController
public class CurrencyExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}

	@RequestMapping(path = "/api/exchange-rates/{currencyCode}")
	@GetMapping
	public String currency(@PathVariable String currencyCode) {
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

				return avgPrinces.toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(path = "/api/gold-price/average")
	@GetMapping
	public String gold() {
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

				return avgPrinces.toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
