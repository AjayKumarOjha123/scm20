package com.scm.scm20.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;
}
