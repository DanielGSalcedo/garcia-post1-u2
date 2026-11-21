package com.patrones.u2;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class ReportFactoryRegistry {
    private static final Map<String, Supplier<ReportFormatFactory>> FACTORIES =
            new ConcurrentHashMap<>();

    private ReportFactoryRegistry() {
    }

    public static void register(String format, Supplier<ReportFormatFactory> provider) {
        String key = normalize(format);
        Objects.requireNonNull(provider, "La fábrica es obligatoria");
        if (FACTORIES.putIfAbsent(key, provider) != null) {
            throw new IllegalArgumentException("Formato ya registrado: " + key);
        }
    }

    public static ReportFormatFactory resolve(String format) {
        String key = normalize(format);
        var provider = FACTORIES.get(key);
        if (provider == null) {
            throw new IllegalArgumentException("Formato no registrado: " + key
                    + ". Disponibles: " + new TreeSet<>(FACTORIES.keySet()));
        }
        return provider.get();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El formato es obligatorio");
        }
        return value.strip().toLowerCase(Locale.ROOT);
    }
}
