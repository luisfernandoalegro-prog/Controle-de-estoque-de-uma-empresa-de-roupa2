package br.senai.sistema.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Produto;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.CategoriaRepository;
import br.senai.sistema.repository.ProdutoRepository;
import br.senai.sistema.repository.UsuarioRepository;

@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria("Feminino", "Roupas e acessórios femininos"));
            categoriaRepository.save(new Categoria("Masculino", "Roupas e acessórios masculinos"));
            categoriaRepository.save(new Categoria("Infantil", "Roupas e acessórios infantis"));
        }

        produtoRepository.deleteAll();

        Produto produto1 = new Produto("CAM-001", "Camiseta Básica Algodão", "Camiseta 100% algodão gola", new BigDecimal("25.00"), new BigDecimal("59.90"), 15, 50, "UN", "Corredor A - Prateleira 1");
        Produto produto2 = new Produto("CAL-002", "Calça Jeans", "Calça jeans masculina modelagem", new BigDecimal("70.00"), new BigDecimal("149.90"), 10, 30, "UN", "Corredor B - Prateleira 2");
        Produto produto3 = new Produto("VES-003", "Vestido ", "Vestido feminino estampado", new BigDecimal("60.00"), new BigDecimal("129.90"), 8, 7, "UN", "Corredor C - Prateleira 3");
        
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);
        produtoRepository.save(produto3);
    }
}