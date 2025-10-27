package com.p2f4.med_office.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO {
    private String email;
    private String password;

    public CredentialsDTO() {
    }

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
