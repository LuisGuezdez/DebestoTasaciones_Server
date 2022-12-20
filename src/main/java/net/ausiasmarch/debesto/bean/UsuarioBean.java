package net.ausiasmarch.debesto.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioBean {

    
    private String username = "";
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contraseña = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    

}
