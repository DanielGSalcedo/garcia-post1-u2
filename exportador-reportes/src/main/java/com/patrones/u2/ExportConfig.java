package com.patrones.u2;

import java.util.IllformedLocaleException;
import java.util.Locale;

public final class ExportConfig {
    private final String format;
    private final String outputPath;
    private final String pageSize;
    private final String orientation;
    private final String locale;
    private final String watermarkText;
    private final boolean includeLogo;
    private final boolean compress;
    private final int maxRowsPerPage;

    private ExportConfig(Builder builder) {
        format = builder.format;
        outputPath = builder.outputPath;
        pageSize = builder.pageSize;
        orientation = builder.orientation;
        locale = builder.locale;
        watermarkText = builder.watermarkText;
        includeLogo = builder.includeLogo;
        compress = builder.compress;
        maxRowsPerPage = builder.maxRowsPerPage;
    }

    public String format() { return format; }
    public String outputPath() { return outputPath; }
    public String pageSize() { return pageSize; }
    public String orientation() { return orientation; }
    public String locale() { return locale; }
    public String watermarkText() { return watermarkText; }
    public boolean includeLogo() { return includeLogo; }
    public boolean compress() { return compress; }
    public int maxRowsPerPage() { return maxRowsPerPage; }

    public static final class Builder {
        private final String format;
        private String outputPath;
        private String pageSize = "A4";
        private String orientation = "PORTRAIT";
        private String locale = "es-CO";
        private String watermarkText;
        private boolean includeLogo = true;
        private boolean compress = false;
        private int maxRowsPerPage = 40;

        public Builder(String format) {
            this.format = format;
        }

        public Builder outputPath(String value) {
            outputPath = value;
            return this;
        }

        public Builder pageSize(String value) {
            pageSize = value;
            return this;
        }

        public Builder orientation(String value) {
            orientation = value;
            return this;
        }

        public Builder locale(String value) {
            locale = value;
            return this;
        }

        public Builder watermarkText(String value) {
            watermarkText = value;
            return this;
        }

        public Builder includeLogo(boolean value) {
            includeLogo = value;
            return this;
        }

        public Builder compress(boolean value) {
            compress = value;
            return this;
        }

        public Builder maxRowsPerPage(int value) {
            maxRowsPerPage = value;
            return this;
        }

        public ExportConfig build() {
            if (format == null || format.isBlank()) {
                throw new IllegalArgumentException("El formato es obligatorio");
            }
            if (outputPath != null && outputPath.isBlank()) {
                throw new IllegalStateException("La ruta de salida está vacía");
            }
            if (compress && outputPath == null) {
                throw new IllegalStateException("La compresión requiere una ruta de salida");
            }
            if (maxRowsPerPage <= 0) {
                throw new IllegalStateException("maxRowsPerPage debe ser mayor que cero");
            }
            if (pageSize == null || pageSize.isBlank()) {
                throw new IllegalStateException("El tamaño de página es obligatorio");
            }
            if (!"PORTRAIT".equals(orientation) && !"LANDSCAPE".equals(orientation)) {
                throw new IllegalStateException("La orientación debe ser PORTRAIT o LANDSCAPE");
            }
            try {
                if (locale == null || new Locale.Builder().setLanguageTag(locale).build().getLanguage().isEmpty()) {
                    throw new IllegalArgumentException("Sin idioma");
                }
            } catch (IllegalArgumentException | IllformedLocaleException ex) {
                throw new IllegalStateException("El idioma debe ser una etiqueta BCP 47 válida", ex);
            }
            return new ExportConfig(this);
        }
    }
}
