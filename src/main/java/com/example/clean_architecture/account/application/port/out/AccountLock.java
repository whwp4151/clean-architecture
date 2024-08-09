package com.example.clean_architecture.account.application.port.out;

import com.example.clean_architecture.account.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}
