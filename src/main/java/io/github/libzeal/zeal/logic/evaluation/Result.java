package io.github.libzeal.zeal.logic.evaluation;

public enum Result {
    TRUE,
    FALSE,
    SKIPPED;

    public static Result from(final boolean b) {

        if (b) {
            return Result.TRUE;
        }
        else {
            return Result.FALSE;
        }
    }

    public Result negate() {
        return switch(this) {
            case TRUE -> FALSE;
            case FALSE -> TRUE;
            case SKIPPED -> SKIPPED;
        };
    }

    public boolean isTrue() {
        return equals(TRUE);
    }

    public boolean isFalse() {
        return equals(FALSE);
    }

    public boolean isSkipped() {
        return equals(SKIPPED);
    }
}
