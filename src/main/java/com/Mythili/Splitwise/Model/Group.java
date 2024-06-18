package com.Mythili.Splitwise.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name="groups")
@Getter
@Setter
public class Group extends BaseModel{
    private String name;
    @ManyToOne
    @JoinColumn(name="userId")
    private User createdBy;
    private LocalDateTime creationTime;
    @ManyToMany(mappedBy = "groups")
    private List<User> members;
    @OneToMany
    @JoinColumn(name="groupId")
    private List<Expense> expenses;
    @OneToMany
    @JoinColumn(name="groupId")
    private List<SettlementTransaction> settlementTransactions;
}
