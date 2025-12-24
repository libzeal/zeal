package io.github.libzeal.zeal.logic.evaluation;

import java.util.List;

public record CompoundEvaluation(String name, Result result, List<Evaluation>children)
    implements Evaluation {
}
