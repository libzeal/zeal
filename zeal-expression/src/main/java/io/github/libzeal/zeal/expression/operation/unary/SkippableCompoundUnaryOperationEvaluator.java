package io.github.libzeal.zeal.expression.operation.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.evaluation.Result;
import io.github.libzeal.zeal.expression.operation.EvaluatedOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * An evaluator that allows for operations to be skipped.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
class SkippableCompoundUnaryOperationEvaluator<T> {

    private final String name;
    private final SkipTest skipTest;
    private final ResultProvider<T> resultProvider;
    private final RationaleProvider<T> rationaleProvider;

    /**
     * Creates a new evaluator.
     *
     * @param name
     *     The name of the resulting evaluation.
     * @param skipTest
     *     The test used to decide if an operation is skipped.
     * @param resultProvider
     *     A provider that computes the result of the resulting evaluation.
     * @param rationaleProvider
     *     A provider that computes the rationale for the resulting evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public SkippableCompoundUnaryOperationEvaluator(final String name, final SkipTest skipTest,
                                                    final ResultProvider<T> resultProvider, final RationaleProvider<T> rationaleProvider) {
        this.name = requireNonNull(name);
        this.skipTest = requireNonNull(skipTest);
        this.resultProvider = requireNonNull(resultProvider);
        this.rationaleProvider = requireNonNull(rationaleProvider);
    }

    /**
     * Evaluates the supplied operations against the supplied subject.
     *
     * @param operations
     *     The operations to evaluate.
     * @param subject
     *     The subject of the evaluation.
     *
     * @return The evaluation that results from evaluating the supplied operations against the supplied subject.
     *
     * @throws NullPointerException
     *     The supplied operations are {@code null} (the subject can be {@code null}).
     */
    public Evaluation evaluate(final List<UnaryOperation<T>> operations, final T subject) {

        requireNonNull(operations, "Null operations cannot be evaluated");

        final int total = operations.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (UnaryOperation<T> operation : operations) {

            if (skipTest.shouldSkip(passed, failed, skipped, total)) {
                evaluated.add(operation.evaluateAsSkipped(subject));
            }
            else {
                final Evaluation evaluation = operation.evaluate(subject);
                final Result result = evaluation.result();

                switch (result) {
                    case PASSED:
                        passed++;
                        break;
                    case FAILED:
                        failed++;
                        break;
                    case SKIPPED:
                        skipped++;
                        break;
                }

                evaluated.add(evaluation);
            }
        }

        final EvaluationContext<T> context = new EvaluationContext<>(passed, failed, skipped, total, subject);

        final Result compountResult = resultProvider.compute(context);
        final Supplier<Rationale> rationale = rationaleProvider.compute(context);

        return new EvaluatedOperation(name, compountResult, rationale, evaluated);
    }

    /**
     * A test used to determine if an operations should be skipped.
     */
    @FunctionalInterface
    public interface SkipTest {
        boolean shouldSkip(final int passed, final int failed, final int skipped, final int total);
    }

    /**
     * A context for an evaluation.
     *
     * @param <T>
     *     The type of the subject.
     */
    public static class EvaluationContext<T> {

        private final int passed;
        private final int failed;
        private final int skipped;
        private final int total;
        private final T subject;

        private EvaluationContext(final int passed, final int failed, final int skipped, final int total,
                                  final T subject) {
            this.passed = passed;
            this.failed = failed;
            this.skipped = skipped;
            this.total = total;
            this.subject = subject;
        }

        public int passed() {
            return passed;
        }

        public int failed() {
            return failed;
        }

        public int skipped() {
            return skipped;
        }

        public int total() {
            return total;
        }

        public T subject() {
            return subject;
        }
    }

    /**
     * A provider for the result of an evaluation based on a supplied context.
     *
     * @param <T>
     *     The type of the subject.
     */
    @FunctionalInterface
    public interface ResultProvider<T> {

        /**
         * Computes the result of an evaluation based on the supplied context.
         *
         * @param context
         *     The context of the evaluation.
         *
         * @return THe result of an evaluation.
         */
        Result compute(final EvaluationContext<T> context);
    }

    /**
     * A provider for the rationale for an evaluation based on a supplied context.
     *
     * @param <T>
     *     The type of the subject.
     */
    @FunctionalInterface
    public interface RationaleProvider<T> {

        /**
         * Computes the rationale for an evaluation based on the supplied context.
         *
         * @param context
         *     The context of the evaluation.
         *
         * @return THe rationale for an evaluation.
         */
        Supplier<Rationale> compute(final EvaluationContext<T> context);
    }
}
