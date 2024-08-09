package com.example.clean_architecture.account.application.port.in;

import com.example.clean_architecture.account.domain.Account;
import com.example.clean_architecture.account.domain.Money;
import com.example.clean_architecture.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Gateway에서의 유효성 검증
 *
 * Gateway에서 입력 유효성 검사를 수행할 수도 있습니다.
 * 이 경우 Gateway는 기본적인 형식 검사(예: 필수 필드 확인, 데이터 타입 확인 등)를 통해 잘못된 요청을 미리 걸러낼 수 있습니다.
 * 이는 불필요한 요청이 내부 서비스에 전달되지 않도록 하여 성능을 최적화하고, 보안성을 높이는 데 도움이 됩니다.
 * 그러나, 이러한 유효성 검사는 주로 기술적인 측면에 초점을 맞추며,
 * 비즈니스 규칙과 관련된 검증은 여전히 도메인 계층에서 처리하는 것이 일반적입니다.
 */

@Getter
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final Account.AccountId sourceAccountId;

    @NotNull
    private final Account.AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(Account.AccountId sourceAccountId, Account.AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }

}
