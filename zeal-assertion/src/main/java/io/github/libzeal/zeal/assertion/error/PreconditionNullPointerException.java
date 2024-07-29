package io.github.libzeal.zeal.assertion.error;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;

public class PreconditionNullPointerException extends NullPointerException {

    private final Evaluation eval;
    private final String message;

    public PreconditionNullPointerException(Evaluation eval, String message) {
        super(message);
        this.eval = eval;
        this.message = message;
    }

    @Override
    public String getMessage() {

        final ExceptionMessageGenerator exceptionMessageGenerator = new ExceptionMessageGenerator();
        final String evalMessage = exceptionMessageGenerator.generate(eval, message, getStackTrace());

        return evalMessage + "\n\nStack Trace: " + getClass().getName() + ": " + super.getMessage();
    }
}
