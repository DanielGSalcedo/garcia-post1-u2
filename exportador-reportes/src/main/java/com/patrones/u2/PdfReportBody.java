package com.patrones.u2;

import java.util.List;
import java.util.Locale;

final class PdfReportBody implements ReportBody {
    @Override
    public String render(List<GradeRecord> records, Locale locale) {
        var out = new StringBuilder("[PDF:cuerpo]\n");
        for (GradeRecord row : records) {
            out.append(String.format(locale, " %s | %s | %s | %.1f%n",
                    row.studentId(), row.studentName(), row.courseCode(), row.grade()));
        }
        return out.toString();
    }
}
