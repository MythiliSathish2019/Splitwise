package com.Mythili.Splitwise.Service;

import com.Mythili.Splitwise.Dto.*;
import com.Mythili.Splitwise.Model.SettlementTransaction;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    GroupResponseDto createGroup(CreateGroupRequestDto createGroupRequestDto);
    GroupResponseDto addAMember(UUID groupId,UUID friendId);
    GroupResponseDto addAnExpense(UUID groupId,UUID expenseId);
    GroupResponseDto updateGroup(UUID groupId,CreateGroupRequestDto createGroupRequestDto);
    GroupResponseDto getById(UUID groupId);
    GroupResponseDto updateExpenseInGroup(UUID groupId, UUID expenseId, CreateExpenseRequestDto createExpenseRequestDto);
    List<UserResponseDto> viewMembersOfAGroup(UUID groupId);
    List<ExpenseResponseDto> viewExpensesOfAGroup(UUID groupId);
    void deleteGroup(UUID groupId);
    void removeAMember(UUID groupId,UUID userId);
    void removeExpense(UUID groupId,UUID expenseId);
    List<SettlementTransactionResponseDto> settleUp(UUID groupId);
}
