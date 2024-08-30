package io.github.libzeal.zeal.logic.evaluation.format;

/**
 * Contains predefined {@link Formatter} instances.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public final class Formatters {

    private Formatters() {
    }

    /**
     * Obtains the default formatter used when no other formatter is configured.
     *
     * @return The default formatter.
     */
    public static Formatter defaultFormatter() {

        final ComponentFormatter componentFormatter = new SimpleComponentFormatter();

        return new RootCauseFirstFormatter(componentFormatter);
    }
}
