package net.ausiasmarch.debesto.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sucursal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SucursalEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String localidad;
    private String calle;
    private Long postal;

    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    private final List<UsuarioEntity> usuarios;

    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    private final List<CompraEntity> compras;

    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    private final List<TasacionEntity> tasaciones;

    

    public SucursalEntity() {
        this.usuarios = new ArrayList<>();
        this.tasaciones = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public SucursalEntity(Long id) {
        this.usuarios = new ArrayList<>();
        this.tasaciones = new ArrayList<>();
        this.compras = new ArrayList<>();
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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Long getPostal() {
        return postal;
    }

    public void setPostal(Long postal) {
        this.postal = postal;
    }

    public int getUsuarios() {
        return usuarios.size();
    }

    public int getTasaciones() {
        return tasaciones.size();
    }

    public int getCompras() {
        return compras.size();
    }

    @PreRemove
    public void nullify() {
        this.compras.forEach(c -> c.setSucursal(null));
        this.tasaciones.forEach(c -> c.setSucursal(null));
        this.usuarios.forEach(c -> c.setSucursal(null));
    }
}