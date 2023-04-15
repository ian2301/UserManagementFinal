package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 接收request body 数据源
 */
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReq {

    @NotBlank(message = "password can not be blank") // 用于检查输入的数据是否为空
    private String password;

    // @JsonProperty("name") //bean转成json字符串后，@JsonProperty注解的bean属性名已经替换成了指定属性名：name;正反序列化都影响
    @JsonAlias("name")  //只在反序列化中起作用，可以多个
    @NonNull
    @Length(min = 3, max = 50, message = "min 2, max 50") //检验字符长度
    private String firstName;

    @NonNull //不能为空值
    @Length(min = 3, max = 50, message = "min 2, max 50")
    private String lastName;

    @NotBlank(message = "email can not be blank")  // 用于检查输入的数据是否为空
    @Email(message = "email is not correct")
    private String email;

    @NonNull
    @Length(min = 8, max = 10, message = " must be 8-10") //检验字符长度
    private String contactNumber;

    private List<String> tags;

}
