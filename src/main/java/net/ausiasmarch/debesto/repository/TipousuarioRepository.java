package net.ausiasmarch.debesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.debesto.entity.TipousuarioEntity;

public interface TipousuarioRepository extends JpaRepository<TipousuarioEntity, Long> {
    
    Page<TipousuarioEntity> findByTipoIgnoreCaseContaining(String strFilterTipo, Pageable oPageable);

}
