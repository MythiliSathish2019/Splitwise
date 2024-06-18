package com.Mythili.Splitwise.Controller;

import com.Mythili.Splitwise.Dto.*;
import com.Mythili.Splitwise.Model.SettlementTransaction;
import com.Mythili.Splitwise.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    //creating a group
    @PostMapping("/create")
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody CreateGroupRequestDto createGroupRequestDto){
        return ResponseEntity.ok(groupService.createGroup(createGroupRequestDto));
    }

    //adding member to a group
    @PutMapping("/addMember/{g_id}/{f_id}")
    public ResponseEntity<GroupResponseDto> addMember(@PathVariable ("g_id") UUID groupId,@PathVariable ("f_id") UUID friendId ){
        return ResponseEntity.ok(groupService.addAMember(groupId,friendId));
    }

    //adding an expense
    @PutMapping("/addExpense/{g_id}/{e_id}")
    public ResponseEntity<GroupResponseDto> addExpense(@PathVariable ("g_id") UUID groupId,@PathVariable ("e_id") UUID expenseId ){
        return ResponseEntity.ok(groupService.addAnExpense(groupId,expenseId));
    }

    //updating group
    @PutMapping("/update/{g_id}")
    public ResponseEntity<GroupResponseDto> updateGroup(@PathVariable ("g_id") UUID groupId,@RequestBody CreateGroupRequestDto createGroupRequestDto){
        return ResponseEntity.ok(groupService.updateGroup(groupId,createGroupRequestDto));
    }

    //getting group by Id (or) viewing a group
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> viewGroup(@PathVariable ("id") UUID groupId){
        return ResponseEntity.ok(groupService.getById(groupId));
    }

    //updating expense in a group
    @PutMapping("/updateExpense/{g_id}/{e_id}")
    public ResponseEntity<GroupResponseDto> updateExpenseInAGroup(@PathVariable ("g_id") UUID groupId, @PathVariable ("e_id") UUID expenseId, @RequestBody CreateExpenseRequestDto createExpenseRequestDto){
        return ResponseEntity.ok(groupService.updateExpenseInGroup(groupId,expenseId,createExpenseRequestDto));
    }

    //view members of a group
    @GetMapping("/viewMembers/{id}")
    public ResponseEntity<List<UserResponseDto>> viewMembers(@PathVariable ("id") UUID groupId){
        return ResponseEntity.ok(groupService.viewMembersOfAGroup(groupId));
    }

    //view expenses of a group
    @GetMapping("/viewExpenses/{id}")
    public ResponseEntity<List<ExpenseResponseDto>> viewExpenses(@PathVariable ("id") UUID groupId){
        return ResponseEntity.ok(groupService.viewExpensesOfAGroup(groupId));
    }

    //deleting a group
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGroup(@PathVariable ("id") UUID groupId){
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok("Successfully deleted the Group");
    }

    //removing a member from the group
    @PutMapping("/removeMember/{g_id}/{m_id}")
    public ResponseEntity removeMember(@PathVariable ("g_id") UUID groupId, @PathVariable ("m_id") UUID userId){
        groupService.removeAMember(groupId,userId);
        return ResponseEntity.ok("Successfully removed the member");
    }

    //removing an expense
    @PutMapping("/removeExpense/{g_id}/{e_id}")
    public ResponseEntity removeExpense(@PathVariable ("g_id") UUID groupId, @PathVariable ("e_id") UUID expenseId){
        groupService.removeExpense(groupId,expenseId);
        return ResponseEntity.ok("Successfully removed the expense");
    }

    //settling up the expenses
    @GetMapping("/settleUp/{id}")
    public ResponseEntity<List<SettlementTransactionResponseDto>> settleUp(@PathVariable ("id") UUID groupId){
        return ResponseEntity.ok(groupService.settleUp(groupId));
    }
}
