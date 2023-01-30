package net.ausiasmarch.debesto.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.entity.CocheEntity;
import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.exception.ResourceNotModifiedException;
import net.ausiasmarch.debesto.exception.ValidationException;
import net.ausiasmarch.debesto.helper.ValidationHelper;
import net.ausiasmarch.debesto.repository.CocheRepository;

@Service
public class CocheService {
    
    @Autowired
    CocheRepository oCocheRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oCocheRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    //Revisar validacion del año y testear
    public void validate(CocheEntity oCocheEntity) {
        ValidationHelper.validateStringLength(oCocheEntity.getMarca(), 2, 20, "campo marca de Coche (el campo debe tener longitud de 2 a 20 caracteres)");
        ValidationHelper.validateStringLength(oCocheEntity.getModelo(), 2, 20, "campo modelo de Coche (el campo debe tener longitud de 2 a 20 caracteres)");
        ValidationHelper.validateRange(oCocheEntity.getKms(), 0, 1000000, "campo kms de Coche (el campo debe estar entre 0 y 1.000.000)");
        ValidationHelper.validateDate(LocalDate.of(oCocheEntity.getAnyo(), 0, 0), LocalDate.of(1900, 0, 0), LocalDate.now(), "campo fecha de Sucursal (el campo debe estar entre el año 1900 y el año actual");
        String comb = oCocheEntity.getCombustible();
        if (!comb.equalsIgnoreCase("diesel") || !comb.equalsIgnoreCase("gasolina") || !comb.equalsIgnoreCase("gas")) {
            throw new ValidationException("error de validación: campo combustible debe ser diesel, gasolina o gas");
        }
    }

    public CocheEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oCocheRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal with id: " + id + " not found"));
    }

    public Long count() {
        //oAuthService.OnlyAdmins();
        return oCocheRepository.count();
    }

    //Revisar la manera y la forma de filtrar
    public Page<CocheEntity> getPage(Pageable oPageable, String strFilter) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            return oCocheRepository.findAll(oPageable);
        }else{
            return oCocheRepository.findByMarcaIgnoreCaseContainingOrModeloIgnoreCaseContaining(strFilter, strFilter, oPageable);
        }
    }

    public Long update(CocheEntity oCocheEntity) {
        // oAuthService.OnlyAdminsOrOwnUsersData(oCocheEntity.getId());
         validate(oCocheEntity.getId());
         return oCocheRepository.save(oCocheEntity).getId();
     }

     public Long create(CocheEntity oNewCocheEntity) {
        //oAuthService.OnlyAdmins();
        validate(oNewCocheEntity);
        oNewCocheEntity.setId(0L);
        return oCocheRepository.save(oNewCocheEntity).getId();
    }

    public Long delete(Long id) {
        validate(id);
        oCocheRepository.deleteById(id);
        if (oCocheRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }
}
