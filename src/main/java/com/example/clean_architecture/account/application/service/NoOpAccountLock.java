package com.example.clean_architecture.account.application.service;

import com.example.clean_architecture.account.application.port.out.AccountLock;
import com.example.clean_architecture.account.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(Account.AccountId accountId) {
        log.info("lockAccount account id :: {}", accountId.value());
    }

    @Override
    public void releaseAccount(Account.AccountId accountId) {
        log.info("releaseAccount account id :: {}", accountId.value());
    }

}
