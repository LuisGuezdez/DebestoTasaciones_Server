package net.ausiasmarch.debesto.api;

import org.springframework.data.domain.Sort;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oSucursalService.count(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SucursalEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<>(oSucursalService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody SucursalEntity oSucursalEntity) {
        return new ResponseEntity<Long>(oSucursalService.update(oSucursalEntity), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody SucursalEntity oNewSucursalEntity) {
        return new ResponseEntity<Long>(oSucursalService.create(oNewSucursalEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oSucursalService.delete(id), HttpStatus.OK);
    }
}
