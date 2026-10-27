package io.github.libzeal.zeal.logic.evaluation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public sealed interface Cause {

    static Cause self() {
        return new Self();
    }

    static Delegated of(final Evaluation evaluation) {
        return new Delegated(List.of(evaluation));
    }

    static Delegated of(final List<Evaluation> evaluations) {
        return new Delegated(evaluations);
    }

    record Self() implements Cause {
    }

    record Delegated(List<Evaluation> evaluations) implements Cause {

        public Delegated {
            if (evaluations.isEmpty()) {
                throw new IllegalArgumentException("A delegated cause requires at least one evaluation");
            }

            evaluations = List.copyOf(evaluations);
        }

        public List<Trace> traces() {
            return evaluations.stream()
                .flatMap(evaluation -> evaluation.causeTraces().stream())
                .toList();
        }

        public List<Evaluation> rootCauses() {
            return traces().stream()
                .map(Trace::rootCause)
                .toList();
        }
    }

    record Trace(List<Evaluation> evaluations) {

        public Trace {
            if (evaluations.isEmpty()) {
                throw new IllegalArgumentException("A cause trace requires at least one evaluation");
            }

            evaluations = List.copyOf(evaluations);
        }

        static Trace of(final Evaluation rootCause) {
            return new Trace(List.of(rootCause));
        }

        Trace prepend(final Evaluation evaluation) {
            return new Trace(Stream.concat(Stream.of(evaluation), evaluations.stream()).toList());
        }

        public Evaluation evaluation() {
            return evaluations.getFirst();
        }

        public Evaluation rootCause() {
            return evaluations.getLast();
        }

        @Override
        public String toString() {
            return evaluations.stream()
                .map(Evaluation::name)
                .collect(Collectors.joining(" -> "));
        }
    }
}
