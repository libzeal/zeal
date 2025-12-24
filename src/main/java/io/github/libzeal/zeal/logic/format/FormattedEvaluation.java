package io.github.libzeal.zeal.logic.format;

public record FormattedEvaluation(String formattedEvaluation) {

    @Override
    public String toString() {
        return formattedEvaluation();
    }
}
