package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.UsuarioEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ResourceNotModifiedException;
import net.ausiasmarch.debesto.exception.ValidationException;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    TipoUsuarioService oTipoUsuarioService;

    @Autowired
    SucursalService oSucursalService;

    @Autowired
    AuthService oAuthService;

    private final String DEBESTO_DEFAULT_PASSWORD = "password";


    public void validate(Long id) {
        if (!oUsuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(UsuarioEntity oUsuarioEntity) {
        ValidationHelper.validateStringLength(oUsuarioEntity.getNombre(), 2, 50, "campo nombre de Usuario(el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateStringLength(oUsuarioEntity.getApellidos(), 2, 50, "campo apellidos de Usuario(el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateEmail(oUsuarioEntity.getEmail(), "campo email de Usuario");
        ValidationHelper.validateLogin(oUsuarioEntity.getUsername(), "campo username de Usuario");
        if (oUsuarioRepository.existsByUsername(oUsuarioEntity.getUsername())) {
            throw new ValidationException("el campo username está repetido");
        }
        oTipoUsuarioService.validate(oUsuarioEntity.getTipousuario().getId());
        oSucursalService.validate(oUsuarioEntity.getSucursal().getId());
    }

    public UsuarioEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario with id: " + id + " not found"));
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable, String strFilter) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            return oUsuarioRepository.findAll(oPageable);
        }else{
            return oUsuarioRepository.findByNombreIgnoreCaseContainingOrApellidosIgnoreCaseContainingOrEmailIgnoreCaseContaining(strFilter, strFilter, strFilter, oPageable);
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsuarioRepository.count();
    }

    public Long update(UsuarioEntity oUsuarioEntity) {
        oAuthService.OnlyAdminsOrOwnUsersData(oUsuarioEntity.getId());
        validate(oUsuarioEntity.getId());
        UsuarioEntity oOldUsuarioEntity=oUsuarioRepository.getById(oUsuarioEntity.getId());
        oUsuarioEntity.setContraseña(oOldUsuarioEntity.getContraseña());
        return oUsuarioRepository.save(oUsuarioEntity).getId();
    }

    public Long create(UsuarioEntity oNewUsuarioEntity) {
        oAuthService.OnlyAdmins();
        validate(oNewUsuarioEntity);
        oNewUsuarioEntity.setId(0L);
        oNewUsuarioEntity.setContraseña(DEBESTO_DEFAULT_PASSWORD);
        return oUsuarioRepository.save(oNewUsuarioEntity).getId();
    }

    public Long delete(Long id) {
        validate(id);
        oUsuarioRepository.deleteById(id);
        if (oUsuarioRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }

}
