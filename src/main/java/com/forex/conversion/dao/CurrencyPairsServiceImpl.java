package com.forex.conversion.dao;

import com.forex.conversion.entity.CurrencyPair;
import com.forex.conversion.repository.CurrencyPairsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CurrencyPairsServiceImpl implements ICurrencyPairsService {

    @Autowired
    CurrencyPairsRepository currencyPairsRepository;

    @Override
    public List<CurrencyPair> getTopNPairs(Integer n) {
        return currencyPairsRepository.getEntitiesByLimit(n);
    }

    @Override
    public void createOrUpdatePairCount(String currency1, String currency2) {
        CurrencyPair currencyPair1 = currencyPairsRepository.findFirstByCurrency1AndCurrency2(currency1, currency2);
        CurrencyPair currencyPair2 = currencyPairsRepository.findFirstByCurrency1AndCurrency2(currency2, currency1);

        if (Objects.nonNull(currencyPair1)) {
            currencyPair1.setCount(currencyPair1.getCount() + 1);
            currencyPairsRepository.save(currencyPair1);
        } else if (Objects.nonNull(currencyPair2)) {
            currencyPair2.setCount(currencyPair2.getCount() + 1);
            currencyPairsRepository.save(currencyPair2);
        } else {
            CurrencyPair currencyPair = new CurrencyPair();
            currencyPair.setCurrency1(currency1);
            currencyPair.setCurrency2(currency2);
            currencyPair.setCount(1);
            currencyPairsRepository.save(currencyPair);
        }
    }
}
