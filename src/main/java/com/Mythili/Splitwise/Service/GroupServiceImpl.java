package com.Mythili.Splitwise.Service;

import com.Mythili.Splitwise.Dto.*;
import com.Mythili.Splitwise.Exception.ExpenseNotFoundException;
import com.Mythili.Splitwise.Exception.GroupNotFoundException;
import com.Mythili.Splitwise.Exception.UserNotFoundException;
import com.Mythili.Splitwise.Model.*;
import com.Mythili.Splitwise.Repository.ExpenseRepository;
import com.Mythili.Splitwise.Repository.GroupRepository;
import com.Mythili.Splitwise.Repository.UserRepository;
import com.Mythili.Splitwise.Service.Strategy.SettleUpStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService{
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private SettleUpStrategy settleUpStrategy;

    @Override
    public GroupResponseDto createGroup(CreateGroupRequestDto createGroupRequestDto) {
        Group group=CreateGroupRequestDto.from(createGroupRequestDto);
        User user=userRepository.findById(createGroupRequestDto.getCreatedByUserId()).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        List<User> users=new ArrayList<>();
        List<Expense> expenses=new ArrayList<>();
        List<SettlementTransaction> settlementTransactions=new ArrayList<>();
        group.setCreatedBy(user);
        group.setMembers(users);
        group.setExpenses(expenses);
        group.setSettlementTransactions(settlementTransactions);
        return GroupResponseDto.from(groupRepository.save(group));
    }

    @Override
    public GroupResponseDto addAMember(UUID groupId, UUID friendId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        User user=userRepository.findById(friendId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        List<User> members=group.getMembers();
        members.add(user);
        group.setMembers(members);
        user.getGroups().add(group);
        userRepository.save(user);
        return GroupResponseDto.from(groupRepository.save(group));
    }

    @Override
    public GroupResponseDto addAnExpense(UUID groupId, UUID expenseId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(
                ()->new ExpenseNotFoundException("Invalid Expense")
        );
        List<Expense>expenses=group.getExpenses();
        expenses.add(expense);
        group.setExpenses(expenses);
        return GroupResponseDto.from(groupRepository.save(group));
    }

    @Override
    public GroupResponseDto updateGroup(UUID groupId, CreateGroupRequestDto createGroupRequestDto) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        group.setName(createGroupRequestDto.getName());

        List<User>users=group.getMembers();
        users.forEach(u->{
            List<Group> groupList=u.getGroups();
            groupList.removeIf(g->g.getId()==groupId);
            groupList.add(group);
            u.setGroups(groupList);
            userRepository.save(u);
        });

        return GroupResponseDto.from(groupRepository.save(group));
    }

    @Override
    public GroupResponseDto getById(UUID groupId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        return GroupResponseDto.from(group);
    }

    @Override
    public GroupResponseDto updateExpenseInGroup(UUID groupId, UUID expenseId, CreateExpenseRequestDto createExpenseRequestDto) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        List<Expense>expenses=group.getExpenses();
        expenses.removeIf(e->e.getId()==expenseId);
        Expense expense=expenseService.updateExpense(expenseId,createExpenseRequestDto);
        expenseRepository.save(expense);
        expenses.add(expense);
        group.setExpenses(expenses);
        return GroupResponseDto.from(groupRepository.save(group));
    }

    @Override
    public List<UserResponseDto> viewMembersOfAGroup(UUID groupId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        List<User> members=group.getMembers();
        List<UserResponseDto> userResponseDtos=new ArrayList<>();
        members.forEach(m->userResponseDtos.add(UserResponseDto.from(m)));
        return userResponseDtos;
    }

    @Override
    public List<ExpenseResponseDto> viewExpensesOfAGroup(UUID groupId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        List<Expense> expenses=group.getExpenses();
        List<ExpenseResponseDto> expenseResponseDtos=new ArrayList<>();
        expenses.forEach(e->expenseResponseDtos.add(ExpenseResponseDto.from(e)));
        return expenseResponseDtos;
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );

        List<Expense>expenses=group.getExpenses();
        expenses.forEach(e->expenseService.deleteExpense(e.getId()));

        List<User>users=group.getMembers();
        users.forEach(u->{
            List<Group> groupList=u.getGroups();
            groupList.removeIf(g->g.getId()==groupId);
            u.setGroups(groupList);
            userRepository.save(u);
        });

        groupRepository.deleteById(groupId);
    }

    @Override
    public void removeAMember(UUID groupId, UUID userId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        List<User>members= group.getMembers();
        members.removeIf(m->m.getId()==userId);
        group.setMembers(members);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void removeExpense(UUID groupId, UUID expenseId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        List<Expense>expenses=group.getExpenses();
        expenses.removeIf(e->e.getId()==expenseId);
        group.setExpenses(expenses);
        groupRepository.save(group);
        expenseService.deleteExpense(expenseId);
    }

    @Override
    public List<SettlementTransactionResponseDto> settleUp(UUID groupId) {
        Group group=groupRepository.findById(groupId).orElseThrow(
                ()->new GroupNotFoundException("Group not found")
        );
        List<SettlementTransaction>settlementTransactions=settleUpStrategy.settleUp(group);
        List<SettlementTransactionResponseDto> settlementTransactionResponseDtoList=new ArrayList<>();
        settlementTransactions.forEach(st->{
            settlementTransactionResponseDtoList.add(SettlementTransactionResponseDto.from(st));
        });
        group.setSettlementTransactions(settlementTransactions);
        groupRepository.save(group);
        return settlementTransactionResponseDtoList;
    }


}
