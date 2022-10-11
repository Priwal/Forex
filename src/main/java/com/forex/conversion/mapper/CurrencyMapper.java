package com.forex.conversion.mapper;

import com.forex.conversion.dto.CurrencyDto;
import com.forex.conversion.entity.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

    public Currency getMappedCurrency(CurrencyDto currencyDto)
    {
        return Currency.builder()
                .currencyName(currencyDto.getCurrencyName())
                .currencySymbol(currencyDto.getCurrencySymbol())
                .exchangeRate(currencyDto.getExchangeRate())
                .build();
    }
}
