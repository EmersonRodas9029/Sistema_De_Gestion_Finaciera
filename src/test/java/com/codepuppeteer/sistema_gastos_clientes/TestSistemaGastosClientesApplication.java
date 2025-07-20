package com.codepuppeteer.sistema_gastos_clientes;

import org.springframework.boot.SpringApplication;

public class TestSistemaGastosClientesApplication {

	public static void main(String[] args) {
		SpringApplication.from(SistemaGastosClientesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
