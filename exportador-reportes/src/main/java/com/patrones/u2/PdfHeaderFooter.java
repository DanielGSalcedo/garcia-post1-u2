package com.patrones.u2;

final class PdfHeaderFooter implements ReportHeaderFooter {
    @Override
    public String renderHeader(String institutionName) {
        return "[PDF:encabezado] " + (institutionName + " | Acta de calificaciones");
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[PDF:pie] " + ("Página " + pageNumber + " | Firma: __________");
    }
}
