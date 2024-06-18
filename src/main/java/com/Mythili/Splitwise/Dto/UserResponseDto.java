package com.Mythili.Splitwise.Dto;

import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private List<UUID> friends;
    private List<UUID>groupId;

    public static UserResponseDto from(User user){
        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());

        List<UUID>fuuids=new ArrayList<>();
        List<User>friends= user.getFriends();
        friends.forEach(f->fuuids.add(f.getId()));
        userResponseDto.setFriends(fuuids);

        List<UUID>uuids=new ArrayList<>();
        List<Group>groups=user.getGroups();
        groups.forEach(g->uuids.add(g.getId()));
        userResponseDto.setGroupId(uuids);

        return userResponseDto;
    }
}
