package com.BCB.bcb.domain.dbo.clientcompany.dtos;

import com.BCB.bcb.domain.dbo.client.dtos.BaseClienteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCompanyDTO extends BaseClienteDTO {

    private Integer id;
    private String cnpj;
}
