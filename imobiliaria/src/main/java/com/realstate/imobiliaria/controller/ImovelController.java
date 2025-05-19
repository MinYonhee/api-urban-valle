package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.Imovel;
import com.realstate.imobiliaria.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imoveis")
@CrossOrigin(origins = "*") // Permite chamadas do front-end
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @GetMapping
    public List<Imovel> listarTodos() {
        return imovelService.listarTodos();
    }

    @GetMapping("/{id}")
    public Imovel buscarPorId(@PathVariable Long id) {
        return imovelService.buscarPorId(id);
    }

    @PostMapping
    public Imovel criar(@RequestBody Imovel imovel) {
        return imovelService.salvar(imovel);
    }

    @PutMapping("/{id}")
    public Imovel atualizar(@PathVariable Long id, @RequestBody Imovel imovel) {
        return imovelService.atualizar(id, imovel);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        imovelService.deletar(id);
    }
}
