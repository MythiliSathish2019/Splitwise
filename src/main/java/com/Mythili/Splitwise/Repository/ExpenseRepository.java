package com.Mythili.Splitwise.Repository;

import com.Mythili.Splitwise.Model.Expense;
import com.Mythili.Splitwise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    void deleteByAddedBy(User addedBy);
}
