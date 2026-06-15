package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Expense;
import com.example.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExpenseService {

    private static final Set<String> ALLOWED_SORT_FIELDS = new HashSet<>(
            Arrays.asList("id", "category", "amount", "date", "createdAt")
    );

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Page<Expense> getAllExpenses(int page, int size, String sortBy) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "date";
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(safeSortBy).descending());

        return expenseRepository.findAll(pageable);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + id));
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = getExpenseById(id);
        expense.setCategory(expenseDetails.getCategory());
        expense.setAmount(expenseDetails.getAmount());
        expense.setDescription(expenseDetails.getDescription());
        expense.setDate(expenseDetails.getDate());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with ID: " + id);
        }

        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public Double getTotalByCategory(String category) {
        Double total = expenseRepository.getTotalByCategory(category);
        return total != null ? total : 0.0;
    }

    public List<Expense> getAllExpensesForStats() {
        return expenseRepository.findAll();
    }
}
