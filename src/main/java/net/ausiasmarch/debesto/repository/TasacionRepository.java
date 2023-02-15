package net.ausiasmarch.debesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.TasacionEntity;

public interface TasacionRepository extends JpaRepository<TasacionEntity, Long>{
    
    Page<TasacionEntity> findByUsuarioNombreIgnoreCaseContainingOrUsuarioApellidosIgnoreCaseContainingOrCocheMarcaIgnoreCaseContainingOrCocheModeloIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingOrSucursalLocalidadIgnoreCaseContaining(String strFilterNombre, String strFilterApellidos,String strFilterMarca, String strFilterModelo, String strFilterSucNombre, String strFilterLocalidad, Pageable oPageable);

    boolean existsByCocheId(Long id);

    Page<TasacionEntity> findByCocheId(Long cocheId, Pageable oPageable);
}
