package com.example.Util;

import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserToUpdate {

    @Resource
    private UserRepository userRepository;

    public SqlUser buildUserForUpdate(UserReq userReq, String userName) {

        SqlUser existingUser=userRepository.findById(userName).get();

        existingUser.setUserName(userReq.getEmail());
        existingUser.setPassword(userReq.getPassword());
        existingUser.setFirstName(userReq.getFirstName());
        existingUser.setLastName(userReq.getLastName());
        existingUser.setEmail(userReq.getEmail());
        existingUser.setContactNumber(userReq.getContactNumber());
        existingUser.setTags((userReq.getTags()).stream().map(Objects::toString).collect(Collectors.joining(":")));
        existingUser.setUpdated(Instant.now().toString());

        return existingUser;
    }
}
