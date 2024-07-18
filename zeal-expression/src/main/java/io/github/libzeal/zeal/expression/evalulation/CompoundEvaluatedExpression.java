package io.github.libzeal.zeal.expression.evalulation;

import java.util.List;

public class CompoundEvaluatedExpression implements EvaluatedExpression {

    private final String name;
    private final List<EvaluatedExpression> children;

    public CompoundEvaluatedExpression(String name, List<EvaluatedExpression> children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public EvaluationState state() {

        if (children.isEmpty()) {
            return EvaluationState.PASSED;
        }

        int passed = 0;

        for (EvaluatedExpression child: children) {

            switch (child.state()) {
                case FAILED:
                    return EvaluationState.FAILED;
                case PASSED:
                    passed++;
                    break;
            }
        }

        if (passed == children.size()) {
            return EvaluationState.PASSED;
        }
        else {
            return EvaluationState.SKIPPED;
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String expected() {
        return "All children must pass";
    }

    @Override
    public String actual() {

        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (EvaluatedExpression child: children) {

            switch (child.state()) {
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

    @Override
    public final boolean failsNotNullCheck() {
        return children.stream()
                .anyMatch(EvaluatedExpression::failsNotNullCheck);
    }
}
