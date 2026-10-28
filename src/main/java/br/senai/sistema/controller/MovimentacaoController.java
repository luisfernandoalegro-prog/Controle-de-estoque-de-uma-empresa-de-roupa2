package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.TipoMovimentacao;
import br.senai.sistema.service.MovimentacaoService;
import br.senai.sistema.service.ProdutoService;
import br.senai.sistema.service.RegraNegocioException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final ProdutoService produtoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService, ProdutoService produtoService) {
        this.movimentacaoService = movimentacaoService;
        this.produtoService = produtoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) TipoMovimentacao tipo, Model model) {
        model.addAttribute("movimentacoes", movimentacaoService.buscarPorTipo(tipo));
        model.addAttribute("tipos", TipoMovimentacao.values());
        model.addAttribute("tipoSelecionado", tipo);
        return "movimentacoes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("movimentacao", new Movimentacao());
        model.addAttribute("produtos", produtoService.listarTodos());
        model.addAttribute("tipos", TipoMovimentacao.values());
        return "movimentacoes/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Movimentacao movimentacao, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("produtos", produtoService.listarTodos());
            model.addAttribute("tipos", TipoMovimentacao.values());
            return "movimentacoes/form";
        }

        try {
            movimentacaoService.salvar(movimentacao);
            attributes.addFlashAttribute("mensagem", "Movimentação registada com sucesso!");
        } catch (RegraNegocioException e) {
            model.addAttribute("produtos", produtoService.listarTodos());
            model.addAttribute("tipos", TipoMovimentacao.values());
            model.addAttribute("erro", e.getMessage());
            return "movimentacoes/form";
        }

        return "redirect:/movimentacoes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            movimentacaoService.excluir(id);
            attributes.addFlashAttribute("mensagem", "Movimentação excluída com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagem", "Erro ao excluir movimentação.");
        }
        return "redirect:/movimentacoes";
    }
}