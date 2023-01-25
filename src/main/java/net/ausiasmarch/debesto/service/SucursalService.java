package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.SucursalEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ValidationException;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    SucursalRepository oSucursalRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oSucursalRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(SucursalEntity oSucursalEntity) {
        ValidationHelper.validateStringLength(oSucursalEntity.getNombre(), 2, 70, "campo nombre de Usuario(el campo debe tener longitud de 2 a 70 caracteres)");
        ValidationHelper.validateStringLength(oSucursalEntity.getLocalidad(), 2, 70, "campo apellidos de Usuario(el campo debe tener longitud de 2 a 70 caracteres)");
        ValidationHelper.validateStringLength(oSucursalEntity.getCalle(), 2, 70, "campo apellidos de Usuario(el campo debe tener longitud de 2 a 70 caracteres)");
        ValidationHelper.validateRange(oSucursalEntity.getPostal(), 100000, 99999, "campo postal de Sucursal(el campo debe tener 5 dígitos");
        if (oSucursalRepository.existsByNombre(oSucursalEntity.getNombre())) {
            throw new ValidationException("el campo nombre está repetido");
        }
    }

    public SucursalEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oSucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal with id: " + id + " not found"));
    }

    public Long count() {
        //oAuthService.OnlyAdmins();
        return oSucursalRepository.count();
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

    public Long update(SucursalEntity oSucursalEntity) {
        // oAuthService.OnlyAdminsOrOwnUsersData(oSucursalEntity.getId());
         validate(oSucursalEntity.getId());
         return oSucursalRepository.save(oSucursalEntity).getId();
     }

     public Long create(SucursalEntity oNewSucursalEntity) {
        //oAuthService.OnlyAdmins();
        validate(oNewSucursalEntity);
        oNewSucursalEntity.setId(0L);
        oNewSucursalEntity.setContraseña(DEBESTO_DEFAULT_PASSWORD);
        return oSucursalRepository.save(oNewSucursalEntity).getId();
    }
    
}
