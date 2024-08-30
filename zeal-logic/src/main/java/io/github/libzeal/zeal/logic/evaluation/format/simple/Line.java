package io.github.libzeal.zeal.logic.evaluation.format.simple;

import static java.util.Objects.requireNonNull;

public class Line {

    private final int indentation;
    private final String content;

    public Line(final int indentation, final String content) {
        this.indentation = indentation;
        this.content = requireNonNull(content);
    }

    public static Line indent(final int indentation) {
        return new Line(indentation, "");
    }

    public static Line keyValue(final String key, final String value, final int indentation, final int keyWidth) {
        return new Line(indentation, keyValueContent(key, value, keyWidth));
    }

    private static String keyValueContent(final String key, final String value, final int keyWidth) {

        final String format = "%-" + keyWidth + "s : %s";

        return String.format(format, key, value);
    }

    private String createIndent(final int level) {

        final StringBuilder indent = new StringBuilder();
        final String format = "%-" + indentation + "s";

        for (int i = 0; i < level; i++) {
            indent.append(String.format(format, " "));
        }

        return indent.toString();
    }

    public String asString(final SimpleFormatterContext context) {
        return createIndent(context.depth()) + content;
    }
}
