package com.Mythili.Splitwise.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Entity
@Getter
@Setter
public class SettlementTransaction extends BaseModel{
    @ManyToOne
    private User borrower;
    @ManyToOne
    private User lender;
    private double amount;

    public SettlementTransaction(User borrower, User lender) {
        this.borrower = borrower;
        this.lender = lender;
    }

    public SettlementTransaction() {
    }
}
