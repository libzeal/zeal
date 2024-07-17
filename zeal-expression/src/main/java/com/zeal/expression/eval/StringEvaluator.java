package com.zeal.expression.eval;

import com.zeal.expression.eval.base.BaseObjectEvaluator;

public class StringEvaluator extends BaseObjectEvaluator<String, StringEvaluator> {

    public StringEvaluator(String value) {
        super(value);
    }

    public StringEvaluator isEmpty() {
        return satisfies(StringEvaluations.isEmpty());
    }

    public StringEvaluator isNotEmpty() {
        return satisfies(StringEvaluations.isNotEmpty());
    }

    public StringEvaluator isBlank() {
        return satisfies(StringEvaluations.isBlank());
    }

    public StringEvaluator isNotBlank() {
        return satisfies(StringEvaluations.isNotBlank());
    }

    public StringEvaluator hasLengthOf(long length) {
        return satisfies(StringEvaluations.hasLengthOf(length));
    }

    public StringEvaluator isLongerThan(long length) {
        return satisfies(StringEvaluations.isLongerThan(length));
    }

    public StringEvaluator isShorterThan(long length) {
        return satisfies(StringEvaluations.isShorterThan(length));
    }

    public StringEvaluator isLongerThanOrEqualTo(long length) {
        return satisfies(StringEvaluations.isLongerThanOrEqualTo(length));
    }

    public StringEvaluator isShorterThanOrEqualTo(long length) {
        return satisfies(StringEvaluations.isShorterThanOrEqualTo(length));
    }

    public StringEvaluator includes(char c) {
        return satisfies(StringEvaluations.includes(c));
    }

    public StringEvaluator includes(CharSequence sequence) {
        return satisfies(StringEvaluations.includes(sequence));
    }

    public StringEvaluator excludes(char c) {
        return satisfies(StringEvaluations.excludes(c));
    }

    public StringEvaluator excludes(CharSequence c) {
        return satisfies(StringEvaluations.excludes(c));
    }

    public StringEvaluator occurs(char c, long times) {
        return satisfies(StringEvaluations.occurs(c, times));
    }

    public StringEvaluator occursLessThan(char c, long times) {
        return satisfies(StringEvaluations.occursLessThan(c, times));
    }

    public StringEvaluator occursMoreThan(char c, long times) {
        return satisfies(StringEvaluations.occursMoreThan(c, times));
    }

    public StringEvaluator occursMoreThanOrEqualTo(char c, long times) {
        return satisfies(StringEvaluations.occursMoreThanOrEqualTo(c, times));
    }

    public StringEvaluator occursLessThanOrEqualTo(char c, long times) {
        return satisfies(StringEvaluations.occursLessThanOrEqualTo(c, times));
    }

    public StringEvaluator startsWith(String prefix) {
        return satisfies(StringEvaluations.startsWith(prefix));
    }

    public StringEvaluator doesNotStartWith(String prefix) {
        return satisfies(StringEvaluations.doesNotStartWith(prefix));
    }

    public StringEvaluator endsWith(String prefix) {
        return satisfies(StringEvaluations.endsWith(prefix));
    }

    public StringEvaluator doesNotEndWith(String prefix) {
        return satisfies(StringEvaluations.doesNotEndWith(prefix));
    }

    public StringEvaluator matches(String regex) {
        return satisfies(StringEvaluations.matches(regex));
    }

    public StringEvaluator isCaseInsensitiveEqualTo(String other) {
        return satisfies(StringEvaluations.isCaseInsensitiveEqualTo(other));
    }

    public StringEvaluator hasAtIndex(char needle, int index) {
        return satisfies(StringEvaluations.hasAtIndex(needle, index));
    }

    public StringEvaluator hasAtIndex(String needle, int index) {
        return satisfies(StringEvaluations.hasAtIndex(needle, index));
    }

    public StringEvaluator hasAtLastIndex(char needle, int index) {
        return satisfies(StringEvaluations.hasAtLastIndex(needle, index));
    }

    public StringEvaluator hasAtLastIndex(String needle, int index) {
        return satisfies(StringEvaluations.hasAtLastIndex(needle, index));
    }
}
