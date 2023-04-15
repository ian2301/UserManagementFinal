package com.example.repository;

import com.example.pojo.entity.SqlUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SqlUser ,String > {


}
