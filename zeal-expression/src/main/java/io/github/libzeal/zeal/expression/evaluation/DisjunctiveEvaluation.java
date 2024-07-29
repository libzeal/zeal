package io.github.libzeal.zeal.expression.evaluation;

import java.util.List;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;
import static java.util.Objects.requireNonNull;

/**
 * An evaluated expression that treats its children as a disjunctive set.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class DisjunctiveEvaluation implements Evaluation {

    private final String name;
    private final List<Evaluation> children;

    /**
     * Creates a new disjunctive evaluation.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The child evaluations that this evaluation is composed of.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public DisjunctiveEvaluation(String name, List<Evaluation> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    @Override
    public Result result() {

        if (children.isEmpty()) {
            return PASSED;
        }

        int failed = 0;

        for (Evaluation child : children) {

            if (child.result().equals(PASSED)) {
                return PASSED;
            }
            else if (child.result().equals(FAILED)) {
                failed++;
            }
        }

        if (failed == children.size()) {
            return FAILED;
        }
        else {
            return Result.SKIPPED;
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return new Rationale("All children must pass", actual());
    }

    private String actual() {

        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (Evaluation child : children) {

            switch (child.result()) {
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
        }

        return "Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped;
    }

    @Override
    public List<Evaluation> children() {
        return children;
    }
}
