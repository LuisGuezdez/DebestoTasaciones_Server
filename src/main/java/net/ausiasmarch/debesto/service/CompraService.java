package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.CompraEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ResourceNotModifiedException;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.CompraRepository;

@Service
public class CompraService {
    
    @Autowired
    CompraRepository oCompraRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public CompraEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal with id: " + id + " not found"));
    }

    public Page<CompraEntity> getPage(Pageable oPageable, String strFilter) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            return oCompraRepository.findAll(oPageable);
        }else{
            return oCompraRepository.findByUsuarioNombreIgnoreCaseContainingOrUsuarioApellidosIgnoreCaseContainingOrCocheMarcaIgnoreCaseContainingOrCocheModeloIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingOrSucursalLocalidadIgnoreCaseContaining(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
        }
    }

    public Long count() {
        //oAuthService.OnlyAdmins();
        return oCompraRepository.count();
    }

    public Long update(CompraEntity oCompraEntity) {
        // oAuthService.OnlyAdminsOrOwnUsersData(oCompraEntity.getId());
         validate(oCompraEntity.getId());
         return oCompraRepository.save(oCompraEntity).getId();
    }

    public Long create(CompraEntity oNewCompraEntity) {
        //oAuthService.OnlyAdmins();
        validate(oNewCompraEntity);
        oNewCompraEntity.setId(0L);
        return oCompraRepository.save(oNewCompraEntity).getId();
    }

    public Long delete(Long id) {
        validate(id);
        oCompraRepository.deleteById(id);
        if (oCompraRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }
}
