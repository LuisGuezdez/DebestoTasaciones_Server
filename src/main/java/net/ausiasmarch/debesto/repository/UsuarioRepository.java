package net.ausiasmarch.debesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByUsername(String username);

    UsuarioEntity findByUsernameAndContraseña(String username, String contraseña);
    
    UsuarioEntity findByUsernameIgnoreCaseContaining(String username);

    UsuarioEntity findByUsername(String username);

    Page<UsuarioEntity> findByNombreIgnoreCaseContainingAndApellidosIgnoreCaseContaining(String strFilterNombre, String strFilterApellidos, Pageable oPageable);

    Page<UsuarioEntity> findByTipousuarioId(Long id_usertype, Pageable oPageable);
        
    Page<UsuarioEntity> findByNombreIgnoreCaseContainingAndApellidosIgnoreCaseContainingOrEmailIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingOrTipousuarioTipoIgnoreCaseContaining(String strFilterNombre, String strFilterApellidos, String strFilterEmail, String strFilterSucursal, String strFilterTipo, Pageable oPageable);

    Page<UsuarioEntity> findByNombreIgnoreCaseContainingAndApellidosIgnoreCaseContainingOrEmailIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingAndTipousuarioId(String strFilterNombre, String strFilterApellidos, String strFilterEmail, String strFilterSucursal, Long id_usertype, Pageable oPageable);

    //Page<UsuarioEntity> findByTipousuarioIdAndNombreIgnoreCaseContainingOrApellidosIgnoreCaseContaining(Long lTipoUsuario,String strNombre,String strApellido, Pageable oPageable);

    //Quedan por hacer más finds por hacer, mirar el de andamio

    
    
}
