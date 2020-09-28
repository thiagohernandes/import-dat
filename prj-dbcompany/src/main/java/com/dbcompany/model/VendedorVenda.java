package com.dbcompany.model;

import lombok.Data;


/*
    VendedorVenda - classe auxiliar para armazenar dados de vededor, id venda e
    valor de venda por itens
    @since: 26/09/2020
    @author: Thiago Hernandes
 */

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
