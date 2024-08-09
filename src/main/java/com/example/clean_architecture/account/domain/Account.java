package com.example.clean_architecture.account.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    private AccountId id;
    private Money baselineBalance;
    private ActivityWindow activityWindow;

    public Money calculateBalance() {
        Money add = Money.add(
                this.baselineBalance,
                this.activityWindow.calculateBalance(this.id)
        );
        log.info("account id :: {}, calculateBalance :: {}", this.id.value, add.getAmount());
        return add;
    }

    public boolean withdraw(Money money, AccountId targetAccountId) {
        if (!mayWithdraw(money)) {
            log.error("account id :: {}, 출금불가 :: {}", this.id.value, money.getAmount());
            return false;
        }

        Activity withdrawal = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(this.id)
                .targetAccountId(targetAccountId)
                .timestamp(LocalDateTime.now())
                .money(money)
                .build();
        this.activityWindow.addActivity(withdrawal);

        return true;
    }

    // 비즈니스 규칙을 도메인 엔티티 안에 넣기
    private boolean mayWithdraw(Money money) {
        return Money.add(
                        this.calculateBalance(),
                        money.negate()
                )
                .isPositiveOrZero();
    }

    public boolean deposit(Money money, AccountId sourceAccountId) {
        log.info("deposit account id :: {}, money :: {}, source account id :: {}", this.id.value, money.getAmount(), sourceAccountId.value);

        Activity deposit = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(sourceAccountId)
                .targetAccountId(this.id)
                .timestamp(LocalDateTime.now())
                .money(money)
                .build();
        this.activityWindow.addActivity(deposit);
        return true;
    }

    public record AccountId(Long value) {
    }

}
