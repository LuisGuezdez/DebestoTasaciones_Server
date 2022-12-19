package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    SucursalRepository oSucursalRepository;

    public void validate(Long id) {
        if (!oSucursalRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
    
}
