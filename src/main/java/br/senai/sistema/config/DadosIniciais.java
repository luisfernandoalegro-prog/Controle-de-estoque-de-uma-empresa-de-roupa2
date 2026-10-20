package br.senai.sistema.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Produto;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.ProdutoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProdutoRepository produtoRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (produtoRepository.count() == 0) {
            // Ordem do construtor: codigo, nome, descricao, precoCusto, precoVenda, estoqueMinimo
            Produto produto1 = new Produto("CAM-001", "Camiseta Básica Algodão", "Camiseta 100% algodão gola careca", new BigDecimal("25.00"), new BigDecimal("59.90"), 15);
            Produto produto2 = new Produto("CAL-002", "Calça Jeans Slim", "Calça jeans masculina modelagem slim", new BigDecimal("70.00"), new BigDecimal("149.90"), 10);
            Produto produto3 = new Produto("VES-003", "Vestido Midi Floral", "Vestido feminino estampado para o dia a dia", new BigDecimal("60.00"), new BigDecimal("129.90"), 8);
            
            produtoRepository.save(produto1);
            produtoRepository.save(produto2);
            produtoRepository.save(produto3);
        }
    }
}