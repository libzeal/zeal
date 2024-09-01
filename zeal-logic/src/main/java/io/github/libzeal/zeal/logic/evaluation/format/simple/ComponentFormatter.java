package io.github.libzeal.zeal.logic.evaluation.format.simple;

public interface ComponentFormatter<T> {

    String format(T element, SimpleFormatterContext context);
}
