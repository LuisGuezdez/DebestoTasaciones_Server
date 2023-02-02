package net.ausiasmarch.debesto.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    private Double valorcoche;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_coche")
    private CocheEntity coche;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private SucursalEntity sucursal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    

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

    public Double getvalorcoche() {
        return valorcoche;
    }

    public void setvalorcoche(Double valorcoche) {
        this.valorcoche = valorcoche;
    }

    public SucursalEntity getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalEntity sucursal) {
        this.sucursal = sucursal;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public CocheEntity getCoche() {
        return coche;
    }

    public void setCoche(CocheEntity coche) {
        this.coche = coche;
    }

    
}