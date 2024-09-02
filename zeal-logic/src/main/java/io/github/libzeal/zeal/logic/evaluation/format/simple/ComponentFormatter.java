package io.github.libzeal.zeal.logic.evaluation.format.simple;

/**
 * A formatted used to format a specific component.
 *
 * @param <T>
 *     The type of the component.
 */
interface ComponentFormatter<T> {

    /**
     * Formats the supplied component.
     *
     * @param component
     *     The component to format.
     * @param context
     *     The context to use when formatting the component.
     *
     * @return The formatted string for the supplied component.
     */
    String format(T component, SimpleFormatterContext context);
}
