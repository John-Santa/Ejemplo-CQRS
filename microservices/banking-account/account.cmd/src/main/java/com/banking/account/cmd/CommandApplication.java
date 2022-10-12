package com.banking.account.cmd;

import com.banking.account.cmd.api.command.*;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}

	// Recibir un arreglo y encontrar dos numeros que sumados den como resultado un numero dado
	// Ejemplo: [1,2,3,4,5,6,7,8,9,10] y 10
	// Resultado: 1,9
	// Resultado: 2,8
	// Resultado: 3,7
	// Resultado: 4,6
	// Resultado: 5,5
	// Resultado: 6,4
	// Resultado: 7,3
	// Resultado: 8,2
	// Resultado: 9,1
	// Resultado: 10,0
	

}
