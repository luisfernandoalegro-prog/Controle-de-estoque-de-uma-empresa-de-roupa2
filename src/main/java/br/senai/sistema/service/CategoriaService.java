package br.senai.sistema.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Busca por nome ou retorna todas se o termo estiver vazio
    public List<Categoria> buscarPorNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            return categoriaRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return listarTodas();
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }
}