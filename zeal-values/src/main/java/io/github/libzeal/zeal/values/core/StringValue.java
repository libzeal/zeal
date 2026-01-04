package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableField;
import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.StandardRationales;
import io.github.libzeal.zeal.values.api.sequence.OrderedSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.OrderedSequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.OrderedSequenceValueBuilder.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.SequenceValueBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * A value used to evaluate {@link String} instances.
 *
 * @author Justin Albano
 */
public class StringValue extends BaseObjectValue<String, StringValue>
    implements OrderedSequenceValue<Character, String, StringValue>,
        RepeatableSequenceValue<Character, String, StringValue> {

    // TODO: Add cachedExpression(...) for StringValue methods

    private static final String EQUALS_OPERATOR = ":=";
    private static final String NOT_EQUALS_OPERATOR = "!=";
    private static final String LENGTH_EQUAL_PREFIX = "length " + EQUALS_OPERATOR + " ";
    private static final String INCLUDES = "includes";
    private static final String INDEX_OF_PREFIX = "indexOf";
    private static final String LAST_INDEX_OF_PREFIX = "lastIndexOf";
    private static final String OCCURRENCES_PREFIX = "occurrences";

    private final StringOperations operations;

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringValue(final String subject) {
        super(subject, "String value");
        this.operations = new StringOperations();
    }

    /**
     * Adds a predicate to the expression that checks if the subject is empty
     * (has a length of {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    @Override
    public StringValue isEmpty() {
        return append(
            SequenceValueBuilder.isEmpty(operations)
        );
    }

    private static ComputableField<String> isPredicatedPassed() {
        return context -> context.ifPassedOrElse("true", "false");
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not
     * empty (has a length greater than
     * {@code 0}).
     *
     * @return This expression (fluent interface).
     *
     * @see String#isEmpty()
     */
    @Override
    public StringValue isNotEmpty() {
        return append(
            SequenceValueBuilder.isNotEmpty(operations)
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is blank
     * (empty or contains only whitespace).
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
     * Adds a predicate to the expression that checks if the subject is not
     * blank (empty or contains only whitespace).
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
     * Adds a predicate to the expression that checks if the length of the
     * subject is equal to the supplied length.
     *
     * @param length
     *     The desired length of the subject.
     *
     * @return This expression (fluent interface).
     */
    @Override
    public StringValue hasLengthOf(final int length) {
        return append(
            expression(s -> s.length() == length)
                .name("hasLengthOf[" + length + "]")
                .expected(LENGTH_EQUAL_PREFIX + length)
                .actual(context -> LENGTH_EQUAL_PREFIX + context.subject().length())
        );
    }

    @Override
    public StringValue doesNotHaveLengthOf(final int length) {
        return append(
            expression(s -> s.length() != length)
                .name("doesNotHaveLengthOf[" + length + "]")
                .expected(LENGTH_EQUAL_PREFIX + length)
                .actual(context -> LENGTH_EQUAL_PREFIX + context.subject().length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the
     * subject is longer than the supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    @Override
    public StringValue isLongerThan(final int length) {
        return append(
            expression(s -> s.length() > length)
                .name("isLongerThan[" + length + "]")
                .expected("length > " + length)
                .actual(context -> LENGTH_EQUAL_PREFIX + context.subject().length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the
     * subject is longer than or equal to the
     * supplied length.
     *
     * @param length
     *     The desired minimum length of the subject (inclusive).
     *
     * @return This expression (fluent interface).
     */
    @Override
    public StringValue isLongerThanOrEqualTo(int length) {
        return append(
            expression(s -> s.length() >= length)
                .name("isLongerThanOrEqualTo[" + length + "]")
                .expected("length >= " + length)
                .actual(context -> LENGTH_EQUAL_PREFIX + context.subject().length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the
     * subject is shorter than the supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (exclusive).
     *
     * @return This expression (fluent interface).
     */
    @Override
    public StringValue isShorterThan(int length) {
        return append(
            expression(s -> s.length() < length)
                .name("isShorterThan[" + length + "]")
                .expected("length < " + length)
                .actual(context -> LENGTH_EQUAL_PREFIX + context.subject().length())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the length of the
     * subject is shorter than or equal to the
     * supplied length.
     *
     * @param length
     *     The desired maximum length of the subject (inclusive).
     *
     * @return This expression (fluent interface).
     */
    @Override
    public StringValue isShorterThanOrEqualTo(int length) {
        return append(
            expression(s -> s.length() <= length)
                .name("isShorterThanOrEqualTo[" + length + "]")
                .expected("length <= " + length)
                .actual(context -> LENGTH_EQUAL_PREFIX + context.subject().length())
        );
    }

    @Override
    public StringValue includes(final Character c) {
        return append(
            expression(s -> s.indexOf(c) != -1)
                .name(StandardRationales.includes(c))
                .expected(StandardRationales.includes(c))
                .actual(context -> context.ifPassedOrElse(StandardRationales.includes(c), StandardRationales.excludes(c)))
                .hint(context -> needleInHaystackHint(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains
     * the supplied character.
     *
     * @param c
     *     The character to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */

    public StringValue includes(final char c) {
        return append(
            expression(s -> s.indexOf(c) != -1)
                .name(StandardRationales.includes(c))
                .expected(StandardRationales.includes(c))
                .actual(context -> context.ifPassedOrElse(StandardRationales.includes(c), StandardRationales.excludes(c)))
                .hint(context -> needleInHaystackHint(context.subject(), c))
        );
    }

    static String needleInHaystackHint(String s, char c) {

        final int index = s.indexOf(c);

        return StandardRationales.needleInHaystackHint(index, c);
    }

    /**
     * Adds a predicate to the expression that checks if the subject contains
     * the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is included in the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue includes(final CharSequence sequence) {
        return append(
            expression(s -> s.contains(sequence))
                .name(StandardRationales.includes(sequence))
                .expected(StandardRationales.includes(sequence))
                .actual(context -> context.ifPassedOrElse(StandardRationales.includes(sequence), StandardRationales.excludes(sequence)))
                .hint(context -> needleInHaystackHint(context.subject(), sequence))
        );
    }

    @Override
    public StringValue includesAll(final Collection<Character> desired) {
        return append(
            SequenceValueBuilder.includesAll(desired, operations)
        );
    }

    @Override
    public StringValue includesAllOf(final Character... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public StringValue includesAny(final Collection<Character> desired) {
        return append(
            SequenceValueBuilder.includesAny(desired, operations)
        );
    }

    @Override
    public StringValue includesAnyOf(final Character... desired) {
        return includesAny(Arrays.asList(desired));
    }

    static String needleInHaystackHint(String s, CharSequence sequence) {

        final int index = s.indexOf(sequence.toString());

        return StandardRationales.needleInHaystackHint(index, sequence);
    }

    @Override
    public StringValue excludes(final Character c) {
        return append(
            expression(s -> s.indexOf(c) == -1)
                .name(StandardRationales.excludes(c))
                .expected(StandardRationales.excludes(c))
                .actual(context -> context.ifPassedOrElse(StandardRationales.excludes(c), StandardRationales.includes(c)))
                .hint(context -> needleInHaystackHint(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not
     * contain the supplied character.
     *
     * @param c
     *     The character to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue excludes(final char c) {
        return append(
            expression(s -> s.indexOf(c) == -1)
                .name(StandardRationales.excludes(c))
                .expected(StandardRationales.excludes(c))
                .actual(context -> context.ifPassedOrElse(StandardRationales.excludes(c), StandardRationales.includes(c)))
                .hint(context -> needleInHaystackHint(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not
     * contain the supplied sequence.
     *
     * @param sequence
     *     The sequence to ensure is excluded from the subject.
     *
     * @return This expression (fluent interface).
     */
    public StringValue excludes(final CharSequence sequence) {
        return append(
            expression(s -> !s.contains(sequence))
                .name(StandardRationales.excludes(sequence))
                .expected(StandardRationales.excludes(sequence))
                .actual(context -> context.ifPassedOrElse(StandardRationales.excludes(sequence), StandardRationales.includes(sequence)))
                .hint(context -> needleInHaystackHint(context.subject(), sequence))
        );
    }

    @Override
    public StringValue excludesAll(final Collection<Character> desired) {
        return append(
            SequenceValueBuilder.excludesAll(desired, operations)
        );
    }

    @Override
    public StringValue excludesAllOf(final Character... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public StringValue excludesAny(final Collection<Character> desired) {
        return append(
            SequenceValueBuilder.excludesAny(desired, operations)
        );
    }

    @Override
    public StringValue excludesAnyOf(final Character... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public StringValue includesExactly(final Character c, final long times) {
        return append(
            expression(s -> characterCount(s, c) == times)
                .name(occursName(c, times, EQUALS_OPERATOR))
                .expected(OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    public StringValue includesOnce(final char c) {
        return includesExactly(c, 1);
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject
     * contains the supplied character is
     * equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected number of occurrences.
     *
     * @return This expression (fluent interface).
     */
    public StringValue includesExactly(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) == times)
                .name(occursName(c, times, EQUALS_OPERATOR))
                .expected(OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    private static long characterCount(final String s, final char c) {
        return s.chars()
            .filter(ch -> ch == c)
            .count();
    }

    private static String occursName(final char c, final long times, final String operator) {
        return INCLUDES + "[" + c + "] " + operator + " " + times;
    }

    @Override
    public StringValue includesMoreThan(final Character c, final long times) {
        return append(
            expression(s -> characterCount(s, c) > times)
                .name(occursName(c, times, ">"))
                .expected(OCCURRENCES_PREFIX + " > " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject
     * contains the supplied character is
     * greater than the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected minimum number of occurrences (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue includesMoreThan(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) > times)
                .name(occursName(c, times, ">"))
                .expected(OCCURRENCES_PREFIX + " > " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    @Override
    public StringValue includesMoreThanOrEqualTo(final Character c, final long times) {
        return append(
            expression(s -> characterCount(s, c) >= times)
                .name(occursName(c, times, ">="))
                .expected(OCCURRENCES_PREFIX + " >= " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject
     * contains the supplied character is
     * greater than or equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected minimum number of occurrences (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue includesMoreThanOrEqualTo(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) >= times)
                .name(occursName(c, times, ">="))
                .expected(OCCURRENCES_PREFIX + " >= " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    @Override
    public StringValue includesLessThan(final Character c, final long times) {
        return append(
            expression(s -> characterCount(s, c) < times)
                .name(occursName(c, times, "<"))
                .expected(OCCURRENCES_PREFIX + " < " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject
     * contains the supplied character is less
     * than the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected maximum number of occurrences (exclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue includesLessThan(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) < times)
                .name(occursName(c, times, "<"))
                .expected(OCCURRENCES_PREFIX + " < " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    @Override
    public StringValue includesLessThanOrEqualTo(final Character c, final long times) {
        return append(
            expression(s -> characterCount(s, c) <= times)
                .name(occursName(c, times, "<="))
                .expected(OCCURRENCES_PREFIX + " <= " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    /**
     * Adds a predicate to the expression that checks if number of times subject
     * contains the supplied character is less
     * than or equal to the supplied argument.
     *
     * @param c
     *     The character to match.
     * @param times
     *     The expected maximum number of occurrences (inclusive).
     *
     * @return This expression (fluent interface).
     */
    public StringValue includesLessThanOrEqualTo(final char c, final long times) {
        return append(
            expression(s -> characterCount(s, c) <= times)
                .name(occursName(c, times, "<="))
                .expected(OCCURRENCES_PREFIX + " <= " + times)
                .actual(context -> OCCURRENCES_PREFIX + " " + EQUALS_OPERATOR + " " + characterCount(context.subject(), c))
        );
    }

    @Override
    public StringValue startsWith(final Character prefix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(0) == prefix)
                .name("startsWith[" + prefix + "]")
                .expected("startsWith[" + prefix + "]")
        );
    }

    public StringValue startsWith(final char prefix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(0) == prefix)
                .name("startsWith[" + prefix + "]")
                .expected("startsWith[" + prefix + "]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject starts with
     * the supplied prefix.
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

    @Override
    public StringValue doesNotStartWith(final Character prefix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(0) != prefix)
                .name("doesNotStartWith[" + prefix + "]")
                .expected("not[startsWith[" + prefix + "]]")
        );
    }

    public StringValue doesNotStartWith(final char prefix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(0) != prefix)
                .name("doesNotStartWith[" + prefix + "]")
                .expected("not[startsWith[" + prefix + "]]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not
     * start with the supplied prefix.
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

    @Override
    public StringValue endsWith(final Character suffix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(s.length() - 1) == suffix)
                .name("endsWith[" + suffix + "]")
                .expected("endsWith[" + suffix + "]")
        );
    }

    public StringValue endsWith(final char suffix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(s.length() - 1) == suffix)
                .name("endsWith[" + suffix + "]")
                .expected("endsWith[" + suffix + "]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject ends with
     * the supplied suffix.
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

    @Override
    public StringValue doesNotEndWith(final Character suffix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(s.length() - 1) != suffix)
                .name("doesNotEndWith[" + suffix + "]")
                .expected("not[endsWith[" + suffix + "]]")
        );
    }

    public StringValue doesNotEndWith(final char suffix) {
        return append(
            expression(s -> !s.isEmpty() && s.charAt(s.length() - 1) != suffix)
                .name("doesNotEndWith[" + suffix + "]")
                .expected("not[endsWith[" + suffix + "]]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not
     * end with the supplied suffix.
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
     * Adds a predicate to the expression that checks if the supplied regular
     * expression matches the subject.
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
     * Adds a predicate to the expression that checks if the supplied regular
     * expression does not match the subject.
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
     * Adds a predicate to the expression that checks if the supplied argument
     * equals the subject, ignoring case.
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

    @Override
    public StringValue hasAtIndex(final Character needle, final int index) {
        return append(
            expression(s -> s.indexOf(needle) == index)
                .name(INDEX_OF_PREFIX + "[" + needle + "] " + EQUALS_OPERATOR + " " + index)
                .expected(index)
                .actual(context -> String.valueOf(context.subject().indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the
     * supplied needle in the subject matches
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
                .actual(context -> String.valueOf(context.subject().indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the
     * supplied needle in the subject matches
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
                .actual(context -> String.valueOf(context.subject().indexOf(needle)))
        );
    }

    @Override
    public StringValue doesNotHaveAtIndex(final Character needle, final int index) {
        return append(
            expression(s -> s.indexOf(needle) != index)
                .name(INDEX_OF_PREFIX + "[" + needle + "] " + NOT_EQUALS_OPERATOR + " " + index)
                .expected(context -> "not[" + index + "]")
                .actual(context -> String.valueOf(context.subject().indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the
     * supplied needle in the subject does not
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
                .expected(context -> "not[" + index + "]")
                .actual(context -> String.valueOf(context.subject().indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the first index of the
     * supplied needle in the subject does not
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
                .expected(context -> "not[" + index + "]")
                .actual(context -> String.valueOf(context.subject().indexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the
     * supplied needle in the subject matches
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
                .actual(context -> String.valueOf(context.subject().lastIndexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the
     * supplied needle in the subject matches
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
                .actual(context -> String.valueOf(context.subject().lastIndexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the
     * supplied needle in the subject does not
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
                .expected(context -> "not[" + index + "]")
                .actual(context -> String.valueOf(context.subject().lastIndexOf(needle)))
        );
    }

    /**
     * Adds a predicate to the expression that checks if the last index of the
     * supplied needle in the subject does not
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
                .expected(context -> "not[" + index + "]")
                .actual(context -> String.valueOf(context.subject().lastIndexOf(needle)))
        );
    }

    private static final class StringOperations implements RepeatableSequenceOperations<Character, String>,
        OrderedSequenceOperations<Character, String> {

        @Override
        public Character lastElement(final String haystack) {
            return haystack.charAt(haystack.length() - 1);
        }

        @Override
        public Character atIndex(final String haystack, final int index) {

            if (index < haystack.length()) {
                return haystack.charAt(index);
            }
            else {
                return null;
            }
        }

        @Override
        public int indexOf(final String haystack, final Character needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.indexOf(needle);
            }
        }

        @Override
        public int occurrences(final String haystack, final Character needle) {

            if (needle == null) {
                return 0;
            }

            int count = 0;

            for (int i = 0; i < haystack.length(); i++) {
                if (haystack.charAt(i) == needle) {
                    count++;
                }
            }

            return count;
        }

        @Override
        public List<Character> findAllIn(final String haystack, final Collection<Character> needles) {
            return needles.stream()
                .filter(c -> haystack.indexOf(c) != -1)
                .collect(toList());
        }

        @Override
        public int size(final String haystack) {
            return haystack.length();
        }

        @Override
        public boolean isEmpty(final String haystack) {
            return haystack.isEmpty();
        }

        @Override
        public boolean includes(final String haystack, final Character needle) {
            return haystack.indexOf(needle) >= 0;
        }
    }
}