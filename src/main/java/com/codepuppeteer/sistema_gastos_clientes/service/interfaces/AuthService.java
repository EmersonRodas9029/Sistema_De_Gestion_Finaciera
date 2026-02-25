package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.auth.LoginRequest;
import com.codepuppeteer.sistema_gastos_clientes.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
