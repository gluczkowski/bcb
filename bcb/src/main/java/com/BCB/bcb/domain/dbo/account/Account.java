package com.BCB.bcb.domain.dbo.account;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.BCB.bcb.domain.dbo.enums.AccountEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCOUNT")
    private Integer id;

    @Column(name = "VL_BALANCE")
    private BigDecimal balance;

    @Column(name = "VL_LIMIT")
    private BigDecimal limit;

    @Column(name = "VL_CREDIT")
    private BigDecimal credit;

    @Column(name = "TP_ACCOUNT")
    @Enumerated(EnumType.STRING)
    private AccountEnum typeAccount;    

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.limit = BigDecimal.ZERO;
        this.credit = BigDecimal.ZERO;
        this.typeAccount = AccountEnum.PRE;
    }
}
