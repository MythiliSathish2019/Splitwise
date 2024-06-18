package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.UserExpense;
import com.Mythili.Splitwise.Model.UserExpenseType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserExpenseResponseDto {
    private UUID id;
    private UUID userId;
    private double amount;
    private UserExpenseType userExpenseType;

    public static UserExpenseResponseDto from(UserExpense userExpense){
        UserExpenseResponseDto userExpenseResponseDto=new UserExpenseResponseDto();
        userExpenseResponseDto.setId(userExpense.getId());
        userExpenseResponseDto.setUserId(userExpense.getUser().getId());
        userExpenseResponseDto.setAmount(userExpense.getAmount());
        userExpenseResponseDto.setUserExpenseType(userExpense.getUserExpenseType());
        return userExpenseResponseDto;
    }
}
