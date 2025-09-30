package com.infsis.socialpagebackend.events.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class EventDetailDTO {
    private String uuid;
    private String title;
    private String location;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer maxCapacity;
    private Boolean isPublic;
    private Boolean requiresRegistration;
    private String status;
    private String coverImagePath;
    private EventUserDTO creator;
}

