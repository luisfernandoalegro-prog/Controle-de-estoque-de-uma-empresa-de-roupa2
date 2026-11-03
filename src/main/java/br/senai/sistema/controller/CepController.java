package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senai.sistema.service.CepService;

@Controller
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/cep")
    public String paginaCep(@RequestParam(required = false) String cep, Model model) {
        if (cep != null && !cep.isEmpty()) {
            // Remove caracteres não numéricos
            String cepLimpo = cep.replaceAll("\\D", "");
            CepService.CepResponse resultado = cepService.consultarCep(cepLimpo);
            model.addAttribute("resultado", resultado);
            model.addAttribute("cepBuscado", cep);
        }
        return "cep";
    }
}