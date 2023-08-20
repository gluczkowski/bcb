package com.BCB.bcb.domain.dbo.clientcompany;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.BCB.bcb.domain.dbo.account.Account;
import com.BCB.bcb.domain.dbo.account.AccountService;
import com.BCB.bcb.domain.dbo.client.dtos.BaseMessageDTO;
import com.BCB.bcb.domain.dbo.clientcompany.dtos.ClientCompanyDTO;
import com.BCB.bcb.domain.dbo.enums.AccountEnum;
import com.BCB.bcb.domain.dbo.exception.BadRequestException;
import com.BCB.bcb.domain.dbo.twilio.TwilioService;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class ClientCompanyService {
    private static BigDecimal MESSAGE_VALUE = new BigDecimal(0.25);
    private final ClientCompanyRepository repository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final TwilioService twilioService;

    public ClientCompanyService(ClientCompanyRepository repository, ModelMapper modelMapper,
            AccountService accountService, TwilioService twilioService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
        this.twilioService = twilioService;
    }

    public ClientCompanyDTO newClientCompany(ClientCompanyDTO dto) {
        Account account = createAccount(dto);
        dto.setAccount(account);
        ClientCompany savedPerson = this.repository.save(modelMapper.map(dto, ClientCompany.class));
        return modelMapper.map(savedPerson, ClientCompanyDTO.class);
    }

    private Account createAccount(ClientCompanyDTO dto) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setLimit(BigDecimal.ZERO);
        account.setCredit(BigDecimal.ZERO);
        account.setTypeAccount(AccountEnum.PRE);
        return modelMapper.map(accountService.save(account), Account.class);
    }

    public ClientCompanyDTO updateClient(Integer id, ClientCompanyDTO dto) {
        ClientCompany person = repository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
        person = modelMapper.map(dto, ClientCompany.class);
        accountService.updateAccount(person, dto);
        return modelMapper.map(repository.save(person), ClientCompanyDTO.class);
    }

    public ClientCompanyDTO findById(Integer id) {
        return modelMapper.map(
                this.repository.findById(id).orElseThrow(() -> new NoSuchElementException("Client not found")),
                ClientCompanyDTO.class);
    }

    public List<ClientCompanyDTO> findAllClients() {
        List<ClientCompany> clients = this.repository.findAll();
        return clients.stream().map(client -> modelMapper.map(client, ClientCompanyDTO.class))
                .collect(Collectors.toList());
    }

    public Page<ClientCompanyDTO> findAllClientesPage(int page, int size) {
        Page<ClientCompany> clientesPage = repository.findAll(PageRequest.of(page, size));
        return clientesPage.map(cliente -> modelMapper.map(cliente, ClientCompanyDTO.class));
    }

    public Message sendMessage(BaseMessageDTO dto) throws Exception {
        ClientCompany client = this.repository.findById(dto.getIdClient())
                .orElseThrow(() -> new NoSuchElementException("Client not found"));

        if (client.getAccount().getTypeAccount() == AccountEnum.PRE) {
            Boolean check = checkIfClientHaveEnoughCredit(client);
            if (!check) {
                throw new BadRequestException("Insufficient credits.");
            }
        }

        if (client.getAccount().getTypeAccount() == AccountEnum.POS) {
            Boolean check = checkIfThereIsLimit(client);
            if (!check) {
                throw new BadRequestException("Insufficient credits.");
            }
        }

        try {
            var response = twilioService.sendMessage(dto);
            return response;
        } catch (ApiException e) {
            if (client.getAccount().getTypeAccount() == AccountEnum.POS) {
                refoundPos(client);
            } else {
                refoundPre(client);
            }
            
            throw new ApiException("Error on sending the message : " + e.getMessage());
        }
    }

    private Boolean checkIfClientHaveEnoughCredit(ClientCompany client) {
        if (client.getAccount().getCredit().compareTo(MESSAGE_VALUE) >= 0) {
            client.getAccount().setCredit(client.getAccount().getCredit().subtract(MESSAGE_VALUE));
            accountService.save(client.getAccount());
            return true;
        }
        return false;
    }

    private Boolean checkIfThereIsLimit(ClientCompany client) {
        client.getAccount().setBalance(client.getAccount().getBalance().add(MESSAGE_VALUE));
        if (client.getAccount().getBalance().compareTo(client.getAccount().getLimit()) <= 0) {
            accountService.save(client.getAccount());
            return true;
        }
        return false;
    }

    

    private void refoundPre(ClientCompany client) {
        accountService.refoundPre(client);
    }

    private void refoundPos(ClientCompany client) {
        accountService.refoundPos(client);
    }

}
