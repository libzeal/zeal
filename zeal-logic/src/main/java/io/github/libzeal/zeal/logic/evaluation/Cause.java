package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.DirectCause.Delegated;
import io.github.libzeal.zeal.logic.evaluation.DirectCause.Self;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * The causal information for an evaluation that represents the logic that
 * caused the evaluation result. This information includes the direct cause of
 * the evaluation, the root causes, and the traces that show the chain of
 * evaluations that led to the result of the evaluation.
 *
 * @param evaluation
 *         The evaluation associated with this cause.
 * @param direct
 *         The direct cause of the evaluation.
 */
public record Cause(Evaluation evaluation, DirectCause direct) {

    /**
     * Creates a new cause.
     *
     * @param evaluation
     *         The evaluation associated with this cause.
     * @param direct
     *         The direct cause of the evaluation.
     *
     * @throws NullPointerException
     *         The supplied evaluation or direct cause are {@code null}.
     */
    public Cause {
        requireNonNull(evaluation);
        requireNonNull(direct);
    }

    /**
     * The traces from the original evaluation, through the chain of causation,
     * to the root cause of the evaluation. For example, if evaluation A is
     * caused by B and B is caused by C, the trace would be A -> B -> C.
     * <p>
     * A new trace is created for each path through the causation tree. For
     * example, if the causation tree is [A -> B, B -> C, B -> D], two traces
     * will be included:
     * <ol>
     *     <li>A -> B -> C</li>
     *     <li>A -> B -> D</li>
     * </ol>
     * Likewise, an evaluation with a {@link Self} direct cause will have a
     * single trace with the evaluation itself as the only evaluation in the
     * trace.
     *
     * @return The traces associated with the cause.
     */
    public List<Trace> traces() {
        return switch (direct) {
            case DirectCause.Self() -> List.of(Trace.of(evaluation));
            case final DirectCause.Delegated delegated ->
                    delegated.evaluations()
                            .stream()
                            .flatMap(delegate -> delegate.cause().traces().stream())
                            .map(trace -> trace.prepend(evaluation))
                            .toList();
        };
    }

    /**
     * The root cause of the evaluation. For {@link Self} direct causes, the
     * root cause is the evaluation itself. For {@link Delegated} direct causes,
     * the root cause is determined by following the delegated cause to its
     * terminal cause. For example, if the causation tree looks like A -> B ->
     * C, then C is the root cause.
     * <p>
     * Note that more than one evaluation can be a root cause. For example, if
     * the causation tree is [A -> B, B -> C, B -> D], then the root causes will
     * be [C, D].
     *
     * @return The evaluations that represent the root causes of the evaluation.
     */
    public List<Evaluation> rootCauses() {
        return traces().stream()
                .map(Trace::rootCause)
                .toList();
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
    public record Trace(List<Evaluation> evaluations) {

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
         * and A is prepended, a new trace of A -> B -> C will be returned.
         *
         * @param evaluation
         *         The evaluation to prepend.
         *
         * @return A new trace with the supplied evaluation prepended.
         *
         * @throws NullPointerException
         *         The supplied evaluation is {@code null}.
         */
        Trace prepend(final Evaluation evaluation) {

            final Stream<Evaluation> stream = Stream.concat(Stream.of(evaluation), this.evaluations.stream());
            final List<Evaluation> evaluations = stream.toList();

            return new Trace(evaluations);
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
