package com.dbcompany.report;

import com.dbcompany.model.VendedorVenda;
import com.dbcompany.util.ParametrosUtil;
import com.dbcompany.util.VendaUtil;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WatchKeyReport {

    private static final Logger LOGGER = Logger.getLogger(WatchKeyReport.class.getName());

    public static void poolEventsHandler(final Path path,
                                          final WatchService watchService,
                                          final List<String> arquivosImportados,
                                          final List<VendedorVenda> listaValorVenda) {

        try {

            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchService.take()) != null) {
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

                        final String arquivoImportacao = ParametrosUtil.userHomePath.concat(ParametrosUtil.dataInPath)
                                .concat(nomeArquivo.get());

                        AtomicInteger qtdClientes = new AtomicInteger(0);
                        AtomicInteger qtdVendedores = new AtomicInteger(0);

                        if (arquivoImportacao != null) {

                            try (final Stream<String> stream = Files.lines(Paths.get(arquivoImportacao))) {
                                stream.forEach(item -> {
                                    if (VendaUtil.isClienteVendedorVenda(item).get() == ParametrosUtil.venda) {
                                        listaValorVenda.add(VendaUtil.totalVendaVendedor(item.split("ç")[2],
                                                item.split("ç")[3], item.split("ç")[1]));
                                    }
                                    if (VendaUtil.isClienteVendedorVenda(item).get() == ParametrosUtil.vendedor) {
                                        qtdVendedores.incrementAndGet();
                                    }
                                    if (VendaUtil.isClienteVendedorVenda(item).get() == ParametrosUtil.cliente) {
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

                                LOGGER.info(ParametrosUtil.mensagemOk + nomeArquivo);

                            } catch (IOException e) {
                                LOGGER.log(Level.SEVERE,ParametrosUtil.mensagemErro + e.getMessage());
                            }
                        }
                    }
                }
                key.reset();
            }
        }catch (Exception e ) {
            LOGGER.log(Level.SEVERE,ParametrosUtil.mensangemErroHandlerPoolArquivos + e.getMessage());
        }
    }
}
