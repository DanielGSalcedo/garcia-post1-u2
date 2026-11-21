package com.patrones.u2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

public final class ReportExportService {
    public String export(String format, List<GradeRecord> records, String institutionName) {
        return export(new ExportConfig.Builder(format).build(), records, institutionName);
    }

    public String export(ExportConfig config, List<GradeRecord> records, String institutionName) {
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(config.format());
        ReportBody body = factory.createBody();
        ReportHeaderFooter frame = factory.createHeaderFooter();
        Locale locale = Locale.forLanguageTag(config.locale());
        String watermark = config.watermarkText() == null ? "ninguna" : config.watermarkText();
        var out = new StringBuilder(String.format(
                "[config] tamaño=%s orientación=%s idioma=%s marca=%s logo=%s compresión=%s filas=%d%n",
                config.pageSize(), config.orientation(), config.locale(),
                watermark, config.includeLogo(), config.compress(), config.maxRowsPerPage()));

        int page = 1;
        int start = 0;
        do {
            int end = start + Math.min(config.maxRowsPerPage(), records.size() - start);
            out.append(frame.renderHeader(institutionName)).append('\n');
            out.append(body.render(records.subList(start, end), locale));
            out.append(frame.renderFooter(page++)).append('\n');
            start = end;
        } while (start < records.size());

        String result = out.toString();
        if (config.outputPath() != null) {
            save(config, result);
        }
        return result;
    }

    private static void save(ExportConfig config, String result) {
        Path path = Path.of(config.outputPath());
        try {
            if (config.compress()) {
                try (var stream = new GZIPOutputStream(Files.newOutputStream(path))) {
                    stream.write(result.getBytes(StandardCharsets.UTF_8));
                }
            } else {
                Files.writeString(path, result, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException("No se pudo guardar el reporte en " + path, ex);
        }
    }
}
