package com.caixa.fluxoDeCaixa.Controllers;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caixa.fluxoDeCaixa.DAO.CaixaDAO;
import com.caixa.fluxoDeCaixa.Enum.Status;
import com.caixa.fluxoDeCaixa.Models.Caixa;

@Controller
public class HomeController {

    @Autowired
    private final CaixaDAO caixaDAO;

    public HomeController(CaixaDAO caixaDAO) {
        this.caixaDAO = caixaDAO;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String tipo) {
        // Buscar a lista de caixas do banco de dados
        List<Caixa> caixas;
        
        if (tipo != null && !tipo.isEmpty()) {
            caixas = caixaDAO.findByTipoContainingIgnoreCase(tipo);
        } else {
            // Caso contrário, buscar todos os caixas
            caixas = caixaDAO.findAll();
        }

        // Calcular o total de receitas e despesas
        float totalReceitas = 0;
        float totalDespesas = 0;

        for (Caixa caixa : caixas) {
            if (caixa.getStatus().equals(Status.RECEITA)) {
                totalReceitas += caixa.getValor();
            } else if (caixa.getStatus().equals(Status.DESPESA)) {
                totalDespesas += caixa.getValor();
            }
        }

        // Calcular o valor total (saldo)
        float valorTotal = totalReceitas - totalDespesas;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String valorTotalFormatado = df.format(valorTotal);
        String totalReceitasFormatado = df.format(totalReceitas);
        String totalDespesasFormatado = df.format(totalDespesas);

        // Adicionar os atributos ao model
        model.addAttribute("caixas", caixas);
        model.addAttribute("totalReceitas", totalReceitasFormatado);
        model.addAttribute("totalDespesas", totalDespesasFormatado);
        model.addAttribute("valorTotal", valorTotalFormatado);

        return "home/index";
    }

    @GetMapping("/adicionar")
    public String adicionar() {
        return "home/adicionar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id) {
        if (caixaDAO.existsById(id)) {
            caixaDAO.deleteById(id);
        } else {
            System.out.println("Registro com ID " + id + " não encontrado.");
        }
        return "redirect:/";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestParam("tipo") String tipo,
                            @RequestParam("valor") float valor,
                            @RequestParam("status") int status,
                            Model model) {

        // Cria um novo objeto Caixa com os dados recebidos do formulário
        Caixa caixa = new Caixa();
        caixa.setTipo(tipo);
        caixa.setValor(valor);
        Status statusEnum = Status.values()[status];
        caixa.setStatus( statusEnum );

        // Salva o novo registro de caixa no banco de dados
        caixaDAO.save(caixa);

        // Redireciona para a página principal após o cadastro
        return "redirect:/";
    }

}
