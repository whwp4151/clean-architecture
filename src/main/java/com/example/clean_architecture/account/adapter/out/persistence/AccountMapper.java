package com.example.clean_architecture.account.adapter.out.persistence;

import com.example.clean_architecture.account.domain.Account;
import com.example.clean_architecture.account.domain.Activity;
import com.example.clean_architecture.account.domain.ActivityWindow;
import com.example.clean_architecture.account.domain.Money;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public Account mapToDomainEntity(
            AccountJpaEntity account,
            List<ActivityJpaEntity> activities,
            Long withdrawalBalance,
            Long depositBalance) {

        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance)
        );

        return Account.builder()
                .id(new Account.AccountId(account.getId()))
                .baselineBalance(baselineBalance)
                .activityWindow(mapToActivityWindow(activities))
                .build();
    }

    private ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = activities.stream()
                .map(activity ->
                        Activity.builder()
                                .id(new Activity.ActivityId(activity.getId()))
                                .ownerAccountId(new Account.AccountId(activity.getOwnerAccountId()))
                                .sourceAccountId(new Account.AccountId(activity.getSourceAccountId()))
                                .targetAccountId(new Account.AccountId(activity.getTargetAccountId()))
                                .timestamp(activity.getTimestamp())
                                .money(Money.of(activity.getAmount()))
                                .build()
                )
                .collect(Collectors.toCollection(ArrayList::new));

        return new ActivityWindow(mappedActivities);
    }

    public ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
                activity.getId() == null ? null : activity.getId().value(),
                activity.getTimestamp(),
                activity.getOwnerAccountId().value(),
                activity.getSourceAccountId().value(),
                activity.getTargetAccountId().value(),
                activity.getMoney().getAmount().longValue());
    }

}
