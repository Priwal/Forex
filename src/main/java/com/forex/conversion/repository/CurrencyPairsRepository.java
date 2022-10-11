package com.forex.conversion.repository;

import com.forex.conversion.entity.Currency;
import com.forex.conversion.entity.CurrencyPair;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CurrencyPairsRepository extends JpaRepository<CurrencyPair, Integer>, JpaSpecificationExecutor<CurrencyPair> {

    @Query(value = "SELECT * FROM currency_pairs c ORDER BY c.count DESC LIMIT :limit", nativeQuery = true)
    List<CurrencyPair> getEntitiesByLimit(@Param("limit") int limit);

    CurrencyPair findFirstByCurrency1AndCurrency2(String currency1, String currency2);
}
