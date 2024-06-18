package com.Mythili.Splitwise.Dto;


import com.Mythili.Splitwise.Model.User;
import com.Mythili.Splitwise.Model.UserExpense;
import com.Mythili.Splitwise.Model.UserExpenseType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateUserExpenseRequestDto {
    private UUID userId;
    private double amount;
    private String userExpenseType;//PAID,HASTOPAY

    public static UserExpense from(CreateUserExpenseRequestDto createUserExpenseRequestDto){
        UserExpense userExpense=new UserExpense();
        userExpense.setAmount(createUserExpenseRequestDto.getAmount());
        if(createUserExpenseRequestDto.getUserExpenseType().equals("PAID"))
            userExpense.setUserExpenseType(UserExpenseType.PAID);
        else
            userExpense.setUserExpenseType(UserExpenseType.HASTOPAY);
        return userExpense;
    }
}
