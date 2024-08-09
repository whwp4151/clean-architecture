package com.example.clean_architecture.account.application.adapter.out;

import com.example.clean_architecture.account.application.port.out.LoadAccountPort;
import com.example.clean_architecture.account.application.port.out.UpdateAccountStatePort;
import com.example.clean_architecture.account.domain.Account;
import com.example.clean_architecture.account.domain.Activity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements
        LoadAccountPort,
        UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final SpringDataActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate) {
        AccountJpaEntity account = accountRepository.findById(accountId.value())
                .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerAccountIdAndTimestampGreaterThanEqual(accountId.value(), baselineDate);

        Long withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(accountId.value(), baselineDate));

        Long depositBalance = orZero(activityRepository.getDepositBalanceUntil(accountId.value(), baselineDate));

        log.info("accountId :: {}, withdrawalBalance :: {}, depositBalance ;: {}", accountId.value(), withdrawalBalance, depositBalance);

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance
        );
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

}
