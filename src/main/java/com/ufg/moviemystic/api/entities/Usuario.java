package com.ufg.moviemystic.api.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ufg.moviemystic.api.dto.AvaliacaoDTO;
import com.ufg.moviemystic.api.dto.CadastroDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome",nullable = false)
    @NotEmpty(message="O nome deverá ser informado!")
    @Length(min=3, max = 200, message="O nome deverá ter entre 3 a 200 caracteres!")
    private String nome;

    @Column(name = "apelido", nullable = false)
    @Length(min=3, max = 200, message="O apelido deverá ter entre 3 a 200 caracteres!")
    private String apelido;

    @Column(name = "cpf", nullable = false)
    @NotEmpty(message="O CPF do usuario deverá ser informado!")
    private String cpf;

    @Column(name = "dataNascimento",nullable =false)
    @NotNull
    private Date dataNascimento;

    @Column(name = "telefone", nullable = false)
    @NotEmpty(message = "O numero de telefone deve ser informado!")
    private String telefone;

    @OneToOne(mappedBy = "usuario", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Cadastro cadastro;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;


    @JsonProperty("cadastro")
    private CadastroDTO getIdCadastro(){
        return new CadastroDTO(cadastro.getId());
    }

    @JsonProperty("avaliacoes")
    private List<AvaliacaoDTO> getIdAvaliacoes(){
        List<AvaliacaoDTO> avaliacaoDTOList = new ArrayList<>();

        for(Avaliacao avaliacao : avaliacoes ){
            avaliacaoDTOList.add(new AvaliacaoDTO(avaliacao.getId()));
        }

        return avaliacaoDTOList;
    }
}
