package net.ausiasmarch.debesto.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.TasacionEntity;
import net.ausiasmarch.debesto.entity.UsuarioEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ResourceNotModifiedException;
import net.ausiasmarch.debesto.exception.ValidationException;
import net.ausiasmarch.debesto.helper.TipoUsuarioHelper;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.SucursalRepository;
import net.ausiasmarch.debesto.repository.TasacionRepository;


@Service
public class TasacionService {
    
    @Autowired
    TasacionRepository oTasacionRepository;

    @Autowired
    SucursalRepository oSucursalRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    UsuarioService oUsuarioService;

    public void validate(Long id) {
        if (!oTasacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TasacionEntity oTasacionEntity) {
        if (oTasacionEntity.getReserva().isBefore(LocalDateTime.now())) {
            throw new ValidationException("error de validación: la reserva no puede ser anterior a hoy");  
        }
        UsuarioEntity usuario  = oUsuarioService.get(oTasacionEntity.getUsuario().getId());
        if (!usuario.getTipousuario().getId().equals(TipoUsuarioHelper.EMPLEADO)) {
            throw new ValidationException("error de validación: el usuario debe ser un empleado");  
        }
        Long sucursalCli = oSucursalRepository.findByNombreIgnoreCaseContaining("cliente").getId();
        if (oTasacionEntity.getSucursal().getId() == sucursalCli) {
            throw new ValidationException("error de validación: la sucursal no debe ser corresponder a cliente (id: 3)");  
        }
        if (oTasacionRepository.existsByCocheId(oTasacionEntity.getCoche().getId())) {
            throw new ValidationException("error de validación: el coche seleccionado ya está en tasación");
        }
    }

    public TasacionEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oTasacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasacion with id: " + id + " not found"));
    }

    public Long count() {
        //oAuthService.OnlyAdmins();
        return oTasacionRepository.count();
    }

    public Page<TasacionEntity> getPage(Pageable oPageable, String strFilter) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            return oTasacionRepository.findAll(oPageable);
        }else{
            return oTasacionRepository.findByUsuarioNombreIgnoreCaseContainingOrUsuarioApellidosIgnoreCaseContainingOrCocheMarcaIgnoreCaseContainingOrCocheModeloIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingOrSucursalLocalidadIgnoreCaseContaining(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
        }
    }

    public Long update(TasacionEntity oTasacionEntity) {
        // oAuthService.OnlyAdminsOrOwnUsersData(oTasacionEntity.getId());
         validate(oTasacionEntity.getId());
         return oTasacionRepository.save(oTasacionEntity).getId();
    }

    public Long create(TasacionEntity oNewTasacionEntity) {
        //oAuthService.OnlyAdmins();
        validate(oNewTasacionEntity);
        oNewTasacionEntity.setId(0L);
        return oTasacionRepository.save(oNewTasacionEntity).getId();
    }

    public Long delete(Long id) {
        validate(id);
        oTasacionRepository.deleteById(id);
        if (oTasacionRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }
}
