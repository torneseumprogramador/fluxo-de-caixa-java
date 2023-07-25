package com.caixa.fluxoDeCaixa.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caixa.fluxoDeCaixa.Models.Caixa;

public interface CaixaDAO extends JpaRepository<Caixa, Integer> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário
    // O JpaRepository já fornece métodos básicos para CRUD (Create, Read, Update, Delete)

    List<Caixa> findByTipoContainingIgnoreCase(String tipo);
}
