package com.Mythili.Splitwise.Controller;

import com.Mythili.Splitwise.Dto.CreateExpenseRequestDto;
import com.Mythili.Splitwise.Dto.CreateUserExpenseRequestDto;
import com.Mythili.Splitwise.Dto.ExpenseResponseDto;
import com.Mythili.Splitwise.Dto.UserExpenseResponseDto;
import com.Mythili.Splitwise.Model.UserExpense;
import com.Mythili.Splitwise.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    //creating expense
    @PostMapping("/create")
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody CreateExpenseRequestDto createExpenseRequestDto){
        return ResponseEntity.ok(expenseService.createExpense(createExpenseRequestDto));
    }

    //create and adding user expense
    @PostMapping("/addUserExpense/{e_id}")
    public ResponseEntity<ExpenseResponseDto> addUserExpense(@PathVariable ("e_id")UUID expenseId, @RequestBody CreateUserExpenseRequestDto createUserExpenseRequestDto){
        return ResponseEntity.ok(expenseService.addingUserExpense(expenseId,createUserExpenseRequestDto));
    }

    //getting Expense by Id (or) viewing an expense
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> viewExpense(@PathVariable ("id")UUID expenseId){
        return ResponseEntity.ok(expenseService.getById(expenseId));
    }

    //update user expense in a expense
    @PutMapping("/updateUserExpense/{e_id}/{ue_id}")
    public ResponseEntity<ExpenseResponseDto> updateUserExpenseInAExpense(@PathVariable ("e_id")UUID expenseId, @PathVariable ("ue_id") UUID userExpenseId, @RequestBody CreateUserExpenseRequestDto createUserExpenseRequestDto){
        return ResponseEntity.ok(expenseService.updateUserExpenseInExpense(expenseId,userExpenseId,createUserExpenseRequestDto));
    }

    //view user expenses in an expense
    @GetMapping("/viewUserExpenses/{id}")
    public ResponseEntity<List<UserExpenseResponseDto>> viewUserExpenses(@PathVariable ("id")UUID expenseId){
        return ResponseEntity.ok(expenseService.viewUserExpenseOfAExpense(expenseId));
    }

    //delete an expense
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteExpense(@PathVariable ("id")UUID expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Successfully deleted the expense");
    }

    //removing and deleting user expense from expense
    @PutMapping("/removeUserExpense/{e_id}/{ue_id}")
    public ResponseEntity removeUserExpense(@PathVariable ("id")UUID expenseId,@PathVariable ("ue_id") UUID userExpenseId){
        expenseService.removeUserExpense(expenseId,userExpenseId);
        return ResponseEntity.ok("Successfully removed the user expense");
    }

}
