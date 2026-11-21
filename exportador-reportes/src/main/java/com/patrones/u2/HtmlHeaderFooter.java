package com.patrones.u2;

final class HtmlHeaderFooter implements ReportHeaderFooter {
    @Override
    public String renderHeader(String institutionName) {
        return "[HTML:encabezado] " + ("<header>" + HtmlReportBody.escape(institutionName) + "</header>");
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[HTML:pie] " + ("<footer>Página " + pageNumber + "</footer>");
    }
}
