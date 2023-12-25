package com.belajar.springbootfilter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserData {

    private String fullName;

    private String email;

    private String phoneNumber;

}
