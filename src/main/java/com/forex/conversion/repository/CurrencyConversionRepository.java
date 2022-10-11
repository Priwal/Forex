package com.forex.conversion.repository;

import com.forex.conversion.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<Currency, Integer> , JpaSpecificationExecutor<Currency>{

    Currency findByCurrencySymbol(String currencySymbol);
}
