package com.dbcompany.util;

import com.dbcompany.model.VendedorVenda;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaUtil {

    private static final Logger LOGGER = Logger.getLogger(VendaUtil.class.getName());

    public static AtomicReference<String> isClienteVendedorVenda(final String linha) {
        try {
            if (linha.split("ç")[1].length() < 11) {
                return new AtomicReference<String>("VENDA");
            }
            return linha.split("ç")[1].length() > 13 ? new AtomicReference<String>("CLIENTE") :
                    new AtomicReference<String>("VENDEDOR");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,ParametrosUtil.mensagemErroHandlerTipoLinha + e.getMessage());
        }
        return null;
    }

    public static VendedorVenda totalVendaVendedor(String itensVenda, String vendedor, String idVenda) {
       try {
           String itensVendaFormatado = itensVenda.replace("[", "").replace("]", "");
           Double valorTotalVenda = 0.0;
           for (int x = 0; x < itensVendaFormatado.split(",").length; x++) {
               valorTotalVenda += (Integer.valueOf(itensVendaFormatado.split(",")[x].split("-")[1])
                       * Float.parseFloat(itensVendaFormatado.split(",")[x].split("-")[2]));
           }
           return new VendedorVenda(vendedor, Integer.parseInt(idVenda), valorTotalVenda);
       } catch (Exception e) {
           LOGGER.log(Level.SEVERE,ParametrosUtil.mensagemErroHandlerValorVenda + e.getMessage());
       }
       return null;
    }
}
