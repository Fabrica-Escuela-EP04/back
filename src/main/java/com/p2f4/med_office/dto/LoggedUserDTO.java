package com.p2f4.med_office.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserDTO {

    private Integer idUser;
    private String name;
    private String lastName;
    private String email;
    private String userRole;
}
