package br.com.fiap.esportes.controller;

import br.com.fiap.esportes.model.Brinquedo;
import br.com.fiap.esportes.repository.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brinquedos")
public class BrinquedoController {

    @Autowired
    private BrinquedoRepository brinquedoRepository;

    // Endpoint para listar todos os brinquedos
    @GetMapping
    public String listarBrinquedos(Model model) {
        model.addAttribute("brinquedos", brinquedoRepository.findAll());
        return "listarBrinquedos"; // Página Thymeleaf que listará os brinquedos
    }
    // Endpoint para buscar um brinquedo por ID
    @GetMapping("/{id}")
    public String buscarPorId(@PathVariable Long id, Model model) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brinquedo não encontrado: " + id));
        model.addAttribute("brinquedo", brinquedo);
        return "detalheBrinquedo"; // Página Thymeleaf que mostrará os detalhes
    }

    // Endpoint para exibir o formulário de cadastro
    @GetMapping("/novo")
    public String novoBrinquedoForm(Model model) {
        model.addAttribute("brinquedo", new Brinquedo());
        return "novoBrinquedo"; // Página Thymeleaf para o formulário de criação
    }

    // Endpoint para criar um novo brinquedo
    @PostMapping
    public String criarBrinquedo(@ModelAttribute Brinquedo brinquedo) {
        brinquedoRepository.save(brinquedo);
        return "redirect:/brinquedos"; // Redireciona para a lista de brinquedos
    }

    // Endpoint para editar um brinquedo existente
    @GetMapping("/editar/{id}")
    public String editarBrinquedo(@PathVariable Long id, Model model) {
        Brinquedo brinquedo = brinquedoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brinquedo não encontrado"));
        model.addAttribute("brinquedo", brinquedo);
        return "editarBrinquedo"; // Página Thymeleaf para editar o brinquedo
    }

    // Endpoint para atualizar um brinquedo existente
    @PostMapping("/editar/{id}")
    public String atualizarBrinquedo(@PathVariable Long id, @ModelAttribute Brinquedo brinquedo) {
        brinquedo.setId(id);
        brinquedoRepository.save(brinquedo);
        return "redirect:/brinquedos"; // Redireciona para a lista de brinquedos
    }

    // Endpoint para excluir um brinquedo
    @GetMapping("/excluir/{id}")
    public String excluirBrinquedo(@PathVariable Long id) {
        brinquedoRepository.deleteById(id);
        return "redirect:/brinquedos"; // Redireciona para a lista de brinquedos
    }
}
