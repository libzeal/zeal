package com.zeal.expression.subject.values;

import com.zeal.expression.subject.sdk.BaseObjectEvaluator;

public class StringEvaluator extends BaseObjectEvaluator<String, StringEvaluator> {

    public StringEvaluator(String value) {
        super(value);
    }

    public StringEvaluator isEmpty() {
        return appendToChain(s -> s != null && s.isEmpty());
    }

    public StringEvaluator isNotEmpty() {
        return appendToChain(s -> s != null && !s.isEmpty());
    }

    public StringEvaluator isBlank() {
        return appendToChain(s -> s != null && s.trim().isEmpty());
    }

    public StringEvaluator isNotBlank() {
        return appendToChain(s -> s != null && !s.trim().isEmpty());
    }

    public StringEvaluator hasLengthOf(long length) {
        return appendToChain(s -> s != null && s.length() == length);
    }

    public StringEvaluator isLongerThan(long length) {
        return appendToChain(s -> s != null && s.length() > length);
    }

    public StringEvaluator isShorterThan(long length) {
        return appendToChain(s -> s != null && s.length() < length);
    }

    public StringEvaluator isLongerThanOrEqualsTo(long length) {
        return appendToChain(s -> s != null && s.length() >= length);
    }

    public StringEvaluator isShorterThanOrEqualTo(long length) {
        return appendToChain(s -> s != null && s.length() <= length);
    }

    public StringEvaluator includes(char c) {
        return appendToChain(s -> s != null && s.indexOf(c) > -1);
    }

    public StringEvaluator includes(CharSequence sequence) {
        return appendToChain(s -> s != null && s.contains(sequence));
    }

    public StringEvaluator excludes(char c) {
        return appendToChain(s -> s != null && s.indexOf(c) == -1);
    }

    public StringEvaluator occurs(char c, long times) {
        return appendToChain(s -> s != null && characterCount(s, c) == times);
    }

    private long characterCount(String s, char c) {
        return s.chars()
                .filter(ch -> ch == c)
                .count();
    }

    public StringEvaluator occursLessThan(char c, long times) {
        return appendToChain(s -> s != null && characterCount(s, c) < times);
    }

    public StringEvaluator occursMoreThan(char c, long times) {
        return appendToChain(s -> s != null && characterCount(s, c) > times);
    }

    public StringEvaluator occursMoreThanOrEqualTo(char c, long times) {
        return appendToChain(s -> s != null && characterCount(s, c) >= times);
    }

    public StringEvaluator occursLessThanOrEqualTo(char c, long times) {
        return appendToChain(s -> s != null && characterCount(s, c) <= times);
    }

    public StringEvaluator startsWith(String prefix) {
        return appendToChain(s -> s != null && s.startsWith(prefix));
    }

    public StringEvaluator doesNotStartWith(String prefix) {
        return appendToChain(s -> s != null && !s.startsWith(prefix));
    }

    public StringEvaluator endsWith(String prefix) {
        return appendToChain(s -> s != null && s.endsWith(prefix));
    }

    public StringEvaluator doesNotEndWith(String prefix) {
        return appendToChain(s -> s != null && !s.endsWith(prefix));
    }

    public StringEvaluator matches(String regex) {
        return appendToChain(s -> s != null && s.matches(regex));
    }
}
