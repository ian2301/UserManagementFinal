package com.example.service;

import com.example.Util.UserToCreate;
import com.example.Util.UserToUpdate;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserManagementTest {

    @Mock
    private UserToCreate userToCreate ;
    @Mock
    private UserToUpdate userToUpdate ;
    @Mock
    private UserReq userReq ;
    @Mock
    private SqlUser sqlUser, sqlUser1 ;
    private List<SqlUser> listUser = new ArrayList<>();

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserManagement userManagement;

    @BeforeEach
    public void setup() {

        //初始化
        MockitoAnnotations.openMocks(this);

        userReq = new UserReq("322654", "stephen", "Chao",
                "stephen133@gmail.com", "321654987", Arrays.asList("a", "b", "aab"));
        // 创建 response object
        sqlUser = new SqlUser(65L, "stephen133@gmail.com",
                "322654", "stephen", "Chao",
                "stephen133@gmail.com", "321654987", 63,
                "male", "IE", "a:b:aab", "Active",
                Instant.now().toString(), Instant.now().toString());
        sqlUser1 = new SqlUser(66L, "stephen134@gmail.com",
                "322654", "stephen", "Chao",
                "stephen134@gmail.com", "321654987", 63,
                "male", "IE", "a:b:ab", "Active",
                Instant.now().toString(), Instant.now().toString());
        listUser.add(sqlUser);
        listUser.add(sqlUser1);

    }

    @DisplayName("保存user到DB")
    @Test
    public void addUserToDB() {

        Mockito.when(userToCreate.buildUserToRepository(userReq)).thenReturn(sqlUser);
        Mockito.when(userRepository.save(sqlUser)).thenReturn(sqlUser);

        SqlUser sqlUserAdd = userManagement.createNewUser(userReq);

        Assertions.assertSame(sqlUser, sqlUserAdd);
        Assertions.assertNotNull(sqlUserAdd);
        verify(userRepository, times(1)).save(sqlUser);
    }

    @DisplayName("Update user到DB")
    @Test
    public void update() throws UserNotFoundException {

        String userName = "stephen133@gmail.com";
        Mockito.when(userToUpdate.buildUserForUpdate(userReq, userName)).thenReturn(sqlUser);
        Mockito.when(userRepository.findById(userName)).thenReturn(Optional.ofNullable(sqlUser));
        Mockito.when(userRepository.save(sqlUser)).thenReturn(sqlUser);

        SqlUser sqlUserUpdate = userManagement.updateUser(userReq, userName);
        Assertions.assertSame(sqlUser, sqlUserUpdate);
        Assertions.assertNotNull(sqlUserUpdate);
        verify(userRepository, times(1)).save(sqlUser);

    }

    @DisplayName("查询所有User")
    @Test
    public void getAll() {

        Mockito.when(userRepository.findAll()).thenReturn(listUser);

        List<SqlUser> listReturned = userManagement.getAllUser();

        Assertions.assertNotNull(listReturned);
        //MatcherAssert.assertThat(listReturned.stream().map(),);
        verify(userRepository, times(1)).findAll();
    }

    @DisplayName("查询一个user")
    @Test
    public void getOne() throws UserNotFoundException {

        String userName = "stephen133@gmail.com";

        Mockito.when(userRepository.findById(userName)).thenReturn(Optional.ofNullable(sqlUser));

        SqlUser sqlUserReturned = userManagement.getOneUser(userName);

        Assertions.assertNotNull(sqlUserReturned);
        Assertions.assertSame(sqlUser, sqlUserReturned);
        Assertions.assertEquals("stephen133@gmail.com", sqlUserReturned.getUserName());

        verify(userRepository, times(1)).findById(userName);
    }

    @DisplayName("查询一个不存在user")
    @Test
    public void getOneButNotFound() throws UserNotFoundException{

        String username = "unknown@gmail.com";

        Mockito.when(userRepository.findById(username)).thenReturn(Optional.empty());
        //userService.getOne(username).;
        Assertions.assertThrows(UserNotFoundException.class, () -> userManagement.getOneUser(username));
        verify(userRepository, times(1)).findById(username);
    }

    @DisplayName("正常删除 ")
    @Test
    public void delete() throws UserNotFoundException {
        String userName = "stephen133@gmail.com";

        Mockito.when(userRepository.findById(userName)).thenReturn(Optional.ofNullable(sqlUser));

        Assertions.assertTrue(userManagement.deleteUser(userName));

        verify(userRepository, times(1)).delete(sqlUser);
    }

    @DisplayName("删除： User 不存在 ")
    @Test
    public void deleteUserNotFound() throws UserNotFoundException {

        String userName = "unknown@gmail.com";

        Mockito.when(userRepository.findById(userName)).thenThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () -> userManagement.deleteUser(userName));

        verify(userRepository, times(0)).deleteById(userName);
    }
}
