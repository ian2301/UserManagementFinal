package com.example.service;

import com.example.Util.UserToCreate;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

 @ExtendWith(MockitoExtension.class)
public class UserToCreateTest {

    @Mock
    private UserDataFromAPIImp userDataFromWeb;
    @InjectMocks
    private UserToCreate userToCreate;

    @Test
    public void UserToAdd(){

        UserReq userReq= new UserReq("322654","stephen","Chao",
                "stephen133@gmail.com","321654987",
                Arrays.asList("a","b","aab"));

        Mockito.when(userDataFromWeb.GuessUserAge(userReq.getFirstName())).thenReturn(21);
        Mockito.when(userDataFromWeb.GuessUserGender(userReq.getFirstName())).thenReturn("male");
        Mockito.when(userDataFromWeb.GuessUserNation(userReq.getFirstName())).thenReturn("AU");

        SqlUser userReturn = userToCreate.buildUserToRepository(userReq);

        Assertions.assertEquals(21, (int) userReturn.getAge());
        Assertions.assertEquals("male",userReturn.getGender());
        Assertions.assertEquals(userReturn.getUserName(),userReq.getEmail());
    }
}
