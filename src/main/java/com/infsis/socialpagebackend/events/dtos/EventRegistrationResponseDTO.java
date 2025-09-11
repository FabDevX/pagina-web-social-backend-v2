package com.infsis.socialpagebackend.events.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRegistrationResponseDTO {
    private boolean success;
    private String message;
    private String registrationUuid;
    private String eventUuid;
    private String userUuid;
    private Date registrationDate;
    private String status;
}

