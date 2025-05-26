package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.Consultor;
import com.realstate.imobiliaria.service.ConsultorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultores")
@CrossOrigin(origins = "*")
public class ConsultorController { 

    @Autowired
    private ConsultorService consultorService;

    @GetMapping
    public List<Consultor> listarTodos() {
        return consultorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Consultor buscarPorId(@PathVariable Long id) { 
        return consultorService.buscarPorId(id);
    }

    @PostMapping
    public Consultor salvar(@RequestBody Consultor consultor) {
        return consultorService.salvar(consultor);
    }

    @PutMapping("/{id}")
    public Consultor atualizar(@PathVariable Long id, @RequestBody Consultor consultor) { 
        return consultorService.atualizar(id, consultor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) { 
        consultorService.deletar(id);
    }
}

