package br.com.fiap.vitorportelaf.modelocarro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModeloController {

    @GetMapping("/modelos")
    public String listarModelos() {
        return "Modelos disponíveis: Onix, Ka, Corolla";
    }

}