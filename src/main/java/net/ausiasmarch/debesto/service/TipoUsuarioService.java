package net.ausiasmarch.debesto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.debesto.exception.ResourceNotFoundException;
import net.ausiasmarch.debesto.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {

    @Autowired
    TipoUsuarioRepository oTipoUsuarioRepository;

    public void validate(Long id) {
        if (!oTipoUsuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
    
}
