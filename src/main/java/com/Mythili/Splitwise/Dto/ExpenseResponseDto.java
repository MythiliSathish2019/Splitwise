package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.Expense;
import com.Mythili.Splitwise.Model.UserExpense;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExpenseResponseDto {
    private UUID id;
    private String description;
    private double amount;
    private LocalDateTime expenseTime;
    private UUID userId;
    private List<UUID> userExpenses;

    public static ExpenseResponseDto from(Expense expense){
        ExpenseResponseDto expenseResponseDto=new ExpenseResponseDto();
        expenseResponseDto.setId(expense.getId());
        expenseResponseDto.setDescription(expense.getDescription());
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setExpenseTime(expense.getExpenseTime());
        expenseResponseDto.setUserId(expense.getAddedBy().getId());

        List<UUID>uuids=new ArrayList<>();
        List<UserExpense>userExpenses=expense.getUserExpenses();
        userExpenses.forEach(u->uuids.add(u.getId()));
        expenseResponseDto.setUserExpenses(uuids);

        return expenseResponseDto;
    }
}
