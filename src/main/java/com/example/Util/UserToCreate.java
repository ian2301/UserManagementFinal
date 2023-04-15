package com.example.Util;

import com.example.pojo.Status;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.service.UserDataFromAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class UserToCreate {
    @Autowired
    private UserDataFromAPI userDataFromAPI;

    public SqlUser buildUserToRepository(UserReq userReq) {

        //build SqlUser to be saved to DB
        SqlUser userToRepo = SqlUser.builder()
                .userName(userReq.getEmail())
                .password(userReq.getPassword())
                .firstName(userReq.getFirstName())
                .lastName(userReq.getLastName())
                .email(userReq.getEmail())
                .contactNumber(userReq.getContactNumber())
                .tags(String.join(":", userReq.getTags()))
                .age(userDataFromAPI.GuessUserAge(userReq.getFirstName()))
                .gender(userDataFromAPI.GuessUserGender(userReq.getFirstName()))
                .nationality(userDataFromAPI.GuessUserNation(userReq.getFirstName()))
                .status(String.valueOf(Status.active))
                .created(OffsetDateTime.now(ZoneOffset.UTC).toString())  // Instant.now().toString(): 时间戳
                .updated(Instant.now().toString())
                .build();

        /**
         * Instant.now(): the basic building block in java.time, representing a moment on the timeline in UTC with a resolution of nanoseconds.
         * toString method generates a String representation of its value using one specific ISO 8601 format.
         * That format outputs zero, three, six or nine digits digits (milliseconds, microseconds,
         *    or nanoseconds) as necessary to represent the fraction-of-second.
         *  OffsetDateTime class is one of the important class to get current UTC time.
         *  OffsetDateTime is an immutable representation of a date-time with an offset
         *    that is mainly used for storing date-time fields into the precision of nanoseconds,
         *
         *    for UTC itself (ZoneOffset.UTC constant) to get a OffsetDateTime.
         *    OffsetDateTime now = OffsetDateTime.now( ZoneOffset.UTC );
         *
         *    DateTime.now().toDateTime(DateTimeZone.UTC)
         */
        return userToRepo;
    }
}
