package net.ausiasmarch.debesto.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import net.ausiasmarch.debesto.entity.CompraEntity;
import net.ausiasmarch.debesto.service.CompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {
    
    @Autowired
    CompraService oCompraService;

    @GetMapping("/{id}")
    public ResponseEntity<CompraEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(oCompraService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CompraEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<>(oCompraService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCompraService.count(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CompraEntity oCompraEntity) {
        return new ResponseEntity<Long>(oCompraService.update(oCompraEntity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CompraEntity oNewCompraEntity) {
        return new ResponseEntity<Long>(oCompraService.create(oNewCompraEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCompraService.delete(id), HttpStatus.OK);
    }
}
