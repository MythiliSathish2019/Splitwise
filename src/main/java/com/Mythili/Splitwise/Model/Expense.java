package com.Mythili.Splitwise.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Entity
@Setter
@Getter
public class Expense extends BaseModel{
    private String description;
    private double amount;
    private LocalDateTime expenseTime;
    @ManyToOne
    @JoinColumn(name="userId")
    private User addedBy;
    @OneToMany
    @JoinColumn(name="expenseId")
    private List<UserExpense> userExpenses;
}
