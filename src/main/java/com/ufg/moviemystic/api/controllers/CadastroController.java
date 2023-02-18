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

import com.ufg.moviemystic.api.entities.Cadastro;
import com.ufg.moviemystic.api.responses.Response;
import com.ufg.moviemystic.api.services.CadastroService;

import jakarta.validation.Valid;

@RestController
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public List<Cadastro> Get(){
        return cadastroService.findAll();
    }

    @RequestMapping(value = "/cadastro/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cadastro> GetById(@PathVariable(value = "id") Long id){
        Optional<Cadastro> cadastro = Optional.ofNullable(cadastroService.findById(id));

        if(cadastro.isPresent()){
            return new ResponseEntity<Cadastro>(cadastro.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/cadastro",method = RequestMethod.POST)
    public ResponseEntity<Response<Cadastro>> Post (@Valid @RequestBody Cadastro cadastro, BindingResult result) {

        Response<Cadastro> response = new Response<Cadastro>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        cadastroService.save(cadastro);
        response.setData(cadastro);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/cadastro/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response<Cadastro>> Put(@PathVariable(value = "id") Long id, @Valid @RequestBody Cadastro newCadastro, BindingResult result){
        Optional<Cadastro> oldCadastro = Optional.ofNullable(cadastroService.findById(id));
        Response<Cadastro> response = new Response<Cadastro>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if(oldCadastro.isPresent()){
            cadastroService.update(id, newCadastro);
            return ResponseEntity.ok(response);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/cadastro/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete (@PathVariable(value = "id") long id){

        Optional<Cadastro> cadastro = Optional.ofNullable(cadastroService.findById(id));

        if(cadastro.isPresent()) {
            cadastroService.delete(id);
            return new ResponseEntity<> (HttpStatus.OK);
        }
        else {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }

}
