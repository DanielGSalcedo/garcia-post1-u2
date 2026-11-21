package com.patrones.u2;

import java.util.List;
import java.util.Locale;

final class ExcelReportBody implements ReportBody {
    @Override
    public String render(List<GradeRecord> records, Locale locale) {
        var out = new StringBuilder("[XLSX:cuerpo]\nCódigo;Estudiante;Curso;Nota\n");
        for (GradeRecord row : records) {
            out.append(String.format(locale, "%s;%s;%s;%.1f%n",
                    quote(row.studentId()), quote(row.studentName()), quote(row.courseCode()), row.grade()));
        }
        return out.toString();
    }

    private static String quote(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
