package com.ufg.moviemystic.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private Long id;

    public UsuarioDTO(Long id) {
        this.id = id;
    }
}
