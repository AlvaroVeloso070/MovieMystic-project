package com.ufg.moviemystic.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufg.moviemystic.api.Utils.ProjectUtil;
import com.ufg.moviemystic.api.entities.Usuario;
import com.ufg.moviemystic.api.repository.UsuarioRepository;
import com.ufg.moviemystic.api.responses.ResourceNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = findById(id);

        BeanUtils.copyProperties(usuarioAtualizado, usuario, ProjectUtil.getNullPropertyNames(usuarioAtualizado));

        return usuarioRepository.save(usuario);
    }


    public void delete(Long id) {
        Usuario usuario = findById(id);

        usuarioRepository.delete(usuario);
    }
}
