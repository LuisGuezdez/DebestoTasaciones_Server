package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.TipousuarioEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.TipousuarioRepository;

@Service
public class TipoUsuarioService {

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    public void validate(Long id) {
        if (!oTipousuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public TipousuarioEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oTipousuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipousuario with id: " + id + " not found"));
    }

    public Page<TipousuarioEntity> getPage(Pageable oPageable, String strFilter) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            return oTipousuarioRepository.findAll(oPageable);
        }else{
            return oTipousuarioRepository.findByTipoIgnoreCaseContaining(strFilter, oPageable);
        }
    }
    
}
