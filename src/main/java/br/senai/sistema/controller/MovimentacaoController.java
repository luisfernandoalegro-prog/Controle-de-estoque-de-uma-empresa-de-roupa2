package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.TipoMovimentacao;
import br.senai.sistema.service.MovimentacaoService;
import br.senai.sistema.service.ProdutoService;
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
        return "movimentacoes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("movimentacao", new Movimentacao());
        model.addAttribute("produtos", produtoService.listarTodos());
        return "movimentacoes/form";
    }

    // Suporta rotas de edição por /movimentacoes/editar/{id} ou /movimentacoes/{id}/editar
    @GetMapping({"/editar/{id}", "/{id}/editar"})
    public String editar(@PathVariable Long id, Model model, RedirectAttributes atributos) {
        return movimentacaoService.buscarPorId(id)
                .map(mov -> {
                    model.addAttribute("movimentacao", mov);
                    model.addAttribute("produtos", produtoService.listarTodos());
                    return "movimentacoes/form";
                })
                .orElseGet(() -> {
                    atributos.addFlashAttribute("erro", "Movimentação não encontrada.");
                    return "redirect:/movimentacoes";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("movimentacao") Movimentacao movimentacao, BindingResult resultado,
                         Model model, RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            model.addAttribute("produtos", produtoService.listarTodos());
            return "movimentacoes/form";
        }
        try {
            movimentacaoService.salvar(movimentacao);
            atributos.addFlashAttribute("mensagem", "Movimentação salva com sucesso.");
            return "redirect:/movimentacoes";
        } catch (Exception e) {
            // Mantém na mesma página do formulário e exibe a mensagem de erro exata (ex: saldo insuficiente)
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("produtos", produtoService.listarTodos());
            return "movimentacoes/form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            movimentacaoService.excluir(id);
            atributos.addFlashAttribute("mensagem", "Movimentação excluída e stock revertido com sucesso.");
        } catch (Exception e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/movimentacoes";
    }
}