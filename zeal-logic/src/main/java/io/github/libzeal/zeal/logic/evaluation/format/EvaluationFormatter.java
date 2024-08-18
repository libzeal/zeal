package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;


public interface EvaluationFormatter {

    String format(Rationale rationale, FormatterContext context);
    String format(Cause cause, FormatterContext context);
    String format(Evaluation evaluation, FormatterContext context);
}
