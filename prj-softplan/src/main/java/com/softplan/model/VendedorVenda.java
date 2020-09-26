package com.softplan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class VendedorVenda {

    private String nome;
    private int idVenda;
    private Double valorVenda;

    public VendedorVenda(String nome, int idVenda, Double valorVenda){
        this.nome = nome;
        this.idVenda = idVenda;
        this.valorVenda = valorVenda;
    }

}
