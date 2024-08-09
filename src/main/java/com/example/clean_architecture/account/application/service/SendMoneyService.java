package com.example.clean_architecture.account.application.service;

import com.example.clean_architecture.account.application.port.in.SendMoneyCommand;
import com.example.clean_architecture.account.application.port.in.SendMoneyUseCase;
import com.example.clean_architecture.account.application.port.out.AccountLock;
import com.example.clean_architecture.account.application.port.out.LoadAccountPort;
import com.example.clean_architecture.account.application.port.out.UpdateAccountStatePort;
import com.example.clean_architecture.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 1. 입력을 받는다.
 * 2. 비즈니스 규칙을 검증한다.
 * 3. 모델 상태를 조작한다.
 * 4. 출력을 반환한다.
 */
@Service
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
        Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);

        Account.AccountId sourceAccountId = sourceAccount.getId();
        Account.AccountId targetAccountId = targetAccount.getId();

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

}
