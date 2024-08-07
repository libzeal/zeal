package io.github.libzeal.zeal.expression.types.core.unary;

/**
 * An expression used to evaluate {@link String} instances.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class StringUnaryExpression extends ObjectUnaryExpression<String, StringUnaryExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringUnaryExpression(String subject) {
        super(subject, "String evaluation");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is empty (has a length of {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    public StringUnaryExpression isEmpty() {
        return newPredicate(String::isEmpty)
            .name("isEmpty")
            .expectedValue("true")
            .actualBooleanValue(String::isEmpty)
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not empty (has a length greater than
     * {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    public StringUnaryExpression isNotEmpty() {
        return newPredicate(s -> !s.isEmpty())
            .name("isNotEmpty")
            .expectedValue("true")
            .actualBooleanValue(s -> !s.isEmpty())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is blank (empty or contains only whitespace).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isBlank() {
        return newPredicate(s -> s.trim().isEmpty())
            .name("isBlank")
            .expectedValue("true")
            .actualBooleanValue(s -> s.trim().isEmpty())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not blank (empty or contains only whitespace).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isNotBlank() {
        return newPredicate(s -> !s.trim().isEmpty())
            .name("isNotBlank")
            .expectedValue("true")
            .actualBooleanValue(s -> !s.trim().isEmpty())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is equal to the supplied length.
     *
     * @param length
     *     The desired length of the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression hasLengthOf(final long length) {
        return newPredicate(s -> s.length() == length)
            .name("hasLengthOf[" + length + "]")
            .expectedValue("length := " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is longer than the supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isLongerThan(final long length) {
        return newPredicate(s -> s.length() > length)
            .name("isLongerThan[" + length + "]")
            .expectedValue("length > " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
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
    public StringUnaryExpression isLongerThanOrEqualTo(long length) {
        return newPredicate(s -> s.length() >= length)
            .name("isLongerThanOrEqualTo[" + length + "]")
            .expectedValue("length >= " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the length of the subject is shorter than the supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isShorterThan(long length) {
        return newPredicate(s -> s.length() < length)
            .name("isShorterThan[" + length + "]")
            .expectedValue("length < " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
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
    public StringUnaryExpression isShorterThanOrEqualTo(long length) {
        return newPredicate(s -> s.length() <= length)
            .name("isShorterThanOrEqualTo[" + length + "]")
            .expectedValue("length <= " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains the supplied character.
     *
     * @param c
     *     The character to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression includes(final char c) {
        return newPredicate(s -> s.indexOf(c) != -1)
            .name("includes[" + c + "]")
            .expectedValue("includes[" + c + "]")
            .actualValue(s -> needleInHaystackActualValue(s, c))
            .hint(s -> needleInHaystackHint(s, c))
            .append();
    }

    private static String needleInHaystackActualValue(String s, char c) {
        return s.indexOf(c) != -1 ? "includes[" + c + "]" : "excludes[" + c + "]";
    }

    private static String needleInHaystackHint(String s, char c) {

        int index = s.indexOf(c);

        if (index != -1) {
            return "'" + c + "' found at index " + index;
        }
        else {
            return "String does not include '" + c + "'";
        }
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression includes(final CharSequence sequence) {
        return newPredicate(s -> s.contains(sequence))
            .name("includes[" + sequence + "]")
            .expectedValue("includes[" + sequence + "]")
            .actualValue(s -> needleInHaystackActualValue(s, sequence))
            .hint(s -> needleInHaystackHint(s, sequence))
            .append();
    }

    private static String needleInHaystackActualValue(String s, CharSequence sequence) {
        return s.contains(sequence) ? "includes[" + sequence + "]" : "excludes[" + sequence + "]";
    }

    private static String needleInHaystackHint(String s, CharSequence sequence) {

        int index = s.indexOf(sequence.toString());

        if (index != -1) {
            return "\"" + sequence + "\" found at index " + index;
        }
        else {
            return "String does not include \"" + sequence + "\"";
        }
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not contain the supplied character.
     *
     * @param c
     *     The character to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression excludes(final char c) {
        return newPredicate(s -> s.indexOf(c) == -1)
            .name("excludes[" + c + "]")
            .expectedValue("excludes[" + c + "]")
            .actualValue(s -> needleInHaystackActualValue(s, c))
            .hint(s -> needleInHaystackHint(s, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not contain the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression excludes(final CharSequence sequence) {
        return newPredicate(s -> !s.contains(sequence))
            .name("excludes[" + sequence + "]")
            .expectedValue("excludes[" + sequence + "]")
            .actualValue(s -> needleInHaystackActualValue(s, sequence))
            .hint(s -> needleInHaystackHint(s, sequence))
            .append();
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
    public StringUnaryExpression occurs(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) == times)
            .name("occurs[" + c + "] := " + times)
            .expectedValue("occurrences := " + times)
            .actualValue(value -> "occurrences := " + characterCount(value, c))
            .append();
    }

    private static long characterCount(final String s, final char c) {
        return s.chars()
            .filter(ch -> ch == c)
            .count();
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
    public StringUnaryExpression occursMoreThan(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) > times)
            .name("occurs[" + c + "] > " + times)
            .expectedValue("occurrences > " + times)
            .actualValue(value -> "occurrences := " + characterCount(value, c))
            .append();
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
    public StringUnaryExpression occursMoreThanOrEqualTo(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) >= times)
            .name("occurs[" + c + "] >= " + times)
            .expectedValue("occurrences >= " + times)
            .actualValue(value -> "occurrences := " + characterCount(value, c))
            .append();
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
    public StringUnaryExpression occursLessThan(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) < times)
            .name("occurs[" + c + "] < " + times)
            .expectedValue("occurrences < " + times)
            .actualValue(value -> "occurrences := " + characterCount(value, c))
            .append();
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
    public StringUnaryExpression occursLessThanOrEqualTo(final char c, final long times) {
        return newPredicate(s -> characterCount(s, c) <= times)
            .name("occurs[" + c + "] <= " + times)
            .expectedValue("occurrences <= " + times)
            .actualValue(value -> "occurrences := " + characterCount(value, c))
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject starts with the supplied prefix.
     *
     * @param prefix
     *     The desired prefix.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression startsWith(final String prefix) {
        return newPredicate(s -> s.startsWith(prefix))
            .name("startsWith[" + prefix + "]")
            .expectedValue("startsWith[" + prefix + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not start with the supplied prefix.
     *
     * @param prefix
     *     The prefix to ensure the subject does not have.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotStartWith(final String prefix) {
        return newPredicate(s -> !s.startsWith(prefix))
            .name("doesNotStartWith[" + prefix + "]")
            .expectedValue("not[startsWith[" + prefix + "]]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject ends with the supplied suffix.
     *
     * @param suffix
     *     The desired suffix.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression endsWith(final String suffix) {
        return newPredicate(s -> s.endsWith(suffix))
            .name("endsWith[" + suffix + "]")
            .expectedValue("endsWith[" + suffix + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not end with the supplied suffix.
     *
     * @param suffix
     *     The suffix to ensure the subject does not have.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotEndWith(final String suffix) {
        return newPredicate(s -> !s.endsWith(suffix))
            .name("doesNotEndWith[" + suffix + "]")
            .expectedValue("not[endsWith[" + suffix + "]]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied regular expression matches the subject.
     *
     * @param regex
     *     The regular expression to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression matches(final String regex) {
        return newPredicate(s -> s.matches(regex))
            .name("matches[" + regex + "]")
            .expectedValue("matches[" + regex + "]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied regular expression does not match the subject.
     *
     * @param regex
     *     The regular expression to not match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression doesNotMatch(final String regex) {
        return newPredicate(s -> !s.matches(regex))
            .name("doesNotMatch[" + regex + "]")
            .expectedValue("not[matches[" + regex + "]]")
            .append();
    }

    /**
     * Adds a predicate to the expression that checks if the supplied argument equals the subject, ignoring case.
     *
     * @param other
     *     The string to match.
     *
     * @return This expression (fluent interface).
     */
    public StringUnaryExpression isCaseInsensitiveEqualTo(final String other) {
        return newPredicate(s -> s.equalsIgnoreCase(other))
            .name("caseInsensitiveEqualTo[" + other + "]")
            .expectedValue(other)
            .append();
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
    public StringUnaryExpression hasAtIndex(final char needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) == index)
            .name("indexOf[" + needle + "] := " + index)
            .expectedIntValue(index)
            .actualIntValue(subject -> subject.indexOf(needle))
            .append();
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
    public StringUnaryExpression hasAtIndex(final String needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) == index)
            .name("indexOf[" + needle + "] := " + index)
            .expectedIntValue(index)
            .actualIntValue(subject -> subject.indexOf(needle))
            .append();
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
    public StringUnaryExpression doesNotHaveAtIndex(final char needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) != index)
            .name("indexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + index + "]")
            .actualIntValue(subject -> subject.indexOf(needle))
            .append();
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
    public StringUnaryExpression doesNotHaveAtIndex(final String needle, final int index) {
        return newPredicate(s -> s.indexOf(needle) != index)
            .name("indexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + index + "]")
            .actualIntValue(subject -> subject.indexOf(needle))
            .append();
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
    public StringUnaryExpression hasAtLastIndex(final char needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) == index)
            .name("lastIndexOf[" + needle + "] := " + index)
            .expectedIntValue(index)
            .actualIntValue(subject -> subject.lastIndexOf(needle))
            .append();
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
    public StringUnaryExpression hasAtLastIndex(final String needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) == index)
            .name("lastIndexOf[" + needle + "] := " + index)
            .expectedIntValue(index)
            .actualIntValue(subject -> subject.lastIndexOf(needle))
            .append();
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
    public StringUnaryExpression doesNotHaveAtLastIndex(final char needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) != index)
            .name("lastIndexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + index + "]")
            .actualIntValue(subject -> subject.lastIndexOf(needle))
            .append();
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
    public StringUnaryExpression doesNotHaveAtLastIndex(final String needle, final int index) {
        return newPredicate(s -> s.lastIndexOf(needle) != index)
            .name("lastIndexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + index + "]")
            .actualIntValue(subject -> subject.lastIndexOf(needle))
            .append();
    }
}