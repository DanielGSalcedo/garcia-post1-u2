package com.patrones.u2;

import java.util.List;

public final class Main {
    public static void main(String[] args) {
        ReportFactoryRegistry.register("pdf", PdfReportFactory::new);
        ReportFactoryRegistry.register("excel", ExcelReportFactory::new);
        ReportFactoryRegistry.register("html", HtmlReportFactory::new);

        var service = new ReportExportService();
        var records = List.of(new GradeRecord("A01", "Ana Torres", "IS-301", 4.2),
                              new GradeRecord("A02", "Luis Rey", "IS-301", 3.8),
                              new GradeRecord("A03", "Marta Díaz", "IS-301", 4.7));
        for (String format : List.of("pdf", "excel", "html")) {
            System.out.println("=== Formato " + format + " ===");
            System.out.println(service.export(format, records, "UFPS"));
        }

        var defaults = new ExportConfig.Builder("pdf").build();
        var custom = new ExportConfig.Builder("excel")
                .pageSize("LETTER").orientation("LANDSCAPE").locale("en-US")
                .watermarkText("BORRADOR").includeLogo(false).maxRowsPerPage(2)
                .build();
        System.out.println("=== Configuración por defecto ===");
        System.out.println(service.export(defaults, records, "UFPS"));
        System.out.println("=== Configuración personalizada ===");
        System.out.println(service.export(custom, records, "UFPS"));

        try {
            new ExportConfig.Builder("html").compress(true).build();
        } catch (IllegalStateException ex) {
            System.out.println("Configuración rechazada: " + ex.getMessage());
        }
        try {
            service.export("xml", records, "UFPS");
        } catch (IllegalArgumentException ex) {
            System.out.println("Formato rechazado: " + ex.getMessage());
        }
    }
}
