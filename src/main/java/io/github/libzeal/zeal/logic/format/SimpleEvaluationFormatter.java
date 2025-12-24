package io.github.libzeal.zeal.logic.format;

import io.github.libzeal.zeal.logic.evaluation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleEvaluationFormatter implements EvaluationFormatter {

    private static final String INDENT = "    ";

    @Override
    public FormattedEvaluation format(final Evaluation evaluation) {

        final FormatterContext context = FormatterContext.create();
        final Segments formatted = format(evaluation, context);

        return new FormattedEvaluation(formatted.toString());
    }

    private Segments format(final Evaluation evaluation, final FormatterContext context) {
        return switch (evaluation) {
            case EmptyEvaluation e -> formatOther(e, context);
            case TerminalEvaluation e -> formatOther(e, context);
            case NegationEvaluation e -> formatNegation(e, context);
            case CompoundEvaluation e -> formatCompound(e, context);
        };
    }

    private static Segments formatOther(final Evaluation evaluation, final FormatterContext context) {

        final String segment = indent(context) + result(evaluation) + " " + evaluation.name();

        return Segments.of(segment);
    }

    private static Segments formatNegation(final NegationEvaluation e, final FormatterContext context) {

        final Segments negationSegment = formatOther(e, context);
        final Segments childSegment = formatOther(e.evaluation(), context.incrementDepth());

        return Segments.of(negationSegment, childSegment);
    }

    private Segments formatCompound(final CompoundEvaluation e, final FormatterContext context) {

        final Segments root = formatOther(e, context);
        final List<String> childrenStrings = e.children()
            .stream()
            .map(c -> format(c, context.incrementDepth()))
            .map(Segments::segments)
            .flatMap(List::stream)
            .toList();

        return Segments.of(root, new Segments(childrenStrings));
    }

    private static String result(final Evaluation e) {
       return switch (e.result()) {
            case TRUE -> "[/]";
            case FALSE -> "[X]";
            case SKIPPED -> "[ ]";
        };
    }

    private static String indent(final FormatterContext context) {
        return INDENT.repeat(context.depth());
    }

    private record Segments(List<String> segments) {

        public static Segments create() {
            return new Segments(new ArrayList<>());
        }

        public static Segments of(final Segments... segments) {

            final List<String> strings = Stream.of(segments)
                .map(Segments::segments)
                .flatMap(List::stream)
                .toList();

            return new Segments(strings);
        }

        public static Segments of(final String segment) {
            return new Segments(List.of(segment));
        }

        @Override
        public String toString() {
            return segments.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private record FormatterContext(List<String> segments, int depth) {

        public static FormatterContext create() {
            return new FormatterContext(new ArrayList<>(), 0);
        }

        public FormatterContext incrementDepth() {
            return new FormatterContext(segments, depth + 1);
        }
    }
}
