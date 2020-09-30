package com.dbcompany.report;

import com.dbcompany.model.VendedorVenda;
import com.dbcompany.util.ParametrosUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MontarReport {

    private static final Logger LOGGER = Logger.getLogger(MontarReport.class.getName());

    public void escreverSaidaArquivo (final AtomicInteger qtdClientes,
                                      final AtomicInteger qtdVendedores,
                                      final List<VendedorVenda> listaValorVenda,
                                      final Map<String, Double> piorVenda,
                                      final AtomicReference<String> nomeArquivo) {
        try {
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("^^ DBCOMPANY DESAFIO ^^");
            relatorio.append("\nQuantidade de clientes: " + qtdClientes);
            relatorio.append("\nQuantidade de vendedores: " + qtdVendedores);
            relatorio.append("\nID e valor da vend mais cara: ID: " + listaValorVenda.get(0).getIdVenda() + " R$ " +
                    listaValorVenda.get(0).getValorVenda());
            relatorio.append("\nPior vendedor: Nome: " + ((Map.Entry) ((LinkedHashMap) piorVenda).entrySet().toArray()[0]).getKey() + " R$ " +
                    ((Map.Entry) ((LinkedHashMap) piorVenda).entrySet().toArray()[0]).getValue());

            Files.write(Paths.get(ParametrosUtil.userHomePath.concat(ParametrosUtil.dataOutPath)
                            .concat(nomeArquivo.get().replace(".dat", ""))
                            .concat(".done.dat")),
                    relatorio.toString().getBytes());
            LOGGER.info(ParametrosUtil.mensagemEscritaOkArquivo + nomeArquivo);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ParametrosUtil.mensagemErroArquivoSaida + e.getMessage());
        }
    }

}
