package com.BCB.bcb.domain.dbo.client.dtos;

import com.BCB.bcb.domain.dbo.account.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseClienteDTO {
    private Integer id;
    private String name;
    private String telephone;
    private String email;
    private Account account;
    
}
