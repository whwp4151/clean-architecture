package com.example.clean_architecture.account.application.port.out;

import com.example.clean_architecture.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);

}
