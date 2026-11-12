package br.senai.sistema.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.senai.sistema.service.DiagnosticoService;
import br.senai.sistema.service.PainelService;

@Controller
public class InicioController {

    private final PainelService painelService;
    private final DiagnosticoService diagnosticoService;

    public InicioController(PainelService painelService, DiagnosticoService diagnosticoService) {
        this.painelService = painelService;
        this.diagnosticoService = diagnosticoService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("indicadores", painelService.getIndicadores());
        model.addAttribute("totalProdutos", painelService.getTotalProdutos());
        model.addAttribute("produtosAbaixoMinimoCount", painelService.getProdutosAbaixoMinimoCount());
        model.addAttribute("produtosAbaixoMinimo", painelService.getProdutosAbaixoMinimo());
        model.addAttribute("totalEntradas", painelService.getTotalEntradas());
        model.addAttribute("totalSaidas", painelService.getTotalSaidas());
        model.addAttribute("valorTotalEstoque", painelService.getValorTotalEstoque());
        model.addAttribute("statusSaudeEstoque", painelService.getStatusSaudeEstoque()); // DIFERENCIAL
        return "index";
    }

    @GetMapping("/painel")
    public String painel(Model model) {
        model.addAttribute("indicadores", painelService.getIndicadores());
        model.addAttribute("totalProdutos", painelService.getTotalProdutos());
        model.addAttribute("produtosAbaixoMinimoCount", painelService.getProdutosAbaixoMinimoCount());
        model.addAttribute("produtosAbaixoMinimo", painelService.getProdutosAbaixoMinimo());
        model.addAttribute("totalEntradas", painelService.getTotalEntradas());
        model.addAttribute("totalSaidas", painelService.getTotalSaidas());
        model.addAttribute("valorTotalEstoque", painelService.getValorTotalEstoque());
        model.addAttribute("statusSaudeEstoque", painelService.getStatusSaudeEstoque()); // DIFERENCIAL
        return "index";
    }

    @GetMapping("/diagnostico")
    public String diagnostico(Model model) {
        Map<String, DiagnosticoService.Item> itens = diagnosticoService.verificar();
        model.addAttribute("itens", itens.values());
        model.addAttribute("tudoOk", diagnosticoService.tudoOk(itens));
        return "diagnostico";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}