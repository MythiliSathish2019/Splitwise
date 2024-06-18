package com.Mythili.Splitwise.Service;

import com.Mythili.Splitwise.Dto.CreateExpenseRequestDto;
import com.Mythili.Splitwise.Dto.CreateUserExpenseRequestDto;
import com.Mythili.Splitwise.Dto.ExpenseResponseDto;
import com.Mythili.Splitwise.Dto.UserExpenseResponseDto;
import com.Mythili.Splitwise.Model.Expense;
import com.Mythili.Splitwise.Model.UserExpense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponseDto createExpense(CreateExpenseRequestDto createExpenseRequestDto);
    ExpenseResponseDto addingUserExpense(UUID expenseId, CreateUserExpenseRequestDto createUserExpenseRequestDto);
    ExpenseResponseDto getById(UUID expenseId);
    Expense updateExpense(UUID expenseId, CreateExpenseRequestDto createExpenseRequestDto);
    ExpenseResponseDto updateUserExpenseInExpense(UUID expenseId, UUID userExpenseId, CreateUserExpenseRequestDto createUserExpenseRequestDto);
    List<UserExpenseResponseDto> viewUserExpenseOfAExpense(UUID expenseId);
    void deleteExpense(UUID expenseId);
    void removeUserExpense(UUID expenseId, UUID userExpenseId);
}
