package com.patrones.u2;

import java.util.List;
import java.util.Locale;

public interface ReportBody {
    String render(List<GradeRecord> records, Locale locale);
}
