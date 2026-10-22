package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.*;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceExpressions.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions.SizedSequenceOperations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * A value used to evaluate {@link String} instances.
 *
 * @author Justin Albano
 */
public class StringValue extends BaseValue<String, StringValue>
    implements
        SizedSequenceValue<String, StringValue>,
    InspectableSequenceValue<Character, StringValue>,
    OrderedSequenceValue<Character, StringValue>,
    RepeatableSequenceValue<Character, StringValue> {

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
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public StringValue isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
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
                .build()
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
                .build()
        );
    }

    @Override
    public StringValue hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public StringValue doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public StringValue isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public StringValue isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public StringValue isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public StringValue isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public StringValue includes(final Character desired) {
        return append(
            OrderedSequenceExpressions.includes(desired, operations)
        );
    }

    public StringValue includes(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.includes(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue includesAll(final Collection<Character> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    public StringValue includesAllOf(final Character... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public StringValue includesAny(final Collection<Character> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    public StringValue includesAnyOf(final Character... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public StringValue includesExactly(final Character desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public StringValue includesMoreThan(final Character desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public StringValue includesMoreThanOrEqualTo(final Character desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public StringValue includesLessThan(final Character desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public StringValue includesLessThanOrEqualTo(final Character desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public StringValue excludes(final Character desired) {
        return append(
            OrderedSequenceExpressions.excludes(desired, operations)
        );
    }

    public StringValue excludes(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.excludes(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue excludesAll(final Collection<Character> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    public StringValue excludesAllOf(final Character... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public StringValue excludesAny(final Collection<Character> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    public StringValue excludesAnyOf(final Character... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public StringValue hasAtIndex(final Character desired, final int index) {
        return append(
            OrderedSequenceExpressions.hasAtIndex(desired, index, operations)
        );
    }

    public StringValue hasAtIndex(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.hasAtIndex(desired, index, charSequenceOperations)
        );
    }

    @Override
    public StringValue doesNotHaveAtIndex(final Character desired, final int index) {
        return append(
            OrderedSequenceExpressions.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    public StringValue doesNotHaveAtIndex(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.doesNotHaveAtIndex(desired, index, charSequenceOperations)
        );
    }

    @Override
    public StringValue lastIndexOfIs(final Character desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIs(desired, index, operations)
        );
    }

    public StringValue lastIndexOfIs(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIs(desired, index, charSequenceOperations)
        );
    }

    @Override
    public StringValue lastIndexOfIsNot(final Character desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIsNot(desired, index, operations)
        );
    }

    public StringValue lastIndexOfIsNot(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIsNot(desired, index, charSequenceOperations)
        );
    }

    @Override
    public StringValue startsWith(final Character desired) {
        return append(
            OrderedSequenceExpressions.startsWith(desired, operations)
        );
    }

    public StringValue startsWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.startsWith(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue doesNotStartWith(final Character desired) {
        return append(
            OrderedSequenceExpressions.doesNotStartWith(desired, operations)
        );
    }

    public StringValue doesNotStartWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.doesNotStartWith(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue endsWith(final Character desired) {
        return append(
            OrderedSequenceExpressions.endsWith(desired, operations)
        );
    }

    public StringValue endsWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.endsWith(desired, charSequenceOperations)
        );
    }

    @Override
    public StringValue doesNotEndWith(final Character desired) {
        return append(
            OrderedSequenceExpressions.doesNotEndWith(desired, operations)
        );
    }

    public StringValue doesNotEndWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.doesNotEndWith(desired, charSequenceOperations)
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
                .build()
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
                .build()
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
                .build()
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
        return lastIndexOfIs((Character) needle, index);
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
        return lastIndexOfIs((CharSequence) needle, index);
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
        return lastIndexOfIsNot((Character) needle, index);
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
        return lastIndexOfIsNot((CharSequence) needle, index);
    }

    protected static final class StringOperations implements
        SizedSequenceOperations<String>,
        InspectableSequenceOperations<Character, String>,
        RepeatableSequenceOperations<Character, String>,
        OrderedSequenceOperations<Character, String> {

        @Override
        public Element<Character> lastElement(final String haystack, final Character desired) {

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.charAt(haystack.length() - 1));
            }
        }

        @Override
        public Element<Character> firstElement(final String haystack, final Character desired) {

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.charAt(0));
            }
        }

        @Override
        public Element<Character> atIndex(final String haystack, final int index, final Character desired) {

            if (index >= 0 && index < haystack.length()) {
                return Element.found(haystack.charAt(index));
            }
            else {
                return Element.missing();
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
        public int lastIndexOf(final String haystack, final Character needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.lastIndexOf(needle);
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
        public boolean includes(final String haystack, final Character needle) {
            return haystack.indexOf(needle) >= 0;
        }
    }

    protected static final class StringOnStringOperations implements
        SizedSequenceOperations<String>,
        InspectableSequenceOperations<CharSequence, String>,
        OrderedSequenceOperations<CharSequence, String> {

        @Override
        public Element<CharSequence> lastElement(final String haystack, final CharSequence desired) {

            final int length = desired.length();

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else if (length > haystack.length()) {
                return Element.found(haystack);
            }
            else {
                return Element.found(haystack.substring(haystack.length() - length));
            }
        }

        @Override
        public Element<CharSequence> atIndex(final String haystack, final int index, final CharSequence desired) {

            final int length = desired.length();

            if (index < 0 || index >= haystack.length()) {
                return Element.missing();
            }
            else if ((index + length) > haystack.length()) {
                return Element.found(haystack.substring(index));
            }
            else {
                return Element.found(haystack.substring(index, index + length));
            }
        }

        @Override
        public int indexOf(final String haystack, final CharSequence needle) {
            return haystack.indexOf(String.valueOf(needle));
        }

        @Override
        public int lastIndexOf(final String haystack, final CharSequence needle) {
            return haystack.lastIndexOf(String.valueOf(needle));
        }

        @Override
        public Element<CharSequence> firstElement(final String haystack, final CharSequence desired) {

            final int length = desired.length();

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else if (length > haystack.length()) {
                return Element.found(haystack);
            }
            else {
                return Element.found(haystack.substring(0, length));
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
        public boolean includes(final String haystack, final CharSequence needle) {
            return haystack.contains(needle);
        }
    }
}