package net.ausiasmarch.debesto.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.debesto.bean.UsuarioBean;
import net.ausiasmarch.debesto.entity.UsuarioEntity;
import net.ausiasmarch.debesto.repository.UsuarioRepository;
import net.ausiasmarch.debesto.service.AuthService;
import net.ausiasmarch.debesto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sesion")
public class SessionController {

    @Autowired
    AuthService oAuthService;

    @GetMapping("")
    public ResponseEntity<UsuarioEntity> check() {
        return new ResponseEntity<UsuarioEntity>(oAuthService.check(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioEntity> login(@org.springframework.web.bind.annotation.RequestBody UsuarioBean oUsuarioBean) {
        return new ResponseEntity<UsuarioEntity>(oAuthService.login(oUsuarioBean), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<?> logout() {
        oAuthService.logout();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
