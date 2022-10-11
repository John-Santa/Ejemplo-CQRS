package com.banking.account.cmd.domain;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawEvent;
import com.banking.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private Double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(Double amount) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new IllegalStateException("Los fondos no pueden ser depositados en esta cuenta");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new IllegalStateException("La cuenta bancaria esta cerrada");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        if (this.balance - amount < 0) {
            throw new IllegalStateException("No hay fondos suficientes para retirar");
        }

        raiseEvent(FundsWithdrawEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new IllegalStateException("La cuenta bancaria ya esta cerrada");
        }
        if (this.balance > 0) {
            throw new IllegalStateException("La cuenta bancaria no puede ser cerrada, tiene fondos");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }

    public Double getBalance() {
        return this.balance;
    }
}
