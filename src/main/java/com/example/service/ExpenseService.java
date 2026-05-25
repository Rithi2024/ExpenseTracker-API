package com.example.service;

import com.example.model.Expense;
import com.example.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            expense.setCategory(expenseDetails.getCategory());
            expense.setAmount(expenseDetails.getAmount());
            expense.setDescription(expenseDetails.getDescription());
            expense.setDate(expenseDetails.getDate());
            return expenseRepository.save(expense);
        }
        return null;
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public Double getTotalByCategory(String category) {
        Double total = expenseRepository.getTotalByCategory(category);
        return total != null ? total : 0.0;
    }
}
