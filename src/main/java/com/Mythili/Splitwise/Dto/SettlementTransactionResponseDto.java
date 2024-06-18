package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.SettlementTransaction;
import com.Mythili.Splitwise.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
public class SettlementTransactionResponseDto {
    private UUID borrowerId;
    private UUID lenderId;
    private double amount;

    public static SettlementTransactionResponseDto from(SettlementTransaction settlementTransaction){
        SettlementTransactionResponseDto settlementTransactionResponseDto=new SettlementTransactionResponseDto();
        settlementTransactionResponseDto.setBorrowerId(settlementTransaction.getBorrower().getId());
        settlementTransactionResponseDto.setLenderId(settlementTransaction.getLender().getId());
        settlementTransactionResponseDto.setAmount(settlementTransaction.getAmount());

        return settlementTransactionResponseDto;
    }
}
