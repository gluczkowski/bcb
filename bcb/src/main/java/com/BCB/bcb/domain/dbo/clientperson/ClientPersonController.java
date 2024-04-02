package com.BCB.bcb.domain.dbo.clientperson;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import com.BCB.bcb.domain.dbo.clientperson.dtos.ClientPersonDTO;
import com.twilio.http.Response;
import com.twilio.rest.api.v2010.account.Message;

@RestController
@RequestMapping(value = "/client-person")
public class ClientPersonController {

    private final ClientPersonService service;    

    public ClientPersonController(ClientPersonService service) {
        this.service = service;        
    }

    @PostMapping()
    public ResponseEntity<ClientPersonDTO> newClientPerson(@RequestBody ClientPersonDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newClientPerson(dto));
    }

    @PatchMapping("/update-client/{id}")
    public void updateClient(@PathVariable Integer id, @RequestBody ClientPersonDTO dto) {
        service.updateClient(id, dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientPersonDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ClientPersonDTO>> findAll() {
        return ResponseEntity.ok(service.findAllClients());
    }

    @GetMapping("/find-all-page")
    public ResponseEntity<Page<ClientPersonDTO>> findAllPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findAllClientesPage(page, size));
    }

    @PostMapping("/send-message")
    public ResponseEntity<Message> sendMessage(@RequestBody BaseMessageDTO dto) throws Exception{
        return ResponseEntity.accepted().body(service.sendMessage(dto));        
    }
    
}
