package net.ausiasmarch.debesto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.TipoUsuarioEntity;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {
    
}
