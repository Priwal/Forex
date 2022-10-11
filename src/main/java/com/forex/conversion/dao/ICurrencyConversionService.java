package com.forex.conversion.dao;

import com.forex.conversion.entity.Currency;

import java.util.List;

public interface ICurrencyConversionService {

    List<Currency> findAll();

    Double findExchangeRate(String currencySymbol);

}
