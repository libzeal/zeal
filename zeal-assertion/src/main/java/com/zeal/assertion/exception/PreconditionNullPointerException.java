package com.zeal.assertion.exception;

import com.zeal.expression.Explanation;
import com.zeal.expression.ExplanationFormatter;

public class PreconditionNullPointerException extends NullPointerException {

    private final Explanation explanation;
    private final ExplanationFormatter formatter;

    public PreconditionNullPointerException(String message, Explanation explanation, ExplanationFormatter formatter) {
        super(message);
        this.explanation = explanation;
        this.formatter = formatter;
    }

    @Override
    public String toString() {
        return new ExceptionExplanationStringBuilder(explanation, formatter)
                .toString(super.toString(), getStackTrace());
    }
}
