package com.Mythili.Splitwise.Service;

import com.Mythili.Splitwise.Dto.*;
import com.Mythili.Splitwise.Exception.*;
import com.Mythili.Splitwise.Model.Group;
import com.Mythili.Splitwise.Model.User;
import com.Mythili.Splitwise.Repository.ExpenseRepository;
import com.Mythili.Splitwise.Repository.GroupRepository;
import com.Mythili.Splitwise.Repository.UserExpenseRepository;
import com.Mythili.Splitwise.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserExpenseRepository userExpenseRepository;

    @Override
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        User user=CreateUserRequestDto.from(createUserRequestDto);

        passwordValidation(createUserRequestDto.getPassword());

        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequestDto.getPassword()));
        List<Group> groups=new ArrayList<>();
        user.setGroups(groups);
        List<User> users=new ArrayList<>();
        user.setFriends(users);
        return UserResponseDto.from(userRepository.save(user));
    }

    //password validation for user registration or signUp
    private void passwordValidation(String password){
        if(password.length()<8)
            throw new PasswordLengthShortException("Password should be atleast 8 characters");
        boolean hasUpperCase=false;
        boolean hasSpecialCharacter=false;
        boolean hasWhiteSpace=false;
        for(char c:password.toCharArray())
        {
            if(Character.isUpperCase(c))
                hasUpperCase=true;
            if(!Character.isLetterOrDigit(c))
                hasSpecialCharacter=true;
            if(Character.isWhitespace(c))
                hasWhiteSpace=true;
        }
        if(!hasSpecialCharacter && !hasUpperCase && hasWhiteSpace)
            throw new PasswordNotValidException("Password should meet the following criteria:\n1)Should have a special character.\n2)Should have alteast one UpperCase character.\n3)Should not contain any spaces.");
    }
    //

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        User user=userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                ()->new InvalidUserEmailException("Invalid credentials.User not found")
        );
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(loginRequestDto.getPassword(),user.getPassword()))
            return UserResponseDto.from(user);
        else
            throw new UserNotFoundException("Invalid credentials");
    }


    @Override
    public UserResponseDto updateProfile(UUID userId,CreateUserRequestDto createUserRequestDto) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        user.setName(createUserRequestDto.getName());
        user.setEmail(createUserRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequestDto.getPassword()));

        List<Group>groups=user.getGroups();
        groups.forEach(g->{
            List<User>users=g.getMembers();
            users.removeIf(u->u.getId()==userId);
            users.add(user);
            g.setMembers(users);
            groupRepository.save(g);
        });

        return UserResponseDto.from(userRepository.save(user));
    }



    @Override
    public UserResponseDto addAFriend(UUID userId, UUID friendId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );

        User friend=userRepository.findById(friendId).orElseThrow(
                ()->new InvalidUserEmailException("Invalid email.User not found")
        );

        List<User> friends =user.getFriends();
        friends.add(friend);
        user.setFriends(friends);
        return UserResponseDto.from(userRepository.save(user));
    }


    @Override
    public UserResponseDto getById(UUID userId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        return UserResponseDto.from(user);
    }


    @Override
    public List<GroupResponseDto> viewGroupsOfUser(UUID userId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        List<Group>groups=user.getGroups();
        List<GroupResponseDto>groupResponseDtos=new ArrayList<>();
        groups.forEach(g->groupResponseDtos.add(GroupResponseDto.from(g)));
        return groupResponseDtos;
    }


    @Override
    public List<UserResponseDto> viewFriendsOfUser(UUID userId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        List<User>friends=user.getFriends();
        List<UserResponseDto>userResponseDtos=new ArrayList<>();
        friends.forEach(f->userResponseDtos.add(UserResponseDto.from(user)));
        return userResponseDtos;
    }


    @Override
    public void removeAFriend(UUID userId, UUID friendId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );

        userRepository.findById(friendId).orElseThrow(
                ()->new InvalidUserEmailException("Invalid email.User not found")
        );

        List<User>friends= user.getFriends();
        friends.removeIf(f->f.getId()==friendId);
        user.setFriends(friends);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("Invalid credentials.User not found")
        );
        userExpenseRepository.deleteByUser(user);
        expenseRepository.deleteByAddedBy(user);
        groupRepository.deleteByCreatedBy(user);
        userRepository.deleteById(userId);
    }


}
