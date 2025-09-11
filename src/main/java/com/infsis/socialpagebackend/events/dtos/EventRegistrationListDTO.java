package com.infsis.socialpagebackend.events.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class EventRegistrationListDTO {
    private String registrationUuid;
    private Date registrationDate;
    private String status;
    private String userUuid;
    private String userName;
    private String userEmail;
    private String userLastname;
    private String photoProfilePath;
}
