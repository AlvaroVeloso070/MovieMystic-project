package com.ufg.moviemystic.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroDTO {
    private Long id;

    public CadastroDTO(Long id) {
        this.id = id;
    }
}
