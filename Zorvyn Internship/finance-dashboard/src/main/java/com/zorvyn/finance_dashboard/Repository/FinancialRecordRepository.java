package com.zorvyn.finance_dashboard.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zorvyn.finance_dashboard.Entity.FinancialRecord;
import com.zorvyn.finance_dashboard.Enums.TransactionType;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    // Spring Boot Magic: Automatically generates a query to find records by Category
    List<FinancialRecord> findByCategory(String category);

    // Spring Boot Magic: Automatically generates a query to find records by Type (INCOME/EXPENSE)
    List<FinancialRecord> findByType(TransactionType type);

    // Custom JPQL Query: Calculates the sum of all records for a specific type
    // This instantly fulfills a core Dashboard Summary requirement!
    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type")
    BigDecimal calculateTotalAmountByType(TransactionType type);
}
