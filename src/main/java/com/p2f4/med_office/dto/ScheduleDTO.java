package com.p2f4.med_office.dto;

import java.time.LocalDateTime;

public class ScheduleDTO {

    private Long idSchedule;
    private Long idUser;
    private String type;
    private Integer idOffice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
