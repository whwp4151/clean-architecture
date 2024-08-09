package com.example.clean_architecture.account.adapter.in.web;

import com.example.clean_architecture.account.application.port.in.SendMoneyCommand;
import com.example.clean_architecture.account.application.port.in.SendMoneyUseCase;
import com.example.clean_architecture.account.domain.Account;
import com.example.clean_architecture.account.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    public ResponseEntity<Boolean> sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount
    ) {
        SendMoneyCommand command = new SendMoneyCommand(
                new Account.AccountId(sourceAccountId),
                new Account.AccountId(targetAccountId),
                Money.of(amount));

        return ResponseEntity.ok(sendMoneyUseCase.sendMoney(command));
    }

}
