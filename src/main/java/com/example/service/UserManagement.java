package com.example.service;

import com.example.Util.UserToCreate;
import com.example.Util.UserToUpdate;
import com.example.exception.UserNotFoundException;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.repository.UserRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 创建要提供的服务，method form RepositoryImp.
 */
@Transactional
@Slf4j
@Service
public class UserManagement {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserToCreate userToCreate;
    @Resource
    private UserToUpdate userToUpdate;

    //  ****  Add User to DB
    public SqlUser createNewUser(UserReq userReq) {

        SqlUser sqlUser = userToCreate.buildUserToRepository(userReq);
        userRepository.save(sqlUser);
        log.info("user created  =" + sqlUser);
        return sqlUser;
    }

    //  ****  Update  User in DB
    public SqlUser updateUser(UserReq userReq, String userName) throws UserNotFoundException{
        SqlUser existingUser =userRepository.findById(userName)
                .orElseThrow(()->new UserNotFoundException("User not found with user name:"+userName));
        SqlUser sqlUser = userToUpdate.buildUserForUpdate(userReq,userName);

        userRepository.save(sqlUser);
        log.info("user was updated  =" + sqlUser);
        return sqlUser;
    }

    //  ****  List all User in DB
    public List <SqlUser> getAllUser()  {

        return userRepository.findAll();
    }

    //  ****  Get one User from DB
    public SqlUser getOneUser(String userName) throws UserNotFoundException{
        return userRepository.findById(userName)
                    .orElseThrow(()->new UserNotFoundException("User not found with user name:"+userName));

    }

    //  ****  remove one User from DB
    public boolean deleteUser(String userName) throws UserNotFoundException{
        SqlUser sqlUser = userRepository.findById(userName).orElseThrow(
                () -> new  UserNotFoundException("User not found with user name:" + userName));
            userRepository.delete(sqlUser);
            log.info(" used was deleted =" + userName);
            return true;
    }
}
