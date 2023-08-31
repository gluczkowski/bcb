package com.BCB.bcb.domain.dbo.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.BCB.bcb.domain.dbo.account.dto.AccountDTO;
import com.BCB.bcb.domain.dbo.clientcompany.ClientCompany;
import com.BCB.bcb.domain.dbo.clientcompany.ClientCompanyService;
import com.BCB.bcb.domain.dbo.enums.AccountEnum;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @InjectMocks
    private ClientCompanyService clientCompanyService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testSave() {
        Account account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(100));
        account.setLimit(BigDecimal.valueOf(100));
        account.setCredit(BigDecimal.valueOf(100));
        account.setTypeAccount(AccountEnum.PRE);

        AccountDTO expectedDTO = new AccountDTO();
        expectedDTO.setId(1);
        expectedDTO.setBalance(BigDecimal.valueOf(100));
        expectedDTO.setLimit(BigDecimal.valueOf(100));
        expectedDTO.setCredit(BigDecimal.valueOf(100));
        expectedDTO.setTypeAccount(AccountEnum.PRE);

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(expectedDTO);
        AccountDTO resultDTO = accountService.save(account);

        verify(accountRepository).save(account);
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    public void testRefundPos() {

        Account account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(100));

        ClientCompany client = new ClientCompany();
        client.setAccount(account);

        accountService.refoundPos(client);

        verify(accountRepository).save(account);

        assertEquals(BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(0.25)), account.getBalance());
    }

}
