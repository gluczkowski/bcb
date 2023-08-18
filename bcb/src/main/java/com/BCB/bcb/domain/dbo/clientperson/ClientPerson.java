package com.BCB.bcb.domain.dbo.clientperson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

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
@Table(name = "CLIENT_PERSON")
public class ClientPerson extends BaseClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENT_PERSON")
    private Integer id;
    
    @NotBlank
    @CPF(message = "Invalid cpf!")
    @Column(name = "CD_CPF", length = 11)
    private String cpf;
}


