package com.Mythili.Splitwise.Service;

import com.Mythili.Splitwise.Dto.*;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);
    UserResponseDto login(LoginRequestDto loginRequestDto);
    UserResponseDto updateProfile(UUID userId,CreateUserRequestDto createUserRequestDto);
    UserResponseDto addAFriend(UUID userId,UUID friendId);
    UserResponseDto getById(UUID userId);
    List<GroupResponseDto> viewGroupsOfUser(UUID userId);
    List<UserResponseDto> viewFriendsOfUser(UUID userId);
    void removeAFriend(UUID userId,UUID friendId);
    void deleteUser(UUID userId);
}
