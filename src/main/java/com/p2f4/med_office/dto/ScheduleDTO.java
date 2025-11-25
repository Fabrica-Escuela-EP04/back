package com.p2f4.med_office.dto;

import java.time.LocalDate;

public class ScheduleDTO {

    private Integer idSchedule;
    private Integer idUser;
    private String type;
    private Integer idOffice;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public ScheduleDTO(){
    }

    public ScheduleDTO(Integer idSchedule, Integer idUser, String type, Integer idOffice, LocalDate startDate,
            LocalDate endDate, String status) {
        this.idSchedule = idSchedule;
        this.idUser = idUser;
        this.type = type;
        this.idOffice = idOffice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Integer getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(Integer idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}