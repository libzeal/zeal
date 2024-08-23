package io.github.libzeal.zeal.logic.evaluation.cause;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

@FunctionalInterface
public interface CauseGenerator {

    Cause generate(Evaluation evaluation);

    static CauseGenerator self() {
        return Cause::new;
    }

    static CauseGenerator withUnderlyingCause(final Cause cause) {
        return evaluation -> new Cause(evaluation, cause);
    }
}
