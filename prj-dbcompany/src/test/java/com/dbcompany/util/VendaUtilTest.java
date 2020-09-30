package com.dbcompany.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicReference;

@RunWith(MockitoJUnitRunner.class)
public class VendaUtilTest {

    private final String linhaVendedor = "001ç1234567891234çPedroç50000";
    private final String linhaCliente = "002ç2345675434544345çJose da SilvaçRural";
    private final String linhaVenda = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
    private final String itensVenda = "[1-10-100]";
    private final String vendedor = "Pedro";
    private final int idVenda = 1;
    private final Double valorVenda = 1000.0;

    @Test
    public void isClienteVendedorVendaSuccessVendedor__Test() {
        AtomicReference<String> vendedor = new AtomicReference<>("VENDEDOR");
        Assert.assertEquals(vendedor.get(),VendaUtil.isClienteVendedorVenda(linhaVendedor).get());
    }

    @Test
    public void isClienteVendedorVendaSuccessVenda__Test() {
        AtomicReference<String> vendedor = new AtomicReference<>("VENDA");
        Assert.assertEquals(vendedor.get(),VendaUtil.isClienteVendedorVenda(linhaVenda).get());
    }

    @Test
    public void isClienteVendedorVendaSuccessCliente__Test() {
        AtomicReference<String> vendedor = new AtomicReference<>("CLIENTE");
        Assert.assertEquals(vendedor.get(),VendaUtil.isClienteVendedorVenda(linhaCliente).get());
    }

    @Test
    public void isClienteVendedorVendaSuccess__Test() {
        AtomicReference<String> vendedor = new AtomicReference<>("VENDEDOR");
        Assert.assertEquals(vendedor.get(),VendaUtil.isClienteVendedorVenda(linhaVendedor).get());
    }

    @Test
    public void totalVendaVendedorSuccess__Test() {
        Assert.assertEquals(valorVenda,
                VendaUtil.totalVendaVendedor(itensVenda,vendedor,String.valueOf(idVenda)).getValorVenda());
    }

}
