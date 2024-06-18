package com.Mythili.Splitwise.Repository;

import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    void deleteByCreatedBy(User createdBy);
}
