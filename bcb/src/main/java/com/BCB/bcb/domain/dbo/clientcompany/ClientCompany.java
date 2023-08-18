package com.BCB.bcb.domain.dbo.clientcompany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import com.BCB.bcb.domain.dbo.client.BaseClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENT_COMPANY")
public class ClientCompany extends BaseClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENT_COMPANY")
    private Integer id;    
    
    @NotBlank
    @CNPJ(message = "Invalid CNPJ!")
    @Column(name = "CD_CNPJ", length = 14)
    private String cnpj;
}


