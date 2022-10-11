package com.forex.conversion.dao;

import com.forex.conversion.entity.CurrencyPair;

import java.util.List;

public interface ICurrencyPairsService {

    List<CurrencyPair> getTopNPairs(Integer n);
    void createOrUpdatePairCount(String currency1, String currency2);
}
