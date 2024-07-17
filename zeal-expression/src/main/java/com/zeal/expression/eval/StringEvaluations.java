package com.zeal.expression.eval;

public final class StringEvaluations {
    
    private StringEvaluations() {}

    public static Evaluation<String> isEmpty() {
        return Evaluation.of(s -> s != null && s.isEmpty());
    }

    public static Evaluation<String> isNotEmpty() {
        return Evaluation.of(s -> s != null && !s.isEmpty());
    }

    public static Evaluation<String> isBlank() {
        return Evaluation.of(s -> s != null && s.trim().isEmpty());
    }

    public static Evaluation<String> isNotBlank() {
        return Evaluation.of(s -> s != null && !s.trim().isEmpty());
    }

    public static Evaluation<String> hasLengthOf(long length) {
        return Evaluation.of(s -> s != null && s.length() == length);
    }

    public static Evaluation<String> isLongerThan(long length) {
        return Evaluation.of(s -> s != null && s.length() > length);
    }

    public static Evaluation<String> isShorterThan(long length) {
        return Evaluation.of(s -> s != null && s.length() < length);
    }

    public static Evaluation<String> isLongerThanOrEqualTo(long length) {
        return Evaluation.of(s -> s != null && s.length() >= length);
    }

    public static Evaluation<String> isShorterThanOrEqualTo(long length) {
        return Evaluation.of(s -> s != null && s.length() <= length);
    }

    public static Evaluation<String> includes(char c) {
        return Evaluation.of(s -> s != null && s.indexOf(c) > -1);
    }

    public static Evaluation<String> includes(CharSequence sequence) {
        return Evaluation.of(s -> s != null && s.contains(sequence));
    }

    public static Evaluation<String> excludes(char c) {
        return Evaluation.of(s -> s != null && s.indexOf(c) == -1);
    }

    public static Evaluation<String> excludes(CharSequence c) {
        return Evaluation.of(s -> s != null && !s.contains(c));
    }

    public static Evaluation<String> occurs(char c, long times) {
        return Evaluation.of(s -> s != null && characterCount(s, c) == times);
    }

    private static long characterCount(String s, char c) {
        return s.chars()
                .filter(ch -> ch == c)
                .count();
    }

    public static Evaluation<String> occursLessThan(char c, long times) {
        return Evaluation.of(s -> s != null && characterCount(s, c) < times);
    }

    public static Evaluation<String> occursMoreThan(char c, long times) {
        return Evaluation.of(s -> s != null && characterCount(s, c) > times);
    }

    public static Evaluation<String> occursMoreThanOrEqualTo(char c, long times) {
        return Evaluation.of(s -> s != null && characterCount(s, c) >= times);
    }

    public static Evaluation<String> occursLessThanOrEqualTo(char c, long times) {
        return Evaluation.of(s -> s != null && characterCount(s, c) <= times);
    }

    public static Evaluation<String> startsWith(String prefix) {
        return Evaluation.of(s -> s != null && s.startsWith(prefix));
    }

    public static Evaluation<String> doesNotStartWith(String prefix) {
        return Evaluation.of(s -> s != null && !s.startsWith(prefix));
    }

    public static Evaluation<String> endsWith(String prefix) {
        return Evaluation.of(s -> s != null && s.endsWith(prefix));
    }

    public static Evaluation<String> doesNotEndWith(String prefix) {
        return Evaluation.of(s -> s != null && !s.endsWith(prefix));
    }

    public static Evaluation<String> matches(String regex) {
        return Evaluation.of(s -> s != null && s.matches(regex));
    }

    public static Evaluation<String> isCaseInsensitiveEqualTo(String other) {
        return Evaluation.of(s -> s != null && s.equalsIgnoreCase(other));
    }

    public static Evaluation<String> hasAtIndex(char needle, int index) {
        return Evaluation.of(s -> s != null && s.indexOf(needle) == index);
    }

    public static Evaluation<String> hasAtIndex(String needle, int index) {
        return Evaluation.of(s -> s != null && s.indexOf(needle) == index);
    }

    public static Evaluation<String> hasAtLastIndex(char needle, int index) {
        return Evaluation.of(s -> s != null && s.lastIndexOf(needle) == index);
    }

    public static Evaluation<String> hasAtLastIndex(String needle, int index) {
        return Evaluation.of(s -> s != null && s.lastIndexOf(needle) == index);
    }
}
