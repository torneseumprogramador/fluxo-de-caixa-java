package com.caixa.fluxoDeCaixa.DTO;

import java.util.List;

import com.caixa.fluxoDeCaixa.Models.Caixa;

public class ResumoCaixa {

    private List<Caixa> caixas;
    private float totalReceitas;
    private float totalDespesas;
    private float valorTotal;

    public ResumoCaixa(List<Caixa> caixas, float totalReceitas, float totalDespesas, float valorTotal) {
        this.caixas = caixas;
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.valorTotal = valorTotal;
    }

    public List<Caixa> getCaixas() {
        return caixas;
    }

    public void setCaixas(List<Caixa> caixas) {
        this.caixas = caixas;
    }

    public float getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(float totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public float getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(float totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
