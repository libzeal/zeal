package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.SubjectExpression;
import io.github.libzeal.zeal.expression.evalulation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ObjectExpression<T, B extends ObjectExpression<T, B>>
        implements SubjectExpression<T> {

    private final T subject;
    private final CompoundEvaluation<T> children;

    protected ObjectExpression(T subject) {
        this(subject, "Object(" + objectToString(subject) + ") evaluation");
    }

    protected ObjectExpression(T subject, String name) {
        this.subject = subject;
        this.children = new CompoundEvaluation<>(name);
    }

    @Override
    public final T subject() {
        return subject;
    }

    @Override
    public final EvaluatedExpression evaluate() {
        return children.evaluate(subject, false);
    }

    @SuppressWarnings("unchecked")
    protected final B appendEvaluation(Evaluation<T> evaluation) {

        children.append(evaluation);

        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B isNotNull() {
        children.prepend(NotNullEvaluatedExpression::new);
        return (B) this;
    }

    public B isNull() {
        return appendEvaluation(
                TerminalEvaluation.of(
                        "isNull",
                        () -> "(null)",
                        ObjectExpression::objectToString,
                        Objects::isNull
                )
        );
    }

    public B isType(Class<?> type) {
        return appendEvaluation(
                TerminalEvaluation.of(
                        "isType(" + type.getName() + ")",
                        type::toString,
                        o -> o.getClass().toString(),
                        o -> o.getClass().equals(type)
                )
        );
    }

    private static String objectToString(Object o) {
        return o == null ? "(null)" : o.toString();
    }

    private static class NotNullEvaluatedExpression<T> implements EvaluatedExpression {

        private final EvaluationState state;
        private final String actual;

        public NotNullEvaluatedExpression(T subject, boolean skip) {
            this.state = computeState(subject, skip);
            this.actual = objectToString(subject);
        }

        private static <T> EvaluationState computeState(T subject, boolean skip) {

            if (skip) {
                return EvaluationState.SKIPPED;
            }

            if (subject == null) {
                return EvaluationState.FAILED;
            }
            else {
                return EvaluationState.PASSED;
            }
        }

        @Override
        public EvaluationState state() {
            return state;
        }

        @Override
        public String name() {
            return "isNotNull";
        }

        @Override
        public String expected() {
            return "not (null)";
        }

        @Override
        public String actual() {
            return actual;
        }

        @Override
        public List<EvaluatedExpression> children() {
            return Collections.emptyList();
        }

        @Override
        public boolean failsNotNullCheck() {
            return state.equals(EvaluationState.FAILED);
        }
    }
}
