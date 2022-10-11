package com.forex.conversion.mapper;

import com.forex.conversion.dto.CurrencyPairDto;
import com.forex.conversion.entity.CurrencyPair;
import org.springframework.stereotype.Component;

@Component
public class CurrencyPairMapper {

    public CurrencyPair getMappedCurrencyPair(CurrencyPairDto currencyPairDto)
    {
        return CurrencyPair.builder()
                .currency1(currencyPairDto.getCurrency1())
                .currency2(currencyPairDto.getCurrency2())
                .count(currencyPairDto.getCount())
                .build();
    }
}
