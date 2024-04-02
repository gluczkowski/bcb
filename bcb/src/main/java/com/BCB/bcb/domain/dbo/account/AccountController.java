package com.BCB.bcb.domain.dbo.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BCB.bcb.domain.dbo.account.dto.AccountDTO;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getClientAccount(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getClientAccount(id));
    }

    @PatchMapping
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO dto) {
        return ResponseEntity.ok(service.updateAccount(dto));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<AccountDTO>> findAll() {
        return ResponseEntity.ok(service.findAllAccounts());
    }

    @GetMapping("/find-all-page")
    public ResponseEntity<Page<AccountDTO>> findAllPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findAllAccountsPage(page, size));
    }
}
