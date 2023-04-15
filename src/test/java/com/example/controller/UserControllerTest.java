package com.example.controller;

import com.example.pojo.UserReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Integration Testing ,
 * 加载上下文运行环境及所有bean，
 * 测试所有class, MockMvc模拟Http,发送Http请求
 * OK
 */
@Slf4j
@SpringBootTest // 加载所有的bean  //@WebMvcTest : 轻量级： 只加载UserController的相关bean @WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc// 自动构建MVC,
@ExtendWith(SpringExtension.class)//这3条注解加载上下文环境，自动构建service容器环境。
@Transactional  //:开启事务功能
@Rollback  // 事务回滚,默认是true
public class UserControllerTest {

    //创建Mock对象，用于发送http请求
    @Resource
    //p配合@AutoConfigureMockMvc， 自动创建MockMvcBuilders. 可以省去编码@Before的 setup码段, private static MockMvc mockMvc中的static要去掉。
    private MockMvc mockMvc;

    /**
     * //@BeforeAll
     * static void setUp(){
     * //对mockMvc初始化
     * mockMvc =MockMvcBuilders.standaloneSetup(new UserController()).build();
     * }
     */

    @Test
    @DisplayName("测试 Post,  ")
    public void saveUser() throws Exception {
        //创建request body
        UserReq userReq = new UserReq("322654", "cindy", "Chao",
                "cindy000@gmail.com", "321654987", Arrays.asList("a", "b", "aab"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .request(HttpMethod.POST, "/api/users/add")
                                .contentType("application/json")
                                .content(new ObjectMapper().writeValueAsString(userReq))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("322654"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("cindy000@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("cindy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactNumber").value("321654987"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").value("a:b:aab"))
                .andDo(print())
                .andReturn();

        log.info("Posted =" + mvcResult.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("测试 get all,  ")
    void list() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .request(HttpMethod.GET, "/api/users/listAll")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        log.info("list all: " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试 getById,  ")
    void getById() throws Exception {

        String userName = "cindy@gmail.com";
        //String userName ="unknownuser@gmail.com";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                //.get("/api/user-management/users"+"/*")   // 可用
                                .request(HttpMethod.GET, "/api/users/getById/" + userName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("cindy@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("325544"))
                .andDo(print())
                .andReturn();
        log.info("get userName=" + userName + ":" + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试 getById, NoUserFound ")
    void getByIdNoUserFound() throws Exception {

        String userName = "unknown@gmail.com";
        //String userName ="unknownuser@gmail.com";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                //.get("/api/user-management/users"+"/*")   // 可用
                                .request(HttpMethod.GET, "/api/users/getById/" + userName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
        log.info("No user found: userName =" + userName);
    }

    @Test
    @DisplayName("测试 PUT,  ")
    void update() throws Exception {

        //创建request body
        String userName = "cindy@gmail.com";
        UserReq userReq = new UserReq("322654", "stephen", "Chao",
                "cindy@gmail.com", "321654987", Arrays.asList("a", "b", "aab"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.PUT, "/api/users/update/" + userName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userReq))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("cindy@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("322654"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").value("a:b:aab"))
                .andDo(print())
                .andReturn();

        log.info("updated userName =" + userName + ": " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试 PUT, No User Found ")
    void updateNoUserFound() throws Exception {

        //创建request body
        String userName = "unknown@gmail.com";
        UserReq userReq = new UserReq("322654", "stephen", "Chao",
                "cindy@gmail.com", "321654987", Arrays.asList("a", "b", "aab"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.PUT, "/api/users/update/" + userName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userReq))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();

        log.info("No user found: userName =" + userName);
    }


    @DisplayName("测试 Delete, ")
    @ParameterizedTest
    @ValueSource(strings = {"cindy@gmail.com", "stephen11@gmail.com"})
    void deleteById(String userName) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/deleteById/" + userName)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        log.info("deleted userName=" + userName);
    }

    @Test
    @DisplayName("测试 Delete, No user Found")
    void deleteByIdUserNotFound() throws Exception {

        String userName = "unknownuser@gmail.com";

         mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/deleteById/" + userName)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}


