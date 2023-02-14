package net.ausiasmarch.debesto.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.debesto.bean.UsuarioBean;
import net.ausiasmarch.debesto.entity.UsuarioEntity;
import net.ausiasmarch.debesto.exception.UnauthorizedException;
import net.ausiasmarch.debesto.helper.JwtHelper;
import net.ausiasmarch.debesto.helper.TipoUsuarioHelper;
import net.ausiasmarch.debesto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    private HttpServletRequest oRequest;


    public String login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getContraseña() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsernameAndContraseña(oUsuarioBean.getUsername(), oUsuarioBean.getContraseña());
            if (oUsuarioEntity != null) {
                return JwtHelper.generateJWT(oUsuarioBean.getUsername(), oUsuarioEntity.getTipousuario().getId(), oUsuarioEntity.getId());
            } else {
                throw new UnauthorizedException("login or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }/* 

    public UsuarioEntity check() {
        System.out.println(oRequest.getParameter("name"));
        String strUsuarioName = (String) oRequest.getAttribute("name");
        if (strUsuarioName != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuarioName);
            return oUsuarioEntity;
        } else {
            throw new UnauthorizedException("No active session");
        }
    } */

    public UsuarioEntity getUser() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            return oUsuarioSessionEntity;
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    public Long getUserID() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            return oUsuarioSessionEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    // public boolean isAdmin() {
    //     UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
    //     if (oUsuarioSessionEntity != null) {
    //         if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public void OnlyAdmins() {
        //System.out.println(oRequest.getAttribute("username"));
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsername((String) oRequest.getAttribute("username"));
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.EMPLEADO)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.CLIENTE)) {
                if (!oUsuarioSessionEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own data");
                }
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        }
    }

}
