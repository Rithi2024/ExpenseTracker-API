package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be 50 characters or fewer")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    @Column(nullable = false)
    private Double amount;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be 255 characters or fewer")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (date == null) {
            date = LocalDateTime.now();
        }
    }
}
