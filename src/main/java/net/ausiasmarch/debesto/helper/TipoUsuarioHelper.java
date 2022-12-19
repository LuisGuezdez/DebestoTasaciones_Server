package net.ausiasmarch.debesto.helper;

public enum TipoUsuarioHelper {
    EMPLEADO(1L), CLIENTE(2L);
    private final Long TipoUsuario;

    TipoUsuarioHelper(Long TipoUsuario) {
        this.TipoUsuario = TipoUsuario;
    }

    public Long getTipoUsuario() {
        return TipoUsuario;
    }       

}
