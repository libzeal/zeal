package io.github.libzeal.zeal.logic.evaluation.format;

public class Formatters {

    private Formatters() {
    }

    public static Formatter defaultFormatter() {

        final EvaluationFormatter evaluationFormatter = new SimpleEvaluationFormatter();

        return new RootCauseFirstFormatter(evaluationFormatter);
    }
}
