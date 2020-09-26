package com.softplan.report;

import com.softplan.model.VendedorVenda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MontarReport {

    public void escreverSaidaArquivo (AtomicInteger qtdClientes,
                                      AtomicInteger qtdVendedores,
                                      List<VendedorVenda> listaValorVenda,
                                      Map<String, Double> piorVenda,
                                      AtomicReference<String> nomeArquivo) throws IOException {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("-- SOFTPLAN DESAFIO --");
        relatorio.append("\nQuantidade de clientes: " + qtdClientes);
        relatorio.append("\nQuantidade de vendedores: " + qtdVendedores);
        relatorio.append("\nID e valor da vend mais cara: ID: " + listaValorVenda.get(0).getIdVenda() + " R$ " +
                listaValorVenda.get(0).getValorVenda());
        relatorio.append("\nPior vendedor: Nome: " + ((Map.Entry) ((LinkedHashMap) piorVenda).entrySet().toArray()[0]).getKey() + " R$ " +
                ((Map.Entry) ((LinkedHashMap) piorVenda).entrySet().toArray()[0]).getValue());

        Files.write(Paths.get(System.getProperty("user.home").concat("//data//out//")
                        .concat(nomeArquivo.get().replace(".dat",""))
                        .concat(".done.dat")),
                        relatorio.toString().getBytes());
    }

}
