package com.Mythili.Splitwise.Service.Strategy;

import com.Mythili.Splitwise.Model.Expense;
import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.SettlementTransaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

public interface SettleUpStrategy{
    List<SettlementTransaction> settleUp(Group group);
}
