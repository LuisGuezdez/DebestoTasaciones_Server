package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.SucursalEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    SucursalRepository oSucursalRepository;

    public void validate(Long id) {
        if (!oSucursalRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public SucursalEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oSucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal with id: " + id + " not found"));
    }

    public Page<SucursalEntity> getPage(Pageable oPageable, String strFilter) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            return oSucursalRepository.findAll(oPageable);
        }else{
            return oSucursalRepository.findByNombreIgnoreCaseContainingOrLocalidadIgnoreCaseContaining(strFilter, strFilter, oPageable);
        }
    }
    
}
