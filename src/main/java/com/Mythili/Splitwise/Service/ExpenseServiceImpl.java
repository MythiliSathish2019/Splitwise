package com.Mythili.Splitwise.Service;

import com.Mythili.Splitwise.Dto.CreateExpenseRequestDto;
import com.Mythili.Splitwise.Dto.CreateUserExpenseRequestDto;
import com.Mythili.Splitwise.Dto.ExpenseResponseDto;
import com.Mythili.Splitwise.Dto.UserExpenseResponseDto;
import com.Mythili.Splitwise.Exception.ExpenseNotFoundException;
import com.Mythili.Splitwise.Exception.UserExpenseNotFoundException;
import com.Mythili.Splitwise.Exception.UserNotFoundException;
import com.Mythili.Splitwise.Model.Expense;
import com.Mythili.Splitwise.Model.User;
import com.Mythili.Splitwise.Model.UserExpense;
import com.Mythili.Splitwise.Model.UserExpenseType;
import com.Mythili.Splitwise.Repository.ExpenseRepository;
import com.Mythili.Splitwise.Repository.UserExpenseRepository;
import com.Mythili.Splitwise.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserExpenseRepository userExpenseRepository;



    @Override
    public ExpenseResponseDto createExpense(CreateExpenseRequestDto createExpenseRequestDto) {
        Expense expense=CreateExpenseRequestDto.from(createExpenseRequestDto);
        User user=userRepository.findById(createExpenseRequestDto.getUserId()).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        List<UserExpense> userExpenses=new ArrayList<>();
        expense.setUserExpenses(userExpenses);
        expense.setAddedBy(user);
        return ExpenseResponseDto.from(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto addingUserExpense(UUID expenseId, CreateUserExpenseRequestDto createUserExpenseRequestDto) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        User user=userRepository.findById(createUserExpenseRequestDto.getUserId()).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        UserExpense userExpense=CreateUserExpenseRequestDto.from(createUserExpenseRequestDto);
        userExpense.setUser(user);
        List<UserExpense>userExpenses=expense.getUserExpenses();
        userExpenses.add(userExpense);
        expense.setUserExpenses(userExpenses);
        userExpenseRepository.save(userExpense);
        return ExpenseResponseDto.from(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto getById(UUID expenseId) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        return ExpenseResponseDto.from(expense);
    }

    @Override
    public Expense updateExpense(UUID expenseId, CreateExpenseRequestDto createExpenseRequestDto) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        expense.setDescription(createExpenseRequestDto.getDescription());
        expense.setAmount(createExpenseRequestDto.getAmount());
    //think and add logic for the expenses to be updated in the corresponding groups as well.
        return expenseRepository.save(expense);
    }

    @Override
    public ExpenseResponseDto updateUserExpenseInExpense(UUID expenseId, UUID userExpenseId, CreateUserExpenseRequestDto createUserExpenseRequestDto) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        User user=userRepository.findById(createUserExpenseRequestDto.getUserId()).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        List<UserExpense> userExpenses=expense.getUserExpenses();
        userExpenses.removeIf(ue->ue.getId()==userExpenseId);

        UserExpense userExpense=userExpenseRepository.findById(userExpenseId).orElseThrow(
                ()->new UserExpenseNotFoundException("Invalid User expense"));
        userExpense.setUser(user);
        userExpense.setAmount(createUserExpenseRequestDto.getAmount());
        if(createUserExpenseRequestDto.getUserExpenseType().equals("PAID"))
            userExpense.setUserExpenseType(UserExpenseType.PAID);
        else
            userExpense.setUserExpenseType(UserExpenseType.HASTOPAY);
        userExpenseRepository.save(userExpense);

        userExpenses.add(userExpense);
        expense.setUserExpenses(userExpenses);
        return ExpenseResponseDto.from(expenseRepository.save(expense));
    }

    @Override
    public List<UserExpenseResponseDto> viewUserExpenseOfAExpense(UUID expenseId) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        List<UserExpenseResponseDto>userExpenseResponseDtoList=new ArrayList<>();
        List<UserExpense>userExpenses=expense.getUserExpenses();
        userExpenses.forEach(ue->userExpenseResponseDtoList.add(UserExpenseResponseDto.from(ue)));
        return userExpenseResponseDtoList;
    }

    @Override
    @Transactional
    public void deleteExpense(UUID expenseId) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        List<UserExpense>userExpenses=expense.getUserExpenses();
        userExpenses.forEach(ue->userExpenseRepository.deleteById(ue.getId()));
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional
    public void removeUserExpense(UUID expenseId, UUID userExpenseId) {
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        List<UserExpense>userExpenses=expense.getUserExpenses();
        userExpenses.removeIf(ue->ue.getId()==userExpenseId);
        userExpenseRepository.deleteById(userExpenseId);
        expense.setUserExpenses(userExpenses);
        expenseRepository.save(expense);
    }


}
