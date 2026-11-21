package com.patrones.u2;

import java.util.List;
import java.util.Locale;

public final class ReportExportService {
    public String export(String format, List<GradeRecord> records, String institutionName) {
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(format);
        var frame = factory.createHeaderFooter();
        return frame.renderHeader(institutionName) + "\n"
                + factory.createBody().render(records, Locale.forLanguageTag("es-CO"))
                + frame.renderFooter(1);
    }
}
