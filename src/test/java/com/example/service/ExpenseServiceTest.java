package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Expense;
import com.example.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void getAllExpensesClampsPageSizeAndFallsBackToSafeSort() {
        Expense expense = sampleExpense();
        when(expenseRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(expense), PageRequest.of(0, 100), 1));

        Page<Expense> result = expenseService.getAllExpenses(-1, 500, "notAField");

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(expenseRepository).findAll(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(100);
        assertThat(pageable.getSort().getOrderFor("date")).isNotNull();
        assertThat(result.getContent()).containsExactly(expense);
    }

    @Test
    void getExpenseByIdThrowsWhenExpenseDoesNotExist() {
        when(expenseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> expenseService.getExpenseById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updateExpenseCopiesEditableFieldsAndSaves() {
        Expense existing = sampleExpense();
        Expense update = new Expense(null, "Travel", 45.75, "Taxi", LocalDateTime.now(), null);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(expenseRepository.save(existing)).thenReturn(existing);

        Expense result = expenseService.updateExpense(1L, update);

        assertThat(result.getCategory()).isEqualTo("Travel");
        assertThat(result.getAmount()).isEqualTo(45.75);
        assertThat(result.getDescription()).isEqualTo("Taxi");
        verify(expenseRepository).save(existing);
    }

    @Test
    void getTotalByCategoryReturnsZeroWhenRepositoryReturnsNull() {
        when(expenseRepository.getTotalByCategory("Food")).thenReturn(null);

        assertThat(expenseService.getTotalByCategory("Food")).isZero();
    }

    private Expense sampleExpense() {
        return new Expense(1L, "Food", 25.50, "Lunch", LocalDateTime.now(), LocalDateTime.now());
    }
}
