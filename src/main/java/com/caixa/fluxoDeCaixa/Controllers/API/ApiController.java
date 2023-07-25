package com.caixa.fluxoDeCaixa.Controllers.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.caixa.fluxoDeCaixa.DAO.CaixaDAO;
import com.caixa.fluxoDeCaixa.DTO.ResumoCaixa;
import com.caixa.fluxoDeCaixa.Enum.Status;
import com.caixa.fluxoDeCaixa.Models.Caixa;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class ApiController {

    @Autowired
    private final CaixaDAO caixaDAO;

    public ApiController(CaixaDAO caixaDAO) {
        this.caixaDAO = caixaDAO;
    }

    @GetMapping("/api/caixas")
    public List<Caixa> getCaixas() {
        return caixaDAO.findAll();
    }

    @PostMapping("/api/caixas")
    public Caixa cadastrarCaixa(@RequestBody Caixa caixa) {
        return caixaDAO.save(caixa);
    }

    @DeleteMapping("caixas/{id}")
    public void excluirCaixa(@PathVariable int id) {
        caixaDAO.deleteById(id);
    }

    @GetMapping("/api/caixas/resumo")
    public ResumoCaixa getResumoCaixas() {
        List<Caixa> caixas = caixaDAO.findAll();
        float totalReceitas = 0;
        float totalDespesas = 0;

        for (Caixa caixa : caixas) {
            if (caixa.getStatus().equals(Status.RECEITA)) {
                totalReceitas += caixa.getValor();
            } else if (caixa.getStatus().equals(Status.DESPESA)) {
                totalDespesas += caixa.getValor();
            }
        }

        float valorTotal = totalReceitas - totalDespesas;

        return new ResumoCaixa(caixas, totalReceitas, totalDespesas, valorTotal);
    }
}
