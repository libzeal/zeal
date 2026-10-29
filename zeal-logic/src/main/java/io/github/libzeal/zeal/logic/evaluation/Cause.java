package io.github.libzeal.zeal.logic.evaluation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The evaluation that determines the result of an evaluation. For example, if
 * compound evaluation is false, the cause would be the child that caused the
 * compound evaluation to be false.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public sealed interface Cause {

    /**
     * Creates a self-referential cause. This is used for evaluations whose
     * cause was determined by the evaluation only and is common with terminal
     * evaluations.
     *
     * @return A self-referential cause.
     */
    static Cause self() {
        return new Self();
    }

    /**
     * Creates a delegated cause, where one evaluation references another
     * evaluation as its cause. This common with compound evaluations and
     * skipped expressions.
     *
     * @param evaluation
     *         The referenced evaluation (the delegated cause).
     *
     * @return A deleted cause that references the supplied evaluation.
     *
     * @throws NullPointerException
     *         The supplied evaluation is {@code null}.
     */
    static Delegated of(final Evaluation evaluation) {
        return new Delegated(List.of(evaluation));
    }

    /**
     * Creates a delegated cause, where one evaluation references the supplied
     * evaluations as its causes. This common with compound evaluations and
     * skipped expressions.
     *
     * @param evaluations
     *         The referenced evaluations (the delegated causes).
     *
     * @return A deleted cause that references the supplied evaluations.
     *
     * @throws NullPointerException
     *         The supplied evaluations is {@code null} or any of the
     *         evaluations in the supplied list are {@code null}.
     */
    static Delegated of(final List<Evaluation> evaluations) {
        return new Delegated(evaluations);
    }

    /**
     * A self-referential cause, where an evaluation is determined only by
     * itself. This is common with terminal evaluations.
     */
    record Self() implements Cause {
    }

    /**
     * A cause that references one or more delegated evaluations.
     *
     * @param evaluations
     *         The referenced evaluations.
     */
    record Delegated(List<Evaluation> evaluations) implements Cause {

        /**
         * Creates a new delegated cause.
         *
         * @param evaluations
         *         The reference evaluations (delegated causes).
         *
         * @throws NullPointerException
         *         The supplied evaluations is {@code null} or any of the
         *         evaluations in the supplied list are {@code null}.
         * @throws IllegalArgumentException
         *         The supplied evaluations list is empty.
         */
        public Delegated {
            if (evaluations.isEmpty()) {
                throw new IllegalArgumentException("A delegated cause requires at least one evaluation");
            }

            evaluations = List.copyOf(evaluations);
        }

        /**
         * The traces from the original evaluation, through the chain of
         * causation, to the root cause of the evaluation. For example, if
         * evaluation A is caused by B and B is caused by C, the trace would be
         * A -> B -> C.
         * <p>
         * A new trace is created for each path through the causation tree. For
         * example, if the causation tree is [A -> B, B -> C, B -> D], two
         * traces will be included:
         * <ol>
         *     <li>A -> B -> C</li>
         *     <li>A -> B -> D</li>
         * </ol>
         *
         * @return The traces associated with the cause.
         */
        public List<Trace> traces() {
            return evaluations.stream()
                    .flatMap(evaluation -> evaluation.causeTraces().stream())
                    .toList();
        }

        /**
         * The root cause of the evaluation. The root cause is determined by
         * following the delegated cause to its terminal cause. For example, if
         * the causation tree looks like A -> B -> C, then C is the root cause.
         * <p>
         * Note that more than one evaluation can be a root cause. For example,
         * if the causation tree is [A -> B, B -> C, B -> D], then the root
         * causes will be [C, D].
         *
         * @return The
         */
        public List<Evaluation> rootCauses() {
            return traces().stream()
                    .map(Trace::rootCause)
                    .toList();
        }
    }

    /**
     * A trace is a single path through the causation tree. For example, if
     * evaluation A is caused by B and B is caused by C, the trace would be A ->
     * B -> C.
     * <p>
     * A new trace is created for each path through the causation tree. For
     * example, if the causation tree is [A -> B, B -> C, B -> D], two traces
     * will be included:
     * <ol>
     *     <li>A -> B -> C</li>
     *     <li>A -> B -> D</li>
     * </ol>
     *
     * @param evaluations
     *         The ordered list of evaluations that make up the trace. For
     *         example, if the evaluations [A, B, C] are supplied, the trace
     *         will be A -> B -> C.
     *
     * @author Justin Albano
     * @since 0.5.0
     */
    record Trace(List<Evaluation> evaluations) {

        /**
         * Creates a new trace.
         *
         * @param evaluations
         *         The ordered list of evaluations that make up the trace.
         *
         * @throws NullPointerException
         *         The list of evaluations is {@code null} or any of the
         *         evaluations in the list are {@code null}.
         * @throws IllegalArgumentException
         *         The supplied evaluation list is empty.
         */
        public Trace {
            if (evaluations.isEmpty()) {
                throw new IllegalArgumentException("A cause trace requires at least one evaluation");
            }

            evaluations = List.copyOf(evaluations);
        }

        /**
         * Creates a trace of a single evaluation. Common when constructing
         * traces of root causes.
         *
         * @param rootCause
         *         The single evaluation in the trace.
         *
         * @return A trace consisting of the single supplied evaluation.
         *
         * @throws NullPointerException
         *         The supplied evaluation is {@code null}.
         */
        static Trace of(final Evaluation rootCause) {
            return new Trace(List.of(rootCause));
        }

        /**
         * Creates a new trace with the supplied evaluation prepended to the
         * beginning of this trace. For example, if the current trace is B -> C
         * and A is appended, a new trace of A -> B -> C will be returned.
         *
         * @param evaluation
         *         The evaluation to append.
         *
         * @return A new trace with the supplied evaluation prepended.
         *
         * @throws NullPointerException
         *         The supplied evaluation is {@code null}.
         */
        Trace prepend(final Evaluation evaluation) {
            return new Trace(Stream.concat(Stream.of(evaluation), evaluations.stream()).toList());
        }

        /**
         * Obtains the evaluation that is the origin of this trace (the first
         * evaluation in the trace).
         *
         * @return The origin of the trace.
         */
        public Evaluation evaluation() {
            return evaluations.getFirst();
        }

        /**
         * Obtains the root cause of the trace (the last evaluation in the
         * trace).
         *
         * @return The root cause of the trace.
         */
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
