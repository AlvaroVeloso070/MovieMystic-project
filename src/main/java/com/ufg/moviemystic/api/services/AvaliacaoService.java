package com.ufg.moviemystic.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufg.moviemystic.api.Utils.ProjectUtil;
import com.ufg.moviemystic.api.entities.Avaliacao;
import com.ufg.moviemystic.api.entities.Usuario;
import com.ufg.moviemystic.api.repository.AvaliacaoRepository;
import com.ufg.moviemystic.api.responses.ResourceNotFoundException;

@Service
public class AvaliacaoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> findAll() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao findById(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliacao", "id", id));
    }

    public void save(Avaliacao avaliacao) {
        Usuario usuario = usuarioService.findById(avaliacao.getUsuario().getId());
        avaliacao.setUsuario(usuario);
        avaliacaoRepository.save(avaliacao);
    }

    public void update(Long id, Avaliacao avaliacaoAtualizada) {
        Avaliacao avaliacao = findById(id);

        BeanUtils.copyProperties(avaliacaoAtualizada, avaliacao, ProjectUtil.getNullPropertyNames(avaliacaoAtualizada));

        avaliacaoRepository.save(avaliacao);
    }

    public void delete(Long id) {
        Avaliacao avaliacao = findById(id);
        avaliacaoRepository.delete(avaliacao);
    }
}

