package com.p2f4.med_office.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicDTO {
    private Integer idClinic;
    private String name;
    private String type;
    private String location;
    private String phoneNumber;
    private String status;
    
    public ClinicDTO() {
    }

    public ClinicDTO(Integer idClinic, String name, String type, String location, String phoneNumber, String status) {
        this.idClinic = idClinic;
        this.name = name;
        this.type = type;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

}
