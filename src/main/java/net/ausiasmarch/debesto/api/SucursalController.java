package net.ausiasmarch.debesto.api;

import org.springframework.data.domain.Sort;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.debesto.entity.SucursalEntity;
import net.ausiasmarch.debesto.service.SucursalService;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    SucursalService oSucursalService;

    @GetMapping("/{id}")
    public ResponseEntity<SucursalEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(oSucursalService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SucursalEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<>(oSucursalService.getPage(oPageable, strFilter), HttpStatus.OK);
    }
    
}
