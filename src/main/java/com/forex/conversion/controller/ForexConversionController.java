package com.forex.conversion.controller;

import com.forex.conversion.constant.ApiEndpointConstant;
import com.forex.conversion.entity.Currency;
import com.forex.conversion.entity.CurrencyPair;
import com.forex.conversion.service.IForexConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping(value = ApiEndpointConstant.FOREX_CONVERSION_BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ForexConversionController {

    @Autowired
    IForexConversionService forexConversionService;

    @RequestMapping(value = ApiEndpointConstant.GET_SUPPORTED_CURRENCIES, method = RequestMethod.GET)
    public ResponseEntity<List<Currency>> getSupportedCurrencies() {
        List<Currency> currencyList= forexConversionService.getSupportedCurrencies();
        return new ResponseEntity<List<Currency>>(currencyList, HttpStatus.OK);
    }

    @RequestMapping(value = ApiEndpointConstant.GET_CONVERSION, method = RequestMethod.GET)
    public ResponseEntity<String> getConversion(@RequestParam String input) {
        String convertedResult= forexConversionService.getConversionResult(input);
        return new ResponseEntity<String>(convertedResult, HttpStatus.OK);
    }

    @RequestMapping(value = ApiEndpointConstant.GET_TOP_N_PAIRS, method = RequestMethod.GET)
    public ResponseEntity<List<CurrencyPair>> getConversion(@RequestParam Integer n) {
        List<CurrencyPair> currencyPairList= forexConversionService.getTopNPairs(n);
        return new ResponseEntity<List<CurrencyPair>>(currencyPairList, HttpStatus.OK);
    }

}
