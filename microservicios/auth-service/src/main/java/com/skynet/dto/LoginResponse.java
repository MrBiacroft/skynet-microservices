package com.skynet.dto;

import com.skynet.model.Usuario;

public class LoginResponse {
    private String token;
    private Usuario usuario;
    private String mensaje;
    
    // Constructores
    public LoginResponse() {}
    
    public LoginResponse(String token, Usuario usuario, String mensaje) {
        this.token = token;
        this.usuario = usuario;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
