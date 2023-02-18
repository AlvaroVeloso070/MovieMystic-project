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

import com.ufg.moviemystic.api.entities.Usuario;
import com.ufg.moviemystic.api.responses.Response;
import com.ufg.moviemystic.api.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public List<Usuario> Get(){
        return usuarioService.findAll();
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> GetById(@PathVariable(value = "id") long id){
        Optional<Usuario> usuario = Optional.ofNullable(usuarioService.findById(id));

        if(usuario.isPresent()){
            return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/usuario",method = RequestMethod.POST)
    public ResponseEntity<Response<Usuario>> Post (@Valid @RequestBody Usuario usuario, BindingResult result) {

        Response<Usuario> response = new Response<Usuario>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        usuarioService.save(usuario);
        response.setData(usuario);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response<Usuario>> Put (@PathVariable(value = "id") long id, @Valid @RequestBody Usuario newUsuario, BindingResult result){
        Optional<Usuario> oldUsuario = Optional.ofNullable(usuarioService.findById(id));
        Response<Usuario> response = new Response<Usuario>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if(oldUsuario.isPresent()){
            usuarioService.update(id, newUsuario);
            return ResponseEntity.ok(response);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete (@PathVariable(value = "id") long id){

        Optional<Usuario> usuario = Optional.ofNullable(usuarioService.findById(id));

        if(usuario.isPresent()) {
            usuarioService.delete(id);
            return new ResponseEntity<> (HttpStatus.OK);
        }
        else {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }
}
