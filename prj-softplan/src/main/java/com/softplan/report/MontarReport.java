package com.softplan.report;

import com.softplan.model.VendedorVenda;
import com.softplan.util.ParametrosUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
    MontarReport - funçõe de relatório
    @since: 26/09/2020
    @author: Thiago Hernandes
 */
public class MontarReport {

    /*  Criar arquivo no diretório data/out
        @since: 26/09/2020
        @param qtd de clientes
        @param qtd de vendedores
        @param lista com valores venda
        @param pior venda (vendedor e valor)
        @return void
    * */
    public void escreverSaidaArquivo (final AtomicInteger qtdClientes,
                                      final AtomicInteger qtdVendedores,
                                      final List<VendedorVenda> listaValorVenda,
                                      final Map<String, Double> piorVenda,
                                      final AtomicReference<String> nomeArquivo) {
        try {
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("-- SOFTPLAN DESAFIO --");
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
        } catch (Exception e) {
            System.err.println("Houve o seguinte erro: "+ e.getMessage());
        }
    }

}
