package com.softplan.util;

import com.softplan.model.VendedorVenda;

public class VendaUtil {

    public static String isClienteVendedorVenda(String linha) {
        if(linha.split("ç")[1].length() < 11) {
            return "VENDA";
        }
        return linha.split("ç")[1].length() > 13 ? "CLIENTE" : "VENDEDOR";
    }

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
