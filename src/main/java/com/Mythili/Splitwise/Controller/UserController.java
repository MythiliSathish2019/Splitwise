package com.Mythili.Splitwise.Controller;

import com.Mythili.Splitwise.Dto.CreateUserRequestDto;
import com.Mythili.Splitwise.Dto.GroupResponseDto;
import com.Mythili.Splitwise.Dto.LoginRequestDto;
import com.Mythili.Splitwise.Dto.UserResponseDto;
import com.Mythili.Splitwise.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //signUp or createUser
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody CreateUserRequestDto createUserRequestDto){
        return ResponseEntity.ok(userService.createUser(createUserRequestDto));
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    //updating the profile
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable("id") UUID userId,@RequestBody CreateUserRequestDto createUserRequestDto){
        return ResponseEntity.ok(userService.updateProfile(userId,createUserRequestDto));
    }


    //adding a friend
    @PutMapping("/addFriend/{u_id}/{f_id}")
    public ResponseEntity<UserResponseDto> addFriend(@PathVariable ("u_id") UUID userId,@PathVariable ("f_id") UUID friendId){
        return ResponseEntity.ok(userService.addAFriend(userId,friendId));
    }

    //getting User by ID (or) viewing the profile
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable ("id") UUID userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

    //view groups of user
    @GetMapping("/viewGroups/{id}")
    public ResponseEntity<List<GroupResponseDto>> viewGroups(@PathVariable ("id") UUID userId){
        return ResponseEntity.ok(userService.viewGroupsOfUser(userId));
    }

    //view friends of a user
    @GetMapping("/viewFriends/{id}")
    public ResponseEntity<List<UserResponseDto>> viewFriends(@PathVariable ("id") UUID userId){
        return ResponseEntity.ok(userService.viewFriendsOfUser(userId));
    }

    //deleting a user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable ("id") UUID userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("Successfully deleted the User");
    }

    //removing a friend from the list
    @PutMapping("/removeFriend/{u_id}/{f_id}")
    public ResponseEntity removeFriend(@PathVariable ("u_id") UUID userId,@PathVariable ("f_id") UUID friendId){
        userService.removeAFriend(userId,friendId);
        return ResponseEntity.ok("Successfully removed the Friend");
    }
}
