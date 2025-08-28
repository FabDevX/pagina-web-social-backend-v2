package com.infsis.socialpagebackend.events.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class EventDTO {
    // public String institutionUuid; // Dejar comentado
    // public String categoryUuid;    // Dejar comentado
    public String location;
    public String title;
    public String description;
    public Date startDate;
    public Date endDate;
    public Integer maxCapacity;
    public Boolean isPublic;
    public Boolean requiresRegistration;
    public String status;
    public String coverImagePath;
}

