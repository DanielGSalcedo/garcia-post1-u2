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
            System.out.println(service.export(format, records, "UFPS"));
        }
    }
}
