package com.Mythili.Splitwise.Repository;

import com.Mythili.Splitwise.Model.User;
import com.Mythili.Splitwise.Model.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, UUID> {
    void deleteByUser(User user);
}
