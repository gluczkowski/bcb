package com.BCB.bcb.domain.dbo.client;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.BCB.bcb.domain.dbo.account.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public class BaseClient {    

    @Column(name = "DS_NAME")
    private String name;

    @Column(name = "NR_TELEFONE")
    private String telephone;

    @Column(name = "DS_EMAIL")
    private String email;

    @JoinColumn(name = "ID_CONTA")
    @ManyToOne
    private Account account;

    public BaseClient() {
        this.account = new Account();
    }
}
