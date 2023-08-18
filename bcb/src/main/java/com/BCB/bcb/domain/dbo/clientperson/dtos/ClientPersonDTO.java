package com.BCB.bcb.domain.dbo.clientperson.dtos;

import com.BCB.bcb.domain.dbo.client.dtos.BaseClienteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientPersonDTO extends BaseClienteDTO {
    private Integer id;
    private String cpf;
}  
