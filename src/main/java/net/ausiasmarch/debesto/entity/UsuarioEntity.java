package net.ausiasmarch.debesto.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private String email;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contraseña;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<CocheEntity> coches;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipousuario")
    private TipousuarioEntity tipousuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private SucursalEntity sucursal;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private final List<TasacionEntity> tasaciones;

    public UsuarioEntity() {
        this.tasaciones = new ArrayList<>();
        this.coches = new ArrayList<>();
    }

    public UsuarioEntity(Long id) {
        this.tasaciones = new ArrayList<>();
        this.coches = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public TipousuarioEntity getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(TipousuarioEntity tipousuario) {
        this.tipousuario = tipousuario;
    }

    public SucursalEntity getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalEntity sucursal) {
        this.sucursal = sucursal;
    }

    public int getCoches() {
        return coches.size();
    }

    public int getTasaciones() {
        return tasaciones.size();
    }

    @PreRemove
    public void nullify() {
        this.coches.forEach(c -> c.setUsuario(null));
    }
}
