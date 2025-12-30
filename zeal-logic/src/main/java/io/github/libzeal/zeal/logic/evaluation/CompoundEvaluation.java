package io.github.libzeal.zeal.logic.evaluation;

import java.util.List;

public interface CompoundEvaluation extends Evaluation {

    List<Evaluation> children();
}
