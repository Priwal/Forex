package com.forex.conversion.service;

import com.forex.conversion.entity.Currency;
import com.forex.conversion.entity.CurrencyPair;

import java.util.List;
import java.util.Optional;

public interface IForexConversionService {

    List<Currency> getSupportedCurrencies();

    String getConversionResult(String input);

    List<CurrencyPair> getTopNPairs(Integer n);
}
