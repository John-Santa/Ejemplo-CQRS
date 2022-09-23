package com.banking.cqrs.core.infrastructure;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> commandType, CommandHandlerMethod<T> handlerMethod);
    void send(BaseCommand command);
}
