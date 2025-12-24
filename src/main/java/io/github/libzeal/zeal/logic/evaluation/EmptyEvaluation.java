package io.github.libzeal.zeal.logic.evaluation;

public final class EmptyEvaluation implements Evaluation{

    @Override
    public String name() {
        return "(empty)";
    }

    @Override
    public Result result() {
        return Result.TRUE;
    }
}
