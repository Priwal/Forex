package com.forex.conversion.dao;

import com.forex.conversion.entity.Currency;
import com.forex.conversion.repository.CurrencyConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyConversionServiceImpl implements ICurrencyConversionService {

    @Autowired
    CurrencyConversionRepository currencyConversionRepository;

    @Override
    public List<Currency> findAll() {
        return currencyConversionRepository.findAll();
    }

    @Override
    public Double findExchangeRate(String currencySymbol) {
        Currency currency = currencyConversionRepository.findByCurrencySymbol(currencySymbol);
        double rate = currency.getExchangeRate();
        return rate;
    }

}
