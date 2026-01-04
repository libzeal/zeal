package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.sequence.*;
import io.github.libzeal.zeal.values.api.sequence.OrderedSequenceValueBuilder.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;

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

    private static final String EQUALS_OPERATOR = "=";
    private static final String NOT_EQUALS_OPERATOR = "!=";
    private static final String INDEX_OF_PREFIX = "indexOf";
    private static final String LAST_INDEX_OF_PREFIX = "lastIndexOf";

    private final StringOperations operations;
    private final StringOnStringOperations charSequenceOperations = new StringOnStringOperations();

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

    @Override
    public StringValue isEmpty() {
        return append(
            SequenceValueBuilder.isEmpty(operations)
        );
    }

    @Override
    public StringValue isNotEmpty() {
        return append(
            SequenceValueBuilder.isNotEmpty(operations)
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
                .actual(context -> context.ifPassedOrElse("true", "false"))
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
                .actual(context -> context.ifPassedOrElse("true", "false"))
        );
    }

    @Override
    public StringValue hasLengthOf(final int length) {
        return append(
            SequenceValueBuilder.hasLengthOf(length, operations)
        );
    }

    @Override
    public StringValue doesNotHaveLengthOf(final int length) {
        return append(
            SequenceValueBuilder.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public StringValue isShorterThan(final int length) {
        return append(
            SequenceValueBuilder.isShorterThan(length, operations)
        );
    }

    @Override
    public StringValue isShorterThanOrEqualTo(final int length) {
        return append(
            SequenceValueBuilder.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public StringValue isLongerThan(final int length) {
        return append(
            SequenceValueBuilder.isLongerThan(length, operations)
        );
    }

    @Override
    public StringValue isLongerThanOrEqualTo(final int length) {
        return append(
            SequenceValueBuilder.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public StringValue includes(final Character desired) {
        return append(
            OrderedSequenceValueBuilder.includes(desired, operations)
        );
    }

    public StringValue includes(final CharSequence desired) {
        return append(
            OrderedSequenceValueBuilder.includes(desired, charSequenceOperations)
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

    @Override
    public StringValue includesExactly(final Character desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesExactly(desired, times, operations)
        );
    }

    @Override
    public StringValue includesMoreThan(final Character desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public StringValue includesMoreThanOrEqualTo(final Character desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public StringValue includesLessThan(final Character desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public StringValue includesLessThanOrEqualTo(final Character desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public StringValue excludes(final Character desired) {
        return append(
            OrderedSequenceValueBuilder.excludes(desired, operations)
        );
    }

    public StringValue excludes(final CharSequence desired) {
        return append(
            OrderedSequenceValueBuilder.excludes(desired, charSequenceOperations)
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
    public StringValue hasAtIndex(final Character desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.hasAtIndex(desired, index, operations)
        );
    }

    public StringValue hasAtIndex(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.hasAtIndex(desired, index, charSequenceOperations)
        );
    }

    @Override
    public StringValue doesNotHaveAtIndex(final Character desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    public StringValue doesNotHaveAtIndex(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.doesNotHaveAtIndex(desired, index, charSequenceOperations)
        );
    }

    @Override
    public StringValue startsWith(final Character desired) {
        return append(
            OrderedSequenceValueBuilder.startsWith(desired, operations)
        );
    }

    public StringValue startsWith(final CharSequence desired) {
        return append(
            OrderedSequenceValueBuilder.startsWith(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue doesNotStartWith(final Character desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotStartWith(desired, operations)
        );
    }

    public StringValue doesNotStartWith(final CharSequence desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotStartWith(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue endsWith(final Character desired) {
        return append(
            OrderedSequenceValueBuilder.endsWith(desired, operations)
        );
    }

    public StringValue endsWith(final CharSequence desired) {
        return append(
            OrderedSequenceValueBuilder.endsWith(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue doesNotEndWith(final Character desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotEndWith(desired, operations)
        );
    }

    public StringValue doesNotEndWith(final CharSequence desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotEndWith(desired, charSequenceOperations)
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

    protected static final class StringOperations implements RepeatableSequenceOperations<Character, String>,
        OrderedSequenceOperations<Character, String> {

        @Override
        public Character lastElement(final String haystack, final Character desired) {

            if (haystack.isEmpty()) {
                return null;
            }
            else {
                return haystack.charAt(haystack.length() - 1);
            }
        }

        @Override
        public Character firstElement(final String haystack, final Character desired) {

            if (haystack.isEmpty()) {
                return null;
            }
            else {
                return haystack.charAt(0);
            }
        }

        @Override
        public Character atIndex(final String haystack, final int index, final Character desired) {

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

    protected static final class StringOnStringOperations implements
        OrderedSequenceOperations<CharSequence, String> {

        @Override
        public String lastElement(final String haystack, final CharSequence desired) {

            final int length = desired.length();

            if (haystack.isEmpty()) {
                return null;
            }
            else if (length > haystack.length()) {
                return haystack;
            }
            else {
                return haystack.substring(haystack.length() - length);
            }
        }

        @Override
        public String atIndex(final String haystack, final int index, final CharSequence desired) {

            final int length = desired.length();

            if (haystack.isEmpty() || index >= haystack.length()) {
                return null;
            }
            else if ((index + length) > haystack.length()) {
                return haystack.substring(index);
            }
            else {
                return haystack.substring(index, length);
            }
        }

        @Override
        public int indexOf(final String haystack, final CharSequence needle) {
            return haystack.indexOf(String.valueOf(needle));
        }

        @Override
        public CharSequence firstElement(final String haystack, final CharSequence desired) {

            final int length = desired.length();

            if (haystack.isEmpty()) {
                return null;
            }
            else if (length > haystack.length()) {
                return haystack;
            }
            else {
                return haystack.substring(0, length);
            }
        }

        @Override
        public List<CharSequence> findAllIn(final String haystack, final Collection<CharSequence> needles) {
            return needles.stream()
                .filter(haystack::contains)
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
        public boolean includes(final String haystack, final CharSequence needle) {
            return haystack.contains(needle);
        }
    }
}