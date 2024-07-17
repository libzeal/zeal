package com.zeal.assertion.exception;

import com.zeal.expression.BooleanResult;
import com.zeal.expression.Explanation;
import com.zeal.expression.BooleanResultFormatter;

public class PreconditionNullPointerException extends NullPointerException {

    private final BooleanResult result;
    private final BooleanResultFormatter formatter;

    public PreconditionNullPointerException(String message, BooleanResult result, BooleanResultFormatter formatter) {
        super(message);
        this.result = result;
        this.formatter = formatter;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(super.toString());
        final String formattedResult = formatter.format(result, findLocation());

        builder.append(formattedResult);

        return builder.toString();
    }

    private String findLocation() {

        StackTraceElement[] stackTrace = getStackTrace();

        if (stackTrace != null && stackTrace.length > 1) {
            return String.valueOf(stackTrace[1]);
        }
        else {
            return null;
        }
    }
}
