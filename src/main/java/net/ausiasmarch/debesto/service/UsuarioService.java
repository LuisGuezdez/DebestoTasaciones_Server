package net.ausiasmarch.debesto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.UsuarioEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ResourceNotModifiedException;
import net.ausiasmarch.debesto.exception.ValidationException;
import net.ausiasmarch.debesto.helper.RandomHelper;
import net.ausiasmarch.debesto.helper.TipoUsuarioHelper;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.SucursalRepository;
import net.ausiasmarch.debesto.repository.TipousuarioRepository;
import net.ausiasmarch.debesto.repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    SucursalRepository oSucursalRepository;

    @Autowired
    TipoUsuarioService oTipoUsuarioService;

    @Autowired
    SucursalService oSucursalService;

    @Autowired
    AuthService oAuthService;

    private final String DEBESTO_DEFAULT_PASSWORD = "password";
    private final String[] NOMBRES = {"Jose", "Luis", "Elen", "Toni", "Hector", "Enrique", "Laura", "Miguel", "Sergio",
        "Javi", "Marcos", "Peret", "Daniel", "Jose", "Alba", "Mayara", "Aaron", "Rafa", "Lionel", "Borja"};

    private final String[] APELLIDOS = {"Torres", "Tatay", "Coronado", "Rodríguez", "Mikayelyan", "Amador", "Martinez",
    "Vargas", "Raga", "Santos", "Heredia", "Arias", "Salazar", "Kuvshinnikova", "Jimenez", "Frejo", "Fernández",
    "Valcarcel", "Sesa", "Laenos", "Villanueva", "García", "Navarro", "Boriko", "Primo", "Gil", "Mocholi",
    "Ortega", "Dung", "Santiago", "Sanchis", "Merida", "Aznar", "Bermúdez", "Tarazón", "Manzaneque", "Romero", "Santamaría"};


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

    public Page<UsuarioEntity> getPage(Pageable oPageable, String strFilter, Long id_usertype) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        // if (strFilter != null && strFilter.indexOf(" ") + 1 != -1) {
        //     String nombre = strFilter.substring(0, strFilter.indexOf(" "));
        //     String apellidos = strFilter.substring(strFilter.indexOf(" ") + 1);

        //     return oUsuarioRepository.findByNombreIgnoreCaseContainingAndApellidosIgnoreCaseContaining(nombre, apellidos, oPageable);
        // }
        if (strFilter == null || strFilter.length() == 0) {
            if (id_usertype == null) {
                return oUsuarioRepository.findAll(oPageable);
            } else {
                return oUsuarioRepository.findByTipousuarioId(id_usertype, oPageable);
            }
        }else{
            if (id_usertype == null) {
                return oUsuarioRepository.findByNombreIgnoreCaseContainingAndApellidosIgnoreCaseContainingOrEmailIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingOrTipousuarioTipoIgnoreCaseContaining(strFilter,strFilter,strFilter,strFilter, strFilter, oPageable);
            } else {
                return oUsuarioRepository.findByNombreIgnoreCaseContainingAndApellidosIgnoreCaseContainingOrEmailIgnoreCaseContainingOrSucursalNombreIgnoreCaseContainingAndTipousuarioId( strFilter, strFilter, strFilter, strFilter, id_usertype, oPageable);
            }
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsuarioRepository.count();
    }

    public Long update(UsuarioEntity oUsuarioEntity) {
       // oAuthService.OnlyAdminsOrOwnUsersData(oUsuarioEntity.getId());
        validate(oUsuarioEntity.getId());
        UsuarioEntity oOldUsuarioEntity=oUsuarioRepository.getById(oUsuarioEntity.getId());
        oUsuarioEntity.setContraseña(oOldUsuarioEntity.getContraseña());
        return oUsuarioRepository.save(oUsuarioEntity).getId();
    }

    public Long create(UsuarioEntity oNewUsuarioEntity) {
        //oAuthService.OnlyAdmins();
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

    public Long generateUsers(Integer amount) {
        //oAuthService.OnlyAdmins();
        List<UsuarioEntity> userList = new ArrayList<>();
        int i = 0;
        while(i < amount) {
            UsuarioEntity oUsuarioEntity = generateRandomUser();
            oUsuarioRepository.save(oUsuarioEntity);
            userList.add(oUsuarioEntity);
            i++;
        }
        return oUsuarioRepository.count();
    }

    private UsuarioEntity generateRandomUser() {
        UsuarioEntity oUsuarioEntity = new UsuarioEntity();
        oUsuarioEntity.setNombre(generateName());
        oUsuarioEntity.setApellidos(generateSurname() + " " + generateSurname());
        oUsuarioEntity.setUsername(oUsuarioEntity.getNombre().substring(0, 2) + oUsuarioEntity.getApellidos().substring(0, 2) + RandomHelper.getRandomInt(0, 1000));
        oUsuarioEntity.setContraseña(DEBESTO_DEFAULT_PASSWORD);
        oUsuarioEntity.setEmail(oUsuarioEntity.getUsername() + "@debesto.com");
        if (RandomHelper.getRandomInt(1, 2) == 1) {
            oUsuarioEntity.setTipousuario(oTipousuarioRepository.getById(TipoUsuarioHelper.EMPLEADO));
        } else {
            oUsuarioEntity.setTipousuario(oTipousuarioRepository.getById(TipoUsuarioHelper.CLIENTE));
        }
        if (oUsuarioEntity.getTipousuario().getId() == 1) {
            int totalSucursals = (int) oSucursalRepository.count();
            int randomSucursalId = RandomHelper.getRandomInt(1, totalSucursals);
            oSucursalRepository.findById((long) randomSucursalId)
                    .ifPresent(oUsuarioEntity::setSucursal);
        }
        else{
            oSucursalRepository.findById((long) 3)
                    .ifPresent(oUsuarioEntity::setSucursal);
        }

        if (oUsuarioRepository.existsByUsername(oUsuarioEntity.getUsername())) {
            throw new ValidationException("The username created already exists");
        }
        else{
            return oUsuarioEntity;
        }
        
    }

    private String generateName() {
        return NOMBRES[RandomHelper.getRandomInt(0, NOMBRES.length - 1)].toLowerCase();
    }

    private String generateSurname() {
        return APELLIDOS[RandomHelper.getRandomInt(0, APELLIDOS.length - 1)].toLowerCase();
    }

}
