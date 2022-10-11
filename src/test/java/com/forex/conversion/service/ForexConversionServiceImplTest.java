package com.forex.conversion.service;

import com.forex.conversion.dao.ICurrencyConversionService;
import com.forex.conversion.dao.ICurrencyPairsService;
import com.forex.conversion.entity.CurrencyPair;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class ForexConversionServiceImplTest {

    @InjectMocks
    ForexConversionServiceImpl forexConversionService;

    @Mock
    ICurrencyConversionService currencyConversionService;

    @Mock
    ICurrencyPairsService currencyPairsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<CurrencyPair> list = new ArrayList<>();
        CurrencyPair curr1 = new CurrencyPair();
        curr1.setCurrency1("USD");
        curr1.setCurrency2("GBP");
        curr1.setCount(5);
        list.add(curr1);
        CurrencyPair curr2 = new CurrencyPair();
        curr2.setCurrency1("USD");
        curr2.setCurrency2("INR");
        curr2.setCount(4);
        list.add(curr2);
        Mockito.doReturn(1.03).when(currencyConversionService).findExchangeRate("EUR");
        Mockito.doReturn(82.86).when(currencyConversionService).findExchangeRate("INR");
        Mockito.doReturn(1.0).when(currencyConversionService).findExchangeRate("USD");
        Mockito.doReturn(list).when(currencyPairsService).getTopNPairs(2);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void convert_CurrencyParameters_ShouldReturnConvertedValue() throws IOException {

        String expectedOutput1 = "82.86  ";
        String expectedOutput2 = "82.86 1.03  ";
        String expectedOutput3 = "82.86  41430.0 515.0  ";
        Assert.assertEquals(expectedOutput1, forexConversionService.getConversionResult("USDINR"));
        Assert.assertEquals(expectedOutput2, forexConversionService.getConversionResult("USDINREUR"));
        Assert.assertEquals(expectedOutput3, forexConversionService.getConversionResult("USDINR,USDINREUR500"));
    }

    @Test
    void getTopNPairs() {
        List<CurrencyPair> expectedOutput = new ArrayList<>();
        CurrencyPair curr1 = new CurrencyPair();
        curr1.setCurrency1("USD");
        curr1.setCurrency2("GBP");
        curr1.setCount(5);
        expectedOutput.add(curr1);
        CurrencyPair curr2 = new CurrencyPair();
        curr2.setCurrency1("USD");
        curr2.setCurrency2("INR");
        curr2.setCount(4);
        expectedOutput.add(curr2);

        Assert.assertEquals(expectedOutput, forexConversionService.getTopNPairs(2));
    }
}