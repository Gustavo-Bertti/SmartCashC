package br.com.fiap.SmartCash.Assinatura;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND; 
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("assinatura")
@CacheConfig(cacheNames = "assinaturas")
@Tag(name = "Assinaturas", description = "Endpoint relacionado com Assinaturas")
public class AssinaturaController {

    @Autowired
    AssinaturaRepository repository;
    
    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public Assinatura create(@RequestBody @Valid Assinatura assinatura){
        return repository.save(assinatura);
    }
    
    @GetMapping
    @Cacheable
    public List<Assinatura> readAll(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Assinatura> readItem(@PathVariable Long id){
        return repository.findById(id)
                         .map(ResponseEntity::ok) 
                         .orElse(ResponseEntity.notFound().build());      
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    @CacheEvict(allEntries = true)
    public Assinatura update(@PathVariable Long id, @RequestBody Assinatura assinatura){
        verificarSeExisteAssinatura(id);
        assinatura.setID_ASSINATURA(id);
        return repository.save(assinatura);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable Long id){        
        verificarSeExisteAssinatura(id);
        repository.deleteById(id);
    }

    private Assinatura verificarSeExisteAssinatura(Long id) {
        return repository.findById(id)
                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Assinatura não encontrada"));
    }

}
