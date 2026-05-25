package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Expense;
import com.example.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller for managing expenses
 * Provides endpoints for CRUD operations and expense analytics
 */
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Get all expenses with pagination and sorting
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy) {
        
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
            List<Expense> expenses = expenseRepository.findAll();
            
            Map<String, Object> response = new HashMap<>();
            response.put("totalExpenses", expenses.size());
            response.put("page", page);
            response.put("size", size);
            response.put("data", expenses);
            response.put("totalAmount", expenses.stream().mapToDouble(Expense::getAmount).sum());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching expenses: " + e.getMessage());
        }
    }

    /**
     * Get expense by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + id));
    }

    /**
     * Create new expense
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createExpense(@Valid @RequestBody Expense expense) {
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        
        Expense savedExpense = expenseRepository.save(expense);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expense created successfully");
        response.put("data", savedExpense);
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Update existing expense
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateExpense(
            @PathVariable Long id, 
            @Valid @RequestBody Expense expenseDetails) {
        
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + id));
        
        if (expenseDetails.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        expense.setCategory(expenseDetails.getCategory());
        expense.setAmount(expenseDetails.getAmount());
        expense.setDescription(expenseDetails.getDescription());
        expense.setDate(expenseDetails.getDate());
        
        Expense updated = expenseRepository.save(expense);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expense updated successfully");
        response.put("data", updated);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Delete expense
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with ID: " + id);
        }
        
        expenseRepository.deleteById(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Expense deleted successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get expenses by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getExpensesByCategory(@PathVariable String category) {
        List<Expense> expenses = expenseRepository.findByCategory(category);
        
        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("count", expenses.size());
        response.put("total", expenses.stream().mapToDouble(Expense::getAmount).sum());
        response.put("data", expenses);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get total expenses by category
     */
    @GetMapping("/total/{category}")
    public ResponseEntity<Map<String, Object>> getTotalByCategory(@PathVariable String category) {
        Double total = expenseRepository.getTotalByCategory(category);
        int count = expenseRepository.findByCategory(category).size();
        
        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("total", total != null ? total : 0.0);
        response.put("expenseCount", count);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get expense statistics
     */
    @GetMapping("/stats/summary")
    public ResponseEntity<Map<String, Object>> getExpenseStats() {
        List<Expense> allExpenses = expenseRepository.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalExpenses", allExpenses.size());
        stats.put("totalAmount", allExpenses.stream().mapToDouble(Expense::getAmount).sum());
        stats.put("averageExpense", allExpenses.isEmpty() ? 0 : 
                   allExpenses.stream().mapToDouble(Expense::getAmount).average().orElse(0));
        stats.put("highestExpense", allExpenses.isEmpty() ? 0 :
                   allExpenses.stream().mapToDouble(Expense::getAmount).max().orElse(0));
        
        return ResponseEntity.ok(stats);
    }
}
