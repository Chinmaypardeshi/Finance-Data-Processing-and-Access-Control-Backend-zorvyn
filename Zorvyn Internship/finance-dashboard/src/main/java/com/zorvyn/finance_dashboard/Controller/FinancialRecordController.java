package com.zorvyn.finance_dashboard.Controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zorvyn.finance_dashboard.Entity.FinancialRecord;
import com.zorvyn.finance_dashboard.Service.FinancialRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FinancialRecordController {
    
    private final FinancialRecordService recordService;

    // 1. GET ALL RECORDS
    // Security Config rules: Viewers, Analysts, and Admins can access this.
    @GetMapping("/records")
    public ResponseEntity<List<FinancialRecord>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    // 2. CREATE A RECORD
    // Security Config rules: ONLY Admins can access this.
    @PostMapping("/records")
    public ResponseEntity<FinancialRecord> createRecord(@RequestBody FinancialRecord record, Principal principal) {
        // Magic Trick: 'Principal' is automatically provided by Spring Security.
        // It holds the identity (email) of the user who just logged in!
        String loggedInUserEmail = principal.getName();
        
        FinancialRecord savedRecord = recordService.createRecord(record, loggedInUserEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }

    // 3. DASHBOARD SUMMARY: NET BALANCE
    // Security Config rules: Only Analysts and Admins can access this.
    @GetMapping("/summary/net-balance")
    public ResponseEntity<BigDecimal> getNetBalance() {
        return ResponseEntity.ok(recordService.calculateNetBalance());
    }

}
