package com.ufg.moviemystic.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufg.moviemystic.api.entities.Avaliacao;
import com.ufg.moviemystic.api.responses.Response;
import com.ufg.moviemystic.api.services.AvaliacaoService;

import jakarta.validation.Valid;

@RestController
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @RequestMapping(value = "/avaliacao", method = RequestMethod.GET)
    public List<Avaliacao> Get(){
        return avaliacaoService.findAll();
    }

    @RequestMapping(value = "/avaliacao/{id}", method = RequestMethod.GET)
    public ResponseEntity<Avaliacao> GetById(@PathVariable(value = "id") Long id){
        Optional<Avaliacao> avaliacao = Optional.ofNullable(avaliacaoService.findById(id));

        if(avaliacao.isPresent()){
            return new ResponseEntity<Avaliacao>(avaliacao.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/avaliacao",method = RequestMethod.POST)
    public ResponseEntity<Response<Avaliacao>> Post (@Valid @RequestBody Avaliacao avaliacao, BindingResult result) {

        Response<Avaliacao> response = new Response<Avaliacao>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        avaliacaoService.save(avaliacao);
        response.setData(avaliacao);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/avaliacao/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response<Avaliacao>> Put(@PathVariable(value = "id") Long id, @Valid @RequestBody Avaliacao newAvaliacao, BindingResult result){
        Optional<Avaliacao> oldAvaliacao = Optional.ofNullable(avaliacaoService.findById(id));
        Response<Avaliacao> response = new Response<Avaliacao>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if(oldAvaliacao.isPresent()){
            avaliacaoService.update(id, newAvaliacao);
            return ResponseEntity.ok(response);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/avaliacao/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete (@PathVariable(value = "id") long id){

        Optional<Avaliacao> avaliacao = Optional.ofNullable(avaliacaoService.findById(id));

        if(avaliacao.isPresent()) {
            avaliacaoService.delete(id);
            return new ResponseEntity<> (HttpStatus.OK);
        }
        else {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }
}
