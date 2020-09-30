package com.dbcompany.report;

import com.dbcompany.model.VendedorVenda;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(MockitoJUnitRunner.class)
public class MontarReportTest {

    private final MontarReport montarReport = new MontarReport();
    private AtomicInteger qtdClientes = new AtomicInteger(2);
    private AtomicInteger qtdVendedores = new AtomicInteger(4);
    private List<VendedorVenda> listaValorVenda = Arrays.asList(new VendedorVenda("Beltrano",22,400.3));
    private Map<String, Double> piorVenda = new HashMap<>();
    private AtomicReference<String> nomeArquivo = new AtomicReference<>("arquivo-teste");

    @Before
    public void init() {
        piorVenda.put("Ciclano", 33.2);
    }

    @Test
    public void escreverSaidaSuccess__Test() {
        montarReport.escreverSaidaArquivo(qtdClientes, qtdVendedores, listaValorVenda,piorVenda,nomeArquivo);
    }
}
