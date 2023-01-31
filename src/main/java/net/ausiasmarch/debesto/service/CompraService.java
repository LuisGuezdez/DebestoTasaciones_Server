package net.ausiasmarch.debesto.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.CompraEntity;
import net.ausiasmarch.debesto.entity.SucursalEntity;
import net.ausiasmarch.debesto.entity.UsuarioEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ResourceNotModifiedException;
import net.ausiasmarch.debesto.exception.ValidationException;
import net.ausiasmarch.debesto.helper.TipoUsuarioHelper;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.CompraRepository;
import net.ausiasmarch.debesto.repository.SucursalRepository;

@Service
public class CompraService {
    
    @Autowired
    CompraRepository oCompraRepository;

    @Autowired
    SucursalRepository oSucursalRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    UsuarioService oUsuarioService;

    public void validate(Long id) {
        if (!oCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(CompraEntity oCompraEntity) {
        if (oCompraEntity.getPrecio() < 0) {
            throw new ValidationException("error de validación: el precio no puede ser un numero negativo");  
        }
        UsuarioEntity usuario  = oUsuarioService.get(oCompraEntity.getUsuario().getId());
        if (!usuario.getTipousuario().getId().equals(TipoUsuarioHelper.EMPLEADO)) {
            throw new ValidationException("error de validación: el usuario debe ser un empleado");  
        }
        Long sucursalCli = oSucursalRepository.findByNombreIgnoreCaseContaining("cliente").getId();
        if (oCompraEntity.getSucursal().getId() == sucursalCli) {
            throw new ValidationException("error de validación: la sucursal no debe ser corresponder a cliente (id: 3)");  
        }
        if (oCompraRepository.existsByCocheId(oCompraEntity.getCoche().getId())) {
            throw new ValidationException("error de validación: el coche seleccionado ya está vendido");
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
         oCompraEntity.setFecha(oCompraRepository.getById(oCompraEntity.getId()).getFecha());
         return oCompraRepository.save(oCompraEntity).getId();
    }

    public Long create(CompraEntity oNewCompraEntity) {
        //oAuthService.OnlyAdmins();
        validate(oNewCompraEntity);
        oNewCompraEntity.setId(0L);
        oNewCompraEntity.setFecha(LocalDate.now());
        
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
