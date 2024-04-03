package com.BCB.bcb.domain.dbo.account.dto;

import java.math.BigDecimal;

import com.BCB.bcb.domain.dbo.enums.AccountEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Integer id;
    private BigDecimal balance;
    private BigDecimal limit;
    private BigDecimal credit;    
    private AccountEnum typeAccount;
}
