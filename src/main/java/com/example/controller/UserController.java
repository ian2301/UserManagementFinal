package com.example.controller;

import com.example.exception.UserNotFoundException;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.service.UserManagement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserManagement userManagement; //

    @PostMapping("/add")
    public ResponseEntity<SqlUser> addUser(@RequestBody @Valid UserReq userReq) { //  @Valid用于检查输入的数据是否合法

        SqlUser newUser = userManagement.createNewUser(userReq);
        //return new ResponseEntity<>.( HttpStatus.CREATED);//修改response的返回值为： 201 Created
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<SqlUser> update(@RequestBody UserReq userReq, @PathVariable String userName) throws UserNotFoundException {
        SqlUser sqlUser = userManagement.updateUser(userReq, userName);
        return  ResponseEntity.ok(sqlUser);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<SqlUser>> listUsers() {
        return ResponseEntity.ok().body(userManagement.getAllUser());
    }

    @GetMapping("/getById/{userName}")
    public ResponseEntity<SqlUser> getById(@PathVariable String userName) throws UserNotFoundException {
        SqlUser user = userManagement.getOneUser(userName);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/deleteById/{userName}")  //按id删除
    public ResponseEntity<String> deleteById(@PathVariable String userName) throws UserNotFoundException {
        userManagement.deleteUser(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
