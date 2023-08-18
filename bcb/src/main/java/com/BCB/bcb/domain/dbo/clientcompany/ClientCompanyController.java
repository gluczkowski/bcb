package com.BCB.bcb.domain.dbo.clientcompany;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BCB.bcb.domain.dbo.client.dtos.BaseMessageDTO;
import com.BCB.bcb.domain.dbo.clientcompany.dtos.ClientCompanyDTO;
import com.twilio.rest.api.v2010.account.Message;

@RestController
@RequestMapping(value = "/client-company")
public class ClientCompanyController {

    private final ClientCompanyService service;    

    public ClientCompanyController(ClientCompanyService service) {
        this.service = service;        
    }

    @PostMapping()
    public ClientCompanyDTO newClientCompany(@RequestBody ClientCompanyDTO dto) {
        return service.newClientCompany(dto);
    }

    @PatchMapping("/update-client/{id}")
    public void updateClient(@PathVariable Integer id, @RequestBody ClientCompanyDTO dto) {
        service.updateClient(id, dto);
    }

    @GetMapping("/{id}")
    public ClientCompanyDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/find-all")
    public List<ClientCompanyDTO> findAll() {
        return service.findAllClients();
    }

    @GetMapping("/find-all-page")
    public Page<ClientCompanyDTO> findAllPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAllClientesPage(page, size);
    }

    @PostMapping("/send-message")
    public Message sendMessage(@RequestBody BaseMessageDTO dto) throws Exception{
        return service.sendMessage(dto);        
    }
}
