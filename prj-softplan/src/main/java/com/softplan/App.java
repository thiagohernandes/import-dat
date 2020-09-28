package com.softplan;

import com.softplan.model.VendedorVenda;
import com.softplan.report.MontarReport;
import com.softplan.util.ParametrosUtil;
import com.softplan.util.VendaUtil;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    App - classe principal para execução do importador de arquivos (.dat)
    @since: 26/09/2020
    @author: Thiago Hernandes
 */

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> arquivosImportados = new ArrayList<>();

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(ParametrosUtil.userHomePath.concat(ParametrosUtil.path));

        path.register(watchService,StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey key;
        while ((key = watchService.take()) != null ) {
            for (WatchEvent<?> event : key.pollEvents()) {
                AtomicReference<String> nomeArquivo = new AtomicReference<>("");
                    AtomicBoolean importouArquivo = new AtomicBoolean(false);
                        nomeArquivo.getAndSet(event.context().toString());
                        if (arquivosImportados.stream().filter(nome -> nome.equalsIgnoreCase(nomeArquivo.get())).count() > 0) {
                            importouArquivo.getAndSet(true);
                        }
                        if (importouArquivo.get() == false) {
                            arquivosImportados.add(nomeArquivo.get());
                        }

                    if (importouArquivo.get() == false && nomeArquivo.get() != null) {

                        List<VendedorVenda> listaValorVenda = new ArrayList<>();
                        VendaUtil vendaUtil = new VendaUtil();

                        String arquivoImportacao = ParametrosUtil.userHomePath.concat(ParametrosUtil.dataInPath)
                                .concat(nomeArquivo.get());

                        AtomicInteger qtdClientes = new AtomicInteger(0);
                        AtomicInteger qtdVendedores = new AtomicInteger(0);

                        if (arquivoImportacao != null) {

                            try (Stream<String> stream = Files.lines(Paths.get(arquivoImportacao))) {
                                stream.forEach(item -> {
                                    if (vendaUtil.isClienteVendedorVenda(item).equalsIgnoreCase(ParametrosUtil.venda)) {
                                        listaValorVenda.add(vendaUtil.totalVendaVendedor(item.split("ç")[2],
                                                item.split("ç")[3], item.split("ç")[1]));
                                    }
                                    if (vendaUtil.isClienteVendedorVenda(item).equalsIgnoreCase(ParametrosUtil.vendedor)) {
                                        qtdVendedores.incrementAndGet();
                                    }
                                    if (vendaUtil.isClienteVendedorVenda(item).equalsIgnoreCase(ParametrosUtil.cliente)) {
                                        qtdClientes.incrementAndGet();
                                    }
                                });

                                listaValorVenda.sort(Comparator.comparing(VendedorVenda::getValorVenda).reversed());

                                Map<String, Double> listaValorVendaGroupBy = listaValorVenda.stream().collect(
                                        Collectors.groupingBy(VendedorVenda::getNome, Collectors.summingDouble(VendedorVenda::getValorVenda)));

                                Map<String, Double> piorVenda = new LinkedHashMap<>();

                                listaValorVendaGroupBy.entrySet().stream()
                                        .sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> piorVenda.put(e.getKey(), e.getValue()));

                                new MontarReport().escreverSaidaArquivo(qtdClientes, qtdVendedores, listaValorVenda, piorVenda, nomeArquivo);
                                Files.deleteIfExists(Paths.get(ParametrosUtil.userHomePath.concat(ParametrosUtil.dataInPath).concat(nomeArquivo.get())));
                                arquivoImportacao = null;
                                System.out.println(ParametrosUtil.mensagemOk);
                            } catch (IOException e) {
                                System.err.print(ParametrosUtil.mensagemErro + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
            }
            key.reset();
        }
    }
}