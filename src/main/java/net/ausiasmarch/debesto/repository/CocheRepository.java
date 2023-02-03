package net.ausiasmarch.debesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.CocheEntity;

public interface CocheRepository extends JpaRepository<CocheEntity, Long> {
    
    Page<CocheEntity> findByMarcaIgnoreCaseContainingOrModeloIgnoreCaseContainingOrUsuarioNombreIgnoreCaseContainingOrUsuarioApellidosIgnoreCaseContaining(String strFilterMarca, String strFilterModelo, String strFilterNombre, String strFilterApellidos, Pageable oPageable);

}
