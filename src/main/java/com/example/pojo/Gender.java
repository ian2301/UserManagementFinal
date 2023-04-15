package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * data from https://api.agify.io?name=
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gender {
    private int count;
    private String gender;
    private String name;
    private double probability;
}
