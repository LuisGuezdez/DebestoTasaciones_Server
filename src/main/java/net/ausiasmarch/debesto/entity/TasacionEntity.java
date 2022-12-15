package net.ausiasmarch.debesto.entity;

import java.time.LocalDateTime;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tasacion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TasacionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reserva;

    private Boolean asignado;
    private Double precio;

    @OneToMany(mappedBy = "tasacion", fetch = FetchType.LAZY)
    private final List<CocheEntity> coches;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private SucursalEntity sucursal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private SucursalEntity usuario;

    public TasacionEntity() {

        this.coches = new ArrayList<>();
    }

    public TasacionEntity(Long id) {

        this.coches = new ArrayList<>();
        this.id = id; //Ojo, no se si se deberia dejar aqui este this.id
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getReserva() {
        return reserva;
    }

    public void setReserva(LocalDateTime reserva) {
        this.reserva = reserva;
    }

    public Boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<CocheEntity> getCoches() {
        return coches;
    }

    public SucursalEntity getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalEntity sucursal) {
        this.sucursal = sucursal;
    }

    public SucursalEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(SucursalEntity usuario) {
        this.usuario = usuario;
    }

    
}