package com.forex.conversion.service;

import com.forex.conversion.dao.ICurrencyConversionService;
import com.forex.conversion.dao.ICurrencyPairsService;
import com.forex.conversion.entity.Currency;
import com.forex.conversion.entity.CurrencyPair;
import com.forex.conversion.exception.ForexConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ForexConversionServiceImpl implements IForexConversionService {

    public static final Set<String> validCurrencies = new HashSet<>(Arrays.asList("INR", "USD", "CAD", "CNY", "EUR", "HKD", "JPY", "VND", "CHF", "AUD", "KRW", "GBP"));
    @Autowired
    ICurrencyConversionService currencyConversionService;

    @Autowired
    ICurrencyPairsService currencyPairsService;

    @Override
    public List<Currency> getSupportedCurrencies() {
        return currencyConversionService.findAll();
    }

    @Override
    public String getConversionResult(String input) {
        String queries[] = input.split(",");
        String result = "";
        try {
            for (String query : queries) {
                String currResult = evaluateQuery(query);
                result += currResult + " ";

            }
        } catch (ForexConversionException e) {
            log.error("Forex_conversion_services :: Invalid input- {}, exception- {}", input, e);
        }
        return result;
    }

    @Override
    public List<CurrencyPair> getTopNPairs(Integer n) {
        return currencyPairsService.getTopNPairs(n);
    }


    private double convert(String currency1, String currency2, double amount) {
        //fetch rate from db and do computation
        double price = 0.0;
        try {
            double exchangeRate1 = currencyConversionService.findExchangeRate(currency1);
            double exchangeRate2 = currencyConversionService.findExchangeRate(currency2);


            currencyPairsService.createOrUpdatePairCount(currency1, currency2);
            double exchangeValue = exchangeRate2 / exchangeRate1;

            price = amount * exchangeValue;
            log.info("Forex_Conversion_Service :: Succesfully converted from currency1- {} to currency2- {} for amount- {}", currency1, currency2, amount);
        } catch (Exception e) {
            log.error("Forex_Conversion_Service :: Failed to convert from currency1- {} to currency2- {} for amount- {}, exception- {} ", currency1, currency2, amount, e);
        }

        return Math.round(price * 100d) / 100d;

    }

    private String evaluateQuery(String query) throws ForexConversionException {

        double default_value = 1.0;
        String result = "";


        String currency1 = query.substring(0, 3);

        if (!validCurrencies.contains(currency1)) {
            throw new ForexConversionException("Invalid fromCurrency");
        }
        String rem = query.substring(3);

        String[] toCurrencies = rem.split("(?<=\\G...)");

        String Symbols="";
        for (String toCurrency : toCurrencies) {
            if (isNumeric(toCurrency)) {
                default_value = Double.valueOf(toCurrency);

            }
            Symbols+= toCurrency;

        }
        if(Symbols.length()%3!=0)
        {
            throw new ForexConversionException("Invalid input");
        }

        for (String toCurrency : toCurrencies) {
            if (isNumeric(toCurrency)) {
                continue;
            }

            if (!validCurrencies.contains(toCurrency)) {
                throw new ForexConversionException("Invalid toCurrency");
            }
            double ans = convert(currency1, toCurrency, default_value);
            result += String.valueOf(ans) + " ";

        }
        return result;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
