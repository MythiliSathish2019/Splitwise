package com.Mythili.Splitwise.Dto;



import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class CreateGroupRequestDto {
    private String name;
    private UUID createdByUserId;

    public static Group from(CreateGroupRequestDto createGroupRequestDto){
        Group group=new Group();
        group.setName(createGroupRequestDto.getName());
        group.setCreationTime(LocalDateTime.now());
        return group;
    }
}
