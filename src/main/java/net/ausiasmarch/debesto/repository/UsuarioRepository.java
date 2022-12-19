package net.ausiasmarch.debesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByUsername(String username);

    UsuarioEntity findByUsernameAndContraseña(String username, String contraseña);
    
    UsuarioEntity findByUsername(String username);
                        
    Page<UsuarioEntity> findByNombreIgnoreCaseContainingOrApellidosIgnoreCaseContainingOrEmailIgnoreCaseContaining(String strFilterNombre, String strFilterApellidos, String strFilterEmail, Pageable oPageable);

    //Quedan por hacer más finds por hacer, mirar el de andamio

    
    
}
