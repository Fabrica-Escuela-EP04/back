package com.p2f4.med_office.dto;

import java.time.LocalDateTime;

public class ScheduleDTO {

    private Long idSchedule;
    private Long idUser;
    private String type;
    private Integer idOffice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ScheduleDTO(){
    }

    public ScheduleDTO(Long idSchedule, Long idUser, String type, Integer idOffice, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.idSchedule = idSchedule;
        this.idUser = idUser;
        this.type = type;
        this.idOffice = idOffice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(Integer idOffice) {
        this.idOffice = idOffice;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }


}
