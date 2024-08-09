package com.example.clean_architecture.account.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Activity {

    private ActivityId id;

    private final Account.AccountId ownerAccountId;

    private final Account.AccountId sourceAccountId;

    private final Account.AccountId targetAccountId;

    private final LocalDateTime timestamp;

    private final Money money;

    public record ActivityId(Long value) {
    }

}
