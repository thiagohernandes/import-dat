package com.dbcompany.model;

import lombok.Data;

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
