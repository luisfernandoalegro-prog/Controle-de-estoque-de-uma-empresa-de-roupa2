package br.senai.sistema.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Produto;
import br.senai.sistema.service.CategoriaService;
import br.senai.sistema.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

    public ProdutoController(ProdutoService produtoService, CategoriaService categoriaService) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produtos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") Produto produto, BindingResult resultado,
                         Model model, RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "produtos/form";
        }
        try {
            produtoService.salvar(produto);
        } catch (DataIntegrityViolationException e) {
            resultado.rejectValue("codigo", "duplicado", "Já existe um registo com este código.");
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "produtos/form";
        }
        atributos.addFlashAttribute("mensagem", "Produto salvo com sucesso.");
        return "redirect:/produtos";
    }

    @GetMapping({"/editar/{id}", "/{id}/editar"})
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("ID inválido")));
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produtos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        produtoService.excluir(id);
        atributos.addFlashAttribute("mensagem", "Produto excluído com sucesso.");
        return "redirect:/produtos";
    }
}