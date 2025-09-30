package com.infsis.socialpagebackend.events.dtos;

import lombok.Data;

@Data
public class EventUserDTO {
    private String uuid;
    private String name;
    private String lastName;
    private String email;
}

