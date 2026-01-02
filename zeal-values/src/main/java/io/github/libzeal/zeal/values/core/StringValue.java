package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableField;
import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.CommonRationale;

/**
 * An expression used to evaluate {@link String} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class StringValue extends BaseObjectValue<String, StringValue> {

    // TODO: Add cachedExpression(...) for StringValue methods

    private static final String EQUALS_OPERATOR = ":=";
    private static final String NOT_EQUALS_OPERATOR = "!=";
    private static final String LENGTH_EQUAL_PREFIX = "length " + EQUALS_OPERATOR + " ";
    private static final String OCCURS = "occurs";
    private static final String INDEX_OF_PREFIX = "indexOf";
    private static final String LAST_INDEX_OF_PREFIX = "lastIndexOf";
    private static final String OCCURRENCES_PREFIX = "occurrences";

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringValue(final String subject) {
        super(subject, "String value");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is empty (has a length of {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    public StringValue isEmpty() {
        return append(
            expression(String::isEmpty)
                .name("isEmpty")
                .expected("true")
                .actual(isPredicatedPassed())
        );
    }

    private static ComputableField<String> isPredicatedPassed() {
        return (o, passed) -> passed ? "true" : "false";
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not empty (has a length greater than
     * {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    public StringValue isNotEmpty() {
        return append(
            expression(s -> !s.isEmpty())
                .name("isNotEmpty")
                .expected("true")
                .actual(isPredicatedPassed())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is blank (empty or contains only whitespace).
     *
     * @return This expression (fluent interface).
     */
    public StringValue isBlank() {
        return append(
            expression(s -> s.trim().isEmpty())
                .name("isBlank")
                .expected("true")
                .actual(isPredicatedPassed())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not blank (empty or contains only whitespace).
     *
     * @return This expression (fluent interface).
     */
    public StringValue isNotBlank() {
        return append(
            expression(s -> !s.trim().isEmpty())
                .name("isNotBlank")
                .expected("true")
                .actual(isPredicatedPassed())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is equal to the supplied length.
     *
     * @param length
     *     The desired length of the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue hasLengthOf(final long length) {
        return append(
            expression(s -> s.length() == length)
                .name("hasLengthOf[" + length + "]")
                .expected(LENGTH_EQUAL_PREFIX + length)
                .actual((s, passed) -> LENGTH_EQUAL_PREFIX + s.length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is longer than the supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue isLongerThan(final long length) {
        return append(
            expression(s -> s.length() > length)
                .name("isLongerThan[" + length + "]")
                .expected("length > " + length)
                .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is longer than or equal to the
     * supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue isLongerThanOrEqualTo(long length) {
        return append(
            expression(s -> s.length() >= length)
                .name("isLongerThanOrEqualTo[" + length + "]")
                .expected("length >= " + length)
                .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is shorter than the supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue isShorterThan(long length) {
        return append(
            expression(s -> s.length() < length)
                .name("isShorterThan[" + length + "]")
                .expected("length < " + length)
                .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is shorter than or equal to the
     * supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue isShorterThanOrEqualTo(long length) {
        return append(
            expression(s -> s.length() <= length)
                .name("isShorterThanOrEqualTo[" + length + "]")
                .expected("length <= " + length)
                .actual((value, passed) -> LENGTH_EQUAL_PREFIX + value.length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains the supplied character.
     *
     * @param c
     *     The character to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue includes(final char c) {
        return append(
            expression(s -> s.indexOf(c) != -1)
                .name(CommonRationale.includes(c))
                .expected(CommonRationale.includes(c))
                .actual((s, passed) -> passed ? CommonRationale.includes(c) : CommonRationale.excludes(c))
                .hint((s, passed) -> needleInHaystackHint(s, c))
        );
    }

    static String needleInHaystackHint(String s, char c) {

        final int index = s.indexOf(c);

        return CommonRationale.needleInHaystackHint("String", index, c);
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue includes(final CharSequence sequence) {
        return append(
            expression(s -> s.contains(sequence))
                .name(CommonRationale.includes(sequence))
                .expected(CommonRationale.includes(sequence))
                .actual((s, passed) -> passed ? CommonRationale.includes(sequence) : CommonRationale.excludes(sequence))
                .hint((s, passed) -> needleInHaystackHint(s, sequence))
        );
    }

    static String needleInHaystackHint(String s, CharSequence sequence) {

        final int index = s.indexOf(sequence.toString());

        return CommonRationale.needleInHaystackHint("String", index, sequence);
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not contain the supplied character.
     *
     * @param c
     *     The character to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue excludes(final char c) {
        return append(
            expression(s -> s.indexOf(c) == -1)
                .name(CommonRationale.excludes(c))
                .expected(CommonRationale.excludes(c))
                .actual((s, passed) -> passed ? CommonRationale.excludes(c) : CommonRationale.includes(c))
                .hint((s, passed) -> needleInHaystackHint(s, c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not contain the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue excludes(final CharSequence sequence) {
        return append(
            expression(s -> !s.contains(sequence))
                .name(CommonRationale.excludes(sequence))
                .expected(CommonRationale.excludes(sequence))
                .actual((s, passed) -> passed ? CommonRationale.excludes(sequence) : CommonRationale.includes(sequence))
                .hint((s, passed) -> needleInHaystackHint(s, sequence))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is
     * equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected number of occurrences.
     *
     * @return This expression (fluent interface).
     */
    public StringValue occursName(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) == times)
                .name(occursName(c, times, EQUALS_OPERATOR))
                .expected(OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + times)
                .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
        );
    }

    private static long characterCount(final String s, final char c) {
        return s.chars()
            .filter(ch -> ch == c)
            .count();
    }

    private static String occursName(final char c, final long times, final String operator) {
        return OCCURS + "[" + c + "] " + operator + " " + times;
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is
     * greater than the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected minimum number of occurrences (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue occursMoreThan(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) > times)
                .name(occursName(c, times, ">"))
                .expected(OCCURRENCES_PREFIX + " > " + times)
                .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is
     * greater than or equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected minimum number of occurrences (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue occursMoreThanOrEqualTo(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) >= times)
                .name(occursName(c, times, ">="))
                .expected(OCCURRENCES_PREFIX + " >= " + times)
                .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is less
     * than the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected maximum number of occurrences (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue occursLessThan(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) < times)
                .name(occursName(c, times, "<"))
                .expected(OCCURRENCES_PREFIX + " < " + times)
                .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject contains the supplied character is less
     * than or equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected maximum number of occurrences (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue occursLessThanOrEqualTo(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) <= times)
                .name(occursName(c, times, "<="))
                .expected(OCCURRENCES_PREFIX + " <= " + times)
                .actual((value, passed) -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(value, c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject starts with the supplied prefix.
     *
     * @param prefix
     *     The desired prefix.
     *
     * @return This expression (fluent interface).
     */
    public StringValue startsWith(final String prefix) {
        return append(
            expression(s -> s.startsWith(prefix))
                .name("startsWith[" + prefix + "]")
                .expected("startsWith[" + prefix + "]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not start with the supplied prefix.
     *
     * @param prefix
     *     The prefix to ensure the subject does not have.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotStartWith(final String prefix) {
        return append(
            expression(s -> !s.startsWith(prefix))
                .name("doesNotStartWith[" + prefix + "]")
                .expected("not[startsWith[" + prefix + "]]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject ends with the supplied suffix.
     *
     * @param suffix
     *     The desired suffix.
     *
     * @return This expression (fluent interface).
     */
    public StringValue endsWith(final String suffix) {
        return append(
            expression(s -> s.endsWith(suffix))
                .name("endsWith[" + suffix + "]")
                .expected("endsWith[" + suffix + "]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not end with the supplied suffix.
     *
     * @param suffix
     *     The suffix to ensure the subject does not have.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotEndWith(final String suffix) {
        return append(
            expression(s -> !s.endsWith(suffix))
                .name("doesNotEndWith[" + suffix + "]")
                .expected("not[endsWith[" + suffix + "]]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the supplied regular expression matches the subject.
     *
     * @param regex
     *     The regular expression to match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue matches(final String regex) {
        return append(
            expression(s -> s.matches(regex))
                .name("matches[" + regex + "]")
                .expected("matches[" + regex + "]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the supplied regular expression does not match the subject.
     *
     * @param regex
     *     The regular expression to not match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotMatch(final String regex) {
        return append(
            expression(s -> !s.matches(regex))
                .name("doesNotMatch[" + regex + "]")
                .expected("not[matches[" + regex + "]]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the supplied argument equals the subject, ignoring case.
     *
     * @param other
     *     The string to match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue isCaseInsensitiveEqualTo(final String other) {
        return append(
            expression(s -> s.equalsIgnoreCase(other))
                .name("caseInsensitiveEqualTo[" + other + "]")
                .expected(other)
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected first index.
     *
     * @return This expression (fluent interface).
     */
    public StringValue hasAtIndex(final char needle, final int index) {
        return append(
            expression(s -> s.indexOf(needle) == index)
                .name(INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
                .expected(index)
                .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected first index.
     *
     * @return This expression (fluent interface).
     */
    public StringValue hasAtIndex(final String needle, final int index) {
        return append(
            expression(s -> s.indexOf(needle) == index)
                .name(INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
                .expected(index)
                .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotHaveAtIndex(final char needle, final int index) {
        return append(
            expression(s -> s.indexOf(needle) != index)
                .name(INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
                .expected((s, passed) -> "not[" + index + "]")
                .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotHaveAtIndex(final String needle, final int index) {
        return append(
            expression(s -> s.indexOf(needle) != index)
                .name(INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
                .expected((s, passed) -> "not[" + index + "]")
                .actual((s, passed) -> String.valueOf(s.indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected last index.
     *
     * @return This expression (fluent interface).
     */
    public StringValue hasAtLastIndex(final char needle, final int index) {
        return append(
            expression(s -> s.lastIndexOf(needle) == index)
                .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
                .expected(index)
                .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The expected last index.
     *
     * @return This expression (fluent interface).
     */
    public StringValue hasAtLastIndex(final String needle, final int index) {
        return append(
            expression(s -> s.lastIndexOf(needle) == index)
                .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
                .expected(index)
                .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotHaveAtLastIndex(final char needle, final int index) {
        return append(
            expression(s -> s.lastIndexOf(needle) != index)
                .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
                .expected((s, passed) -> "not[" + index + "]")
                .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *     The needle to look for in the subject.
     * @param index
     *     The index expected not to match.
     *
     * @return This expression (fluent interface).
     */
    public StringValue doesNotHaveAtLastIndex(final String needle, final int index) {
        return append(
            expression(s -> s.lastIndexOf(needle) != index)
                .name(LAST_INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
                .expected((s, passed) -> "not[" + index + "]")
                .actual((s, passed) -> String.valueOf(s.lastIndexOf(needle)))
        );
    }
}