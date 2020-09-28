package com.softplan.util;

import com.softplan.model.VendedorVenda;

/*
    VendaUtil - funções de tratativa de vendedor e venda
    @since: 26/09/2020
    @author: Thiago Hernandes
 */

public class VendaUtil {

    /*  Handler de diferenciação entre: venda, cliente e vendedor
        @since: 26/09/2020
        @param linha do arquivo .dat
        @return cliente, vendedor ou venda
    * */
    public static final String isClienteVendedorVenda(final String linha) {
        if(linha.split("ç")[1].length() < 11) {
            return "VENDA";
        }
        return linha.split("ç")[1].length() > 13 ? "CLIENTE" : "VENDEDOR";
    }

    /*  Retorno do valor total de venda por vendedor/item
        @since: 26/09/2020
        @param itens venda
        @param vendedor
        @param id venda
        @return VendedorVenda
    * */
    public static final VendedorVenda totalVendaVendedor(final String itensVenda, final String vendedor, final String idVenda) {
        final String itensVendaFormatado = itensVenda.replace("[","").replace("]","");
        Double valorTotalVenda = 0.0;
        for(int x = 0; x < itensVendaFormatado.split(",").length; x++) {
            valorTotalVenda+=(Integer.valueOf(itensVendaFormatado.split(",")[x].split("-")[1])
                    * Float.parseFloat(itensVendaFormatado.split(",")[x].split("-")[2]));
        }
        return new VendedorVenda(vendedor,Integer.parseInt(idVenda),valorTotalVenda);
    }
}
