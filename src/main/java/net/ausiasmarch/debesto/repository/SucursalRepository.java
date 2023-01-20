package net.ausiasmarch.debesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.SucursalEntity;

public interface SucursalRepository extends JpaRepository<SucursalEntity, Long> {
 
    Page<SucursalEntity> findByNombreIgnoreCaseContainingOrLocalidadIgnoreCaseContaining(String strFilterNombre, String strFilterLocalidad, Pageable oPageable);

}
