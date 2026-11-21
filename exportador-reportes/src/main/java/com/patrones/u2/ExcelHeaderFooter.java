package com.patrones.u2;

final class ExcelHeaderFooter implements ReportHeaderFooter {
    @Override
    public String renderHeader(String institutionName) {
        return "[XLSX:encabezado] " + ("Hoja: " + institutionName + " | Notas");
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[XLSX:pie] " + ("Bloque " + pageNumber);
    }
}
