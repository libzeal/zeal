package io.github.libzeal.zeal.expression.evaluation;

import java.util.List;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;

public class CompoundEvaluatedExpression implements EvaluatedExpression {

    private final String name;
    private final List<EvaluatedExpression> children;

    public CompoundEvaluatedExpression(String name, List<EvaluatedExpression> children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public Result result() {

        if (children.isEmpty()) {
            return PASSED;
        }

        int passed = 0;

        for (EvaluatedExpression child: children) {

            if (child.result().equals(PASSED)) {
                passed++;
            }
            else if (child.result().equals(FAILED)){
                return FAILED;
            }
        }

        if (passed == children.size()) {
            return PASSED;
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

        for (EvaluatedExpression child: children) {

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
    public List<EvaluatedExpression> children() {
        return children;
    }
}
