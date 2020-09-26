package com.softplan;

import com.softplan.model.VendedorVenda;
import com.softplan.report.MontarReport;
import com.softplan.util.VendaUtil;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> arquivosImportados = new ArrayList<>();

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(System.getProperty("user.home").concat("//data//in"));

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

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

                        List<VendedorVenda> listaValorVenda = new ArrayList<>();
                        VendaUtil vendaUtil = new VendaUtil();

                        String fileName = System.getProperty("user.home").concat("//data//in//").concat(nomeArquivo.get());

                        AtomicInteger qtdClientes = new AtomicInteger(0);
                        AtomicInteger qtdVendedores = new AtomicInteger(0);

                        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                            stream.forEach(item -> {
                                if (vendaUtil.isClienteVendedorVenda(item).equalsIgnoreCase("VENDA")) {
                                    listaValorVenda.add(vendaUtil.totalVendaVendedor(item.split("ç")[2],
                                            item.split("ç")[3], item.split("ç")[1]));
                                }
                                if (vendaUtil.isClienteVendedorVenda(item).equalsIgnoreCase("VENDEDOR")) {
                                    qtdVendedores.incrementAndGet();
                                }
                                if (vendaUtil.isClienteVendedorVenda(item).equalsIgnoreCase("CLIENTE")) {
                                    qtdClientes.incrementAndGet();
                                }
                            });

                            listaValorVenda.sort(Comparator.comparing(VendedorVenda::getValorVenda).reversed());

                            Map<String, Double> listaValorVendaGroupBy = listaValorVenda.stream().collect(
                                    Collectors.groupingBy(VendedorVenda::getNome, Collectors.summingDouble(VendedorVenda::getValorVenda)));

                            Map<String, Double> piorVenda = new LinkedHashMap<>();

                            listaValorVendaGroupBy.entrySet().stream()
                                    .sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> piorVenda.put(e.getKey(), e.getValue()));

                            new MontarReport().escreverSaidaArquivo(qtdClientes,qtdVendedores,listaValorVenda,piorVenda,nomeArquivo);

                            System.out.println("Sucesso na importação do arquivo!");
                        } catch (IOException e) {
                            System.err.print("Erro ao importar arquivo! - Erro original: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
            }
            key.reset();
        }
    }

}
