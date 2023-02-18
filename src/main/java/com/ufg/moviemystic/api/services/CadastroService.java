package com.ufg.moviemystic.api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufg.moviemystic.api.Utils.ProjectUtil;
import com.ufg.moviemystic.api.entities.Cadastro;
import com.ufg.moviemystic.api.entities.Usuario;
import com.ufg.moviemystic.api.repository.CadastroRepository;
import com.ufg.moviemystic.api.responses.ResourceNotFoundException;

@Service
public class CadastroService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CadastroRepository cadastroRepository;

    public List<Cadastro> findAll() {
        return cadastroRepository.findAll();
    }

    public Cadastro findById(Long id) {
        return cadastroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cadastro", "id", id));
    }

    public Cadastro save(Cadastro cadastro) {
        cadastro.setDataCadastro(new Date());

        Usuario usuario = usuarioService.save(cadastro.getUsuario());

        cadastro.setUsuario(usuario);
        return cadastroRepository.save(cadastro);
    }

    public Cadastro update(Long id, Cadastro cadastroAtualizado) {
        Cadastro cadastro = findById(id);

        BeanUtils.copyProperties(cadastroAtualizado, cadastro, ProjectUtil.getNullPropertyNames(cadastroAtualizado));

        return cadastroRepository.save(cadastro);
    }

    public void delete(Long id) {
        Cadastro cadastro = findById(id);
        cadastroRepository.delete(cadastro);
    }
}
