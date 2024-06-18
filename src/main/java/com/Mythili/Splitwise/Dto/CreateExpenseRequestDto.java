package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.Expense;

import com.Mythili.Splitwise.Model.UserExpense;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
public class CreateExpenseRequestDto {
    private String description;
    private double amount;
    private UUID userId;

    public static Expense from(CreateExpenseRequestDto createExpenseRequestDto){
        Expense expense=new Expense();
        expense.setDescription(createExpenseRequestDto.getDescription());
        expense.setAmount(createExpenseRequestDto.getAmount());
        expense.setExpenseTime(LocalDateTime.now());
        return expense;
    }
}
