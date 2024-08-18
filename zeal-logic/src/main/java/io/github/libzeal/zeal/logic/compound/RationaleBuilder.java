package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.rationale.Rationale;

interface RationaleBuilder {

    Rationale build(CompoundEvaluator.Tally tally);
}
