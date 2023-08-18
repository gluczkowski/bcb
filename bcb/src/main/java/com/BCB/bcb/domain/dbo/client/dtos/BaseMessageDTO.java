package com.BCB.bcb.domain.dbo.client.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseMessageDTO {
    private Integer idClient;
    private String message;
    private Boolean whatsapp;
    private String number;
}
