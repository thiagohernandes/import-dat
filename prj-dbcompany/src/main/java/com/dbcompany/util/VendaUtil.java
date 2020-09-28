package com.dbcompany.util;

import com.dbcompany.model.VendedorVenda;

import java.util.concurrent.atomic.AtomicReference;

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
    public static AtomicReference<String> isClienteVendedorVenda(final String linha) {
        if(linha.split("ç")[1].length() < 11) {
            return new AtomicReference<String>("VENDA");
        }
        return linha.split("ç")[1].length() > 13 ? new AtomicReference<String>("CLIENTE") :
        new AtomicReference<String>("VENDEDOR");
    }

    /*  Retorno do valor total de venda por vendedor/item
        @since: 26/09/2020
        @param itens venda
        @param vendedor
        @param id venda
        @return VendedorVenda
    * */
    public static VendedorVenda totalVendaVendedor(String itensVenda, String vendedor, String idVenda) {
        String itensVendaFormatado = itensVenda.replace("[","").replace("]","");
        Double valorTotalVenda = 0.0;
        for(int x = 0; x < itensVendaFormatado.split(",").length; x++) {
            valorTotalVenda+=(Integer.valueOf(itensVendaFormatado.split(",")[x].split("-")[1])
                    * Float.parseFloat(itensVendaFormatado.split(",")[x].split("-")[2]));
        }
        return new VendedorVenda(vendedor,Integer.parseInt(idVenda),valorTotalVenda);
    }
}
