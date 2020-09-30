package com.dbcompany;

import com.dbcompany.model.VendedorVenda;
import com.dbcompany.report.MontarReport;
import com.dbcompany.report.WatchKeyReport;
import com.dbcompany.util.ParametrosUtil;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException {
        try {
            List<String> arquivosImportados = new ArrayList<>();
            List<VendedorVenda> listaValorVenda = new ArrayList<>();
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(ParametrosUtil.userHomePath.concat(ParametrosUtil.path));
            WatchKeyReport.poolEventsHandler(path, watchService, arquivosImportados, listaValorVenda);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ParametrosUtil.mensagemErroMainClass + e.getMessage());
        }

    }
}
