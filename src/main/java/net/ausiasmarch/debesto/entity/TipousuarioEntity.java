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
@Table(name = "tipousuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipousuarioEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @OneToMany(mappedBy = "tipousuario", fetch = FetchType.LAZY)
    private final List<UsuarioEntity> usuarios;

    public TipousuarioEntity() {
        this.usuarios = new ArrayList<>();
    }

    public TipousuarioEntity(Long id) {
        this.usuarios = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public int getUsuarios() {
        return usuarios.size();
    }

    @PreRemove
    public void nullify() {
        this.usuarios.forEach(c -> c.setTipousuario(null));
    }
    
}