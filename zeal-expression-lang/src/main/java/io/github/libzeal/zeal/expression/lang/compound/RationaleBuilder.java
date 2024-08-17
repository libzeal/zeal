package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

interface RationaleBuilder {

    Rationale build(CompoundEvaluator.Tally tally);
}
