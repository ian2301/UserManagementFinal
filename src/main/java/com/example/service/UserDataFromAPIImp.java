package com.example.service;

import com.example.pojo.Age;
import com.example.pojo.Gender;
import com.example.pojo.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * get：  age from agify.io,
 * gender from Genderize.io ,
 * nationality from Nationalize.io
 * * @return
 */

@Component
public class UserDataFromAPIImp implements UserDataFromAPI {

    @Autowired
    RestTemplate restTemplate;

      @Override
    public int GuessUserAge(String name) throws RuntimeException{

          Age age = restTemplate
                  .getForObject("https://api.agify.io?name={1}", Age.class, name);
          return age.getAge();

    }

    @Override
    public String GuessUserGender(String name) throws RuntimeException{

        Gender gender = restTemplate
                .getForObject("https://api.Genderize.io?name={1}", Gender.class, name);
        //System.out.println(gender.getGender());
        return gender.getGender();


    }

    @Override
    public String GuessUserNation(String name) throws RuntimeException {

        Nation nation = restTemplate
                .getForObject("https://api.Nationalize.io?name={1}", Nation.class, name);
        nation.getCountry().sort(((o1, o2) -> o2.getCountry_id().compareTo(o1.getCountry_id())));
        String nationId = nation.getCountry().get(0).getCountry_id();
        //System.out.println("nation id is :"+nationId);
        return nationId;

    }
}
