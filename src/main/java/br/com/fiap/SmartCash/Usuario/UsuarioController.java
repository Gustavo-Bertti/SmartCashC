package br.com.fiap.SmartCash.Usuario;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND; 
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    //@Autowired
    UsuarioRepository repository;
    
    public UsuarioController(UsuarioRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Usuario create(@RequestBody @Valid Usuario usuario){        
        usuario.setLOGIN_USUARIO(usuario.getNOME().substring(0, 1) + 
                            (usuario.getDOCUMENTO().length() == 11 ? 
                            usuario.getDOCUMENTO().substring(7)  
                            : 
                            usuario.getDOCUMENTO().substring(10))        
        );                                         
        return repository.save(usuario);
    }
    
    @GetMapping
    public List<Usuario> readAll(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> readItem(@PathVariable Long id){
        return repository.findById(id)
                         .map(ResponseEntity::ok) 
                         .orElse(ResponseEntity.notFound().build());      
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario){
        verificarSeExisteUsuario(id);
        usuario.setID_USUARIO(id);
        return repository.save(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id){        
        verificarSeExisteUsuario(id);
        repository.deleteById(id);
    }

    private Usuario verificarSeExisteUsuario(Long id) {
        return repository.findById(id)
                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrada"));
    }

}
