package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.Expense;
import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.SettlementTransaction;
import com.Mythili.Splitwise.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GroupResponseDto {
    private UUID id;
    private String name;
    private UUID createdBy;
    private LocalDateTime creationTime;
    private List<UUID> members;
    private List<UUID> expenses;
    private List<SettlementTransaction> settlementTransactions;

    public static GroupResponseDto from(Group group){
        GroupResponseDto groupResponseDto=new GroupResponseDto();
        groupResponseDto.setId(group.getId());
        groupResponseDto.setName(group.getName());
        groupResponseDto.setCreatedBy(group.getCreatedBy().getId());
        groupResponseDto.setCreationTime(group.getCreationTime());

        List<UUID> uuids=new ArrayList<>();
        List<User> users=group.getMembers();
        users.forEach(user -> uuids.add(user.getId()));
        groupResponseDto.setMembers(uuids);

        List<UUID> euuids=new ArrayList<>();
        List<Expense> expenseList=group.getExpenses();
        expenseList.forEach(e -> euuids.add(e.getId()));
        groupResponseDto.setExpenses(euuids);

        groupResponseDto.setSettlementTransactions(group.getSettlementTransactions());
        return groupResponseDto;
    }
}
