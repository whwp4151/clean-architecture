package com.example.clean_architecture.account.application.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

    List<ActivityJpaEntity> findByOwnerAccountIdAndTimestampGreaterThanEqual(Long ownerAccountId, LocalDateTime since);

    @Query("select sum(a.amount) from ActivityJpaEntity a " +
            "where a.targetAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    Long getDepositBalanceUntil(@Param("accountId") Long accountId,
                                @Param("until") LocalDateTime until);

    @Query("select sum(a.amount) from ActivityJpaEntity a " +
            "where a.sourceAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    Long getWithdrawalBalanceUntil(@Param("accountId") Long accountId,
                                   @Param("until") LocalDateTime until);

}
