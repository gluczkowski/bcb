package com.BCB.bcb.domain.dbo.clientperson;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BCB.bcb.domain.dbo.account.Account;
import com.BCB.bcb.domain.dbo.account.AccountService;
import com.BCB.bcb.domain.dbo.client.dtos.BaseMessageDTO;
import com.BCB.bcb.domain.dbo.clientperson.dtos.ClientPersonDTO;
import com.BCB.bcb.domain.dbo.enums.AccountEnum;
import com.BCB.bcb.domain.dbo.exception.BadRequestException;
import com.BCB.bcb.domain.dbo.twilio.TwilioService;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class ClientPersonService {
    private static BigDecimal MESSAGE_VALUE = new BigDecimal(0.25);
    private final ClientPersonRepository repository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final TwilioService twilioService;

    public ClientPersonService(ClientPersonRepository repository, ModelMapper modelMapper,
            AccountService accountService, TwilioService twilioService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
        this.twilioService = twilioService;
    }

    public ClientPersonDTO newClientPerson(ClientPersonDTO dto) {
        Account account = createAccount(dto);
        dto.setAccount(account);
        ClientPerson savedPerson = this.repository.save(modelMapper.map(dto, ClientPerson.class));
        return modelMapper.map(savedPerson, ClientPersonDTO.class);
    }

    private Account createAccount(ClientPersonDTO dto) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setLimit(BigDecimal.ZERO);
        account.setCredit(BigDecimal.ZERO);
        account.setTypeAccount(AccountEnum.PRE);
        return modelMapper.map(accountService.save(account), Account.class);
    }

    public ClientPersonDTO updateClient(Integer id, ClientPersonDTO dto) {
        ClientPerson person = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Client not found"));
        person = modelMapper.map(dto, ClientPerson.class);
        accountService.updateAccount(person, dto);
        return modelMapper.map(repository.save(person), ClientPersonDTO.class);
    }

    public ClientPersonDTO findById(Integer id) {
        return modelMapper.map(
                this.repository.findById(id).orElseThrow(() -> new NoSuchElementException("Client not found")),
                ClientPersonDTO.class);
    }

    public List<ClientPersonDTO> findAllClients() {
        List<ClientPerson> clients = this.repository.findAll();
        return clients.stream().map(client -> modelMapper.map(client, ClientPersonDTO.class))
                .collect(Collectors.toList());
    }

    public Page<ClientPersonDTO> findAllClientesPage(int page, int size) {
        Page<ClientPerson> clientesPage = repository.findAll(PageRequest.of(page, size));
        return clientesPage.map(cliente -> modelMapper.map(cliente, ClientPersonDTO.class));
    }

    public Message sendMessage(BaseMessageDTO dto) throws Exception {
        ClientPerson client = this.repository.findById(dto.getIdClient())
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
                throw new BadRequestException("Insufficient limit.");
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
            // queria pegar o retorno da twilio, porém não ficou disponível o atributo
            // detail message para inserir;
            throw new ApiException("Error on sending the message : " + e.getMessage());
        }
    }

    private Boolean checkIfClientHaveEnoughCredit(ClientPerson client) {
        if (client.getAccount().getCredit().compareTo(MESSAGE_VALUE) >= 0) {
            client.getAccount().setCredit(client.getAccount().getCredit().subtract(MESSAGE_VALUE));
            accountService.save(client.getAccount());
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    private Boolean checkIfThereIsLimit(ClientPerson client) {
        client.getAccount().setBalance(client.getAccount().getBalance().add(MESSAGE_VALUE));
        if (client.getAccount().getBalance().compareTo(client.getAccount().getLimit()) <= 0) {
            accountService.save(client.getAccount());
            return true;
        }
        return false;
    }

    private void refoundPre(ClientPerson client) {
        accountService.refoundPre(client);
    }

    private void refoundPos(ClientPerson client) {
        accountService.refoundPos(client);
    }

}
