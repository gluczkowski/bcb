package com.BCB.bcb.domain.dbo.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.BCB.bcb.domain.dbo.account.dto.AccountDTO;
import com.BCB.bcb.domain.dbo.clientcompany.ClientCompany;
import com.BCB.bcb.domain.dbo.clientcompany.dtos.ClientCompanyDTO;
import com.BCB.bcb.domain.dbo.clientperson.ClientPerson;
import com.BCB.bcb.domain.dbo.clientperson.dtos.ClientPersonDTO;

@Service
public class AccountService {
    private static BigDecimal MESSAGE_VALUE = new BigDecimal(0.25);
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    public void updateAccount(ClientPerson person, ClientPersonDTO dto) {
        person.getAccount().setCredit(dto.getAccount().getCredit());
        person.getAccount().setLimit(dto.getAccount().getLimit());
        person.getAccount().setBalance(dto.getAccount().getBalance());
        person.getAccount().setTypeAccount(dto.getAccount().getTypeAccount());
        accountRepository.save(person.getAccount());
    }

    public void updateAccount(ClientCompany person, ClientCompanyDTO dto) {
        person.getAccount().setCredit(dto.getAccount().getCredit());
        person.getAccount().setLimit(dto.getAccount().getLimit());
        person.getAccount().setBalance(dto.getAccount().getBalance());
        person.getAccount().setTypeAccount(dto.getAccount().getTypeAccount());
        accountRepository.save(person.getAccount());
    }

    public AccountDTO save(Account account) {
        return modelMapper.map(accountRepository.save(account), AccountDTO.class);
    }

    public AccountDTO getClientAccount(Integer id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO updateAccount(AccountDTO dto) {
        Account account = this.accountRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Account not found"));

        account.setCredit(dto.getCredit() != null ? dto.getCredit() : null);
        account.setLimit(dto.getLimit() != null ? dto.getCredit() : null);
        account.setBalance(dto.getBalance() != null ? dto.getBalance() : null);
        account.setTypeAccount(dto.getTypeAccount() != null ? dto.getTypeAccount() : null);

        return modelMapper.map(this.accountRepository.save(account), AccountDTO.class);
    }

    public List<AccountDTO> findAllAccounts() {
        List<Account> clients = this.accountRepository.findAll();
        return clients.stream().map(client -> modelMapper.map(client, AccountDTO.class))
                .collect(Collectors.toList());
    }

    public Page<AccountDTO> findAllAccountsPage(int page, int size) {
        Page<Account> clientesPage = accountRepository.findAll(PageRequest.of(page, size));
        return clientesPage.map(cliente -> modelMapper.map(cliente, AccountDTO.class));
    }

    public void refoundPre(ClientPerson client) {
        client.getAccount().getCredit().add(MESSAGE_VALUE);
        accountRepository.save(client.getAccount());
    }

    public void refoundPos(ClientPerson client) {
        client.getAccount().getBalance().subtract(MESSAGE_VALUE);
        accountRepository.save(client.getAccount());
    }

    public void refoundPre(ClientCompany client) {
        client.getAccount().getCredit().add(MESSAGE_VALUE);
        accountRepository.save(client.getAccount());
    }

    public void refoundPos(ClientCompany client) {
        client.getAccount().getBalance().subtract(MESSAGE_VALUE);
        accountRepository.save(client.getAccount());
    }

}
