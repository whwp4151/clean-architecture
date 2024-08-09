package com.example.clean_architecture.account.application.port.out;

import com.example.clean_architecture.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
