package com.patrones.u2;

import java.util.List;
import java.util.Locale;

final class HtmlReportBody implements ReportBody {
    @Override
    public String render(List<GradeRecord> records, Locale locale) {
        var out = new StringBuilder("[HTML:cuerpo]\n<table>\n");
        for (GradeRecord row : records) {
            out.append(String.format(locale, "<tr><td>%s</td><td>%s</td><td>%s</td><td>%.1f</td></tr>%n",
                    escape(row.studentId()), escape(row.studentName()), escape(row.courseCode()), row.grade()));
        }
        return out.append("</table>\n").toString();
    }

    static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
