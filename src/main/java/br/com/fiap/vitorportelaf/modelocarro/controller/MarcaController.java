package br.com.fiap.vitorportelaf.modelocarro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarcaController {

    @GetMapping("/marcas")
    public String listarMarcas() {
        return "Marcas disponíveis: Chevrolet, Ford, Toyota";
    }

    @GetMapping("/marcas/destaque")
    public String marcaDestaque() {
        return "Marca destaque do mês: Chevrolet";
    }

}