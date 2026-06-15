package com.example.controller;

import com.example.model.Expense;
import com.example.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy) {

        Page<Expense> expensePage = expenseService.getAllExpenses(page, size, sortBy);

        Map<String, Object> response = new HashMap<>();
        response.put("totalExpenses", expensePage.getTotalElements());
        response.put("totalPages", expensePage.getTotalPages());
        response.put("page", expensePage.getNumber());
        response.put("size", expensePage.getSize());
        response.put("data", expensePage.getContent());
        response.put("pageAmount", expensePage.getContent().stream().mapToDouble(Expense::getAmount).sum());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createExpense(@Valid @RequestBody Expense expense) {
        Expense savedExpense = expenseService.createExpense(expense);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expense created successfully");
        response.put("data", savedExpense);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense expenseDetails) {

        Expense updated = expenseService.updateExpense(id, expenseDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expense updated successfully");
        response.put("data", updated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Expense deleted successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getExpensesByCategory(@PathVariable String category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);

        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("count", expenses.size());
        response.put("total", expenses.stream().mapToDouble(Expense::getAmount).sum());
        response.put("data", expenses);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total/{category}")
    public ResponseEntity<Map<String, Object>> getTotalByCategory(@PathVariable String category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);

        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("total", expenseService.getTotalByCategory(category));
        response.put("expenseCount", expenses.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/summary")
    public ResponseEntity<Map<String, Object>> getExpenseStats() {
        List<Expense> allExpenses = expenseService.getAllExpensesForStats();

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
