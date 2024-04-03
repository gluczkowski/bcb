package com.BCB.bcb.domain.dbo.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.BCB.bcb.domain.dbo.account.dto.AccountDTO;
import com.BCB.bcb.domain.dbo.clientcompany.ClientCompany;
import com.BCB.bcb.domain.dbo.clientperson.ClientPerson;
import com.BCB.bcb.domain.dbo.enums.AccountEnum;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testSaveNewAccount() {
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

        verify(accountRepository, times(1)).save(account);
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    public void getClientAccountById() {
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

        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(account));
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(expectedDTO);

        AccountDTO result = accountService.getClientAccount(1);

        verify(accountRepository, times(1)).findById(1);
        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void updateAccountWithAccountDTO() {

        Account account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(100));
        account.setLimit(BigDecimal.valueOf(100));
        account.setCredit(BigDecimal.valueOf(100));
        account.setTypeAccount(AccountEnum.PRE);

        AccountDTO dto = new AccountDTO();
        dto.setId(1);
        dto.setBalance(BigDecimal.valueOf(100));
        dto.setLimit(BigDecimal.valueOf(100));
        dto.setCredit(BigDecimal.valueOf(100));
        dto.setTypeAccount(AccountEnum.PRE);

        AccountDTO expectedDTO = new AccountDTO();
        expectedDTO.setId(1);
        expectedDTO.setBalance(BigDecimal.valueOf(100));
        expectedDTO.setLimit(BigDecimal.valueOf(100));
        expectedDTO.setCredit(BigDecimal.valueOf(100));
        expectedDTO.setTypeAccount(AccountEnum.PRE);

        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(expectedDTO);

        AccountDTO result = accountService.updateAccount(dto);

        verify(accountRepository, times(1)).findById(1);
        assertNotNull(result);
        assertEquals(expectedDTO, result);

    }

    @Test
    public void testFindAllAccounts() {

        when(accountRepository.findAll()).thenReturn(Arrays.asList(new Account(), new Account()));

        when(modelMapper.map(Mockito.any(Account.class), Mockito.eq(AccountDTO.class))).thenReturn(new AccountDTO());

        List<AccountDTO> result = accountService.findAllAccounts();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindAllAccountsPage() {

        Pageable pageable = PageRequest.of(0, 2);
        Page<Account> accountPage = new PageImpl<>(Arrays.asList(new Account(), new Account()), pageable, 2);

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);
        when(modelMapper.map(Mockito.any(Account.class), Mockito.eq(AccountDTO.class))).thenReturn(new AccountDTO());

        Page<AccountDTO> result = accountService.findAllAccountsPage(0, 2);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void testRefoundPreClient() {

        ClientPerson client = mock(ClientPerson.class);
        Account account = new Account();
        client.setAccount(account);
        account.setCredit(BigDecimal.ZERO);

        when(client.getAccount()).thenReturn(account);

        accountService.refoundPre(client);

        assertEquals(BigDecimal.valueOf(0.25), account.getCredit());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testRefoundPosClient(){
        ClientPerson client = mock(ClientPerson.class);
        Account account = new Account();
        client.setAccount(account);
        account.setBalance(BigDecimal.valueOf(0.25));

        when(client.getAccount()).thenReturn(account);

        accountService.refoundPos(client);

        assertEquals(BigDecimal.ZERO, account.getCredit());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testRefoundPreCompany() {

        ClientCompany clientCompany = mock(ClientCompany.class);
        Account account = new Account();
        clientCompany.setAccount(account);
        account.setCredit(BigDecimal.ZERO);

        when(clientCompany.getAccount()).thenReturn(account);

        accountService.refoundPre(clientCompany);

        assertEquals(BigDecimal.valueOf(0.25), account.getCredit());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testRefundPosCompany() {

        Account account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(100));

        ClientCompany company = new ClientCompany();
        company.setAccount(account);

        accountService.refoundPos(company);

        verify(accountRepository).save(account);
        assertEquals(BigDecimal.valueOf(99.75), account.getBalance());
    }

}
