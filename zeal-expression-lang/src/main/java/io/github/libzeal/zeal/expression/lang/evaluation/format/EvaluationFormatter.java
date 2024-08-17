package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.*;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;


public interface EvaluationFormatter {

    String format(Rationale rationale, FormatterContext context);
    String format(RootCause rootCause, FormatterContext context);
    String format(Evaluation evaluation, FormatterContext context);
}
