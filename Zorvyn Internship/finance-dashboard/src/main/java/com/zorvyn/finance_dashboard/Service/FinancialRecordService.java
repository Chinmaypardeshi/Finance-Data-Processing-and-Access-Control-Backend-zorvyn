package com.zorvyn.finance_dashboard.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zorvyn.finance_dashboard.Entity.FinancialRecord;
import com.zorvyn.finance_dashboard.Entity.Users;
import com.zorvyn.finance_dashboard.Enums.TransactionType;
import com.zorvyn.finance_dashboard.Repository.FinancialRecordRepository;
import com.zorvyn.finance_dashboard.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    // 1. Create a new record (Requires knowing who created it)
    public FinancialRecord createRecord(FinancialRecord record, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        
        record.setCreatedBy(user);
        return recordRepository.save(record);
    }

    // 2. Get all records
    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    // 3. Dashboard Logic: Get Net Balance (Income - Expenses)
    public BigDecimal calculateNetBalance() {
        BigDecimal totalIncome = recordRepository.calculateTotalAmountByType(TransactionType.INCOME);
        BigDecimal totalExpense = recordRepository.calculateTotalAmountByType(TransactionType.EXPENSE);

        // Handle nulls in case the database is completely empty
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;

        return totalIncome.subtract(totalExpense);
    }
}
