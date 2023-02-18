package com.ufg.moviemystic.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AvaliacaoDTO {
    private Long id;

    public AvaliacaoDTO(Long id) {
        this.id = id;
    }
}
