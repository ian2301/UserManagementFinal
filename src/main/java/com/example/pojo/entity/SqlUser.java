package com.example.pojo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@DynamicUpdate  //只将有变化的字段 去执行update，优化了速度
//@DynamicInsert
@Table(name = "app_user")
// @EntityListeners(AuditingEntityListener.class)  // 和@EnableJpaAuditing一起用， 使   @CreatedDate，@LastModifiedDate 生效
public class SqlUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Index_Number")
    private Long IndexNumber;

    @Id
    @Column(name = "user_name")
    private String userName;

    @NonNull
    @Column(name = "password")
    private String password;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_Number")
    private String contactNumber;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String  gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "tags")
    private String tags;

    @Column(name = "status")
    private String  status;

    @Column(name = "created")
    private String  created;

    @NotNull
    @Column(name = "updated")
    private String updated;
}
