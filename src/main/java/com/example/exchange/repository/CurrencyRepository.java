package com.example.exchange.repository;

import com.example.exchange.model.Currency;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Query("SELECT c FROM Currency c  WHERE c.date >= ?1 AND c.baseCurrency = ?2")
    List<Currency> findAllByData(LocalDate date, String currency);

    @Query("SELECT c FROM Currency c  WHERE c.date = ?1 AND c.baseCurrency = ?2 "
            + "AND c.conversionCurrency = ?3")
    Optional<Currency> findByDataCurrency(LocalDate date,
                                          String baseCurrency, String conversionCurrency);
}
