package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.logic.unary.Observed;
import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceConditions;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SequenceConditions;
import org.jspecify.annotations.Nullable;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;

/**
 * A value used to evaluate {@link String}s.
 *
 * @author Justin Albano
 */
public final class StringValue extends BaseOrderedSequenceValue<String, Character, StringValue> {

    private final StringOnStringOperations stringOperations = new StringOnStringOperations();

    /**
     * Creates a new expression.
     *
     * @param subject
     *         The subject of the expression.
     */
    public StringValue(final @Nullable String subject) {
        super(subject, "String value", new StringOperations());
    }

    /**
     * Checks if the value is blank (empty or contains only whitespace).
     *
     * @return This value (fluent interface).
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
     * Checks if the value is not blank (empty or contains only whitespace).
     *
     * @return This value (fluent interface).
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

    /**
     * Checks if the value includes the supplied character sequence. An empty
     * character sequence ({@code ""}) is always considered included and a
     * {@code null} character sequence is always excluded (not included).
     *
     * @param desired
     *         The desired character sequence.
     *
     * @return This value (fluent interface).
     */
    public StringValue includes(final @Nullable CharSequence desired) {
        return append(
                OrderedSequenceConditions.includes(asString(desired), stringOperations)
        );
    }

    /**
     * Checks if the value excludes the supplied character sequence. An empty
     * character sequence ({@code ""}) is always considered included (never
     * excluded) and a {@code null} character sequence is always excluded.
     *
     * @param undesired
     *         The character sequence that should not be found in the value.
     *
     * @return This value (fluent interface).
     */
    public StringValue excludes(final @Nullable CharSequence undesired) {
        return append(
                OrderedSequenceConditions.excludes(asString(undesired), stringOperations)
        );
    }

    /**
     * Checks that the supplied character sequence is found the supplied number
     * of times. This check abides by the following rules:
     * <ul>
     *     <li><strong>Overlap</strong>: The number of occurrence is countered
     *     without overlap. For example, if the value is {@code "aaa"} and the
     *     desired character sequence is {@code "aa"}, only one occurrence will
     *     be found.</li>
     *     <li><strong>Empty character sequence</strong>: A supplied empty
     *     character sequence always results in {@code value.length + 1}
     *     occurrences being counted. For example, given the value
     *     {@code "aaa"}, {@code includesExactly("", 4)} will pass. This matches
     *     the logic used by both {@link String#indexOf(String)} and
     *     {@link String#lastIndexOf(String)}.</li>
     *     <li><strong>{@code null} character sequence</strong>: A {@code null}
     *     character sequence is never found (have 0 occurrences) by definition.
     *     This differs from the logic in {@link String#contains(CharSequence)},
     *     which throws an exception. This method is designed not to throw an
     *     exception, so it results in 0 occurrences instead.</li>
     * </ul>
     * This check counts without overlap. For example, if the value is
     * {@code "aaa"} and the desired character sequence is {@code "aa"}, only one occurrence will be found.
     *
     * @param desired
     *         The desired character sequence.
     * @param times
     *         The desired number of occurrences.
     *
     * @return This value (fluent interface).
     */
    public StringValue includesExactly(final @Nullable CharSequence desired, final long times) {
        return append(
                SequenceConditions.includesExactly(asString(desired), times, stringOperations)
        );
    }

    /**
     * Checks if the value includes the supplied character sequence exactly
     * once. For more information about the inclusion and exclusion logic, see
     * {@link #includesExactly(CharSequence, long)}.
     *
     * @param desired
     *         The desired character sequence.
     *
     * @return This value (fluent interface).
     *
     * @see #includesExactly(CharSequence, long)
     */
    public StringValue includesOnce(final @Nullable CharSequence desired) {
        return includesExactly(desired, 1);
    }

    /**
     * Checks if the value includes the supplied character sequence the supplied
     * number of times. For more information about the inclusion and exclusion
     * logic, see {@link #includesExactly(CharSequence, long)}.
     *
     * @param desired
     *         The desired character sequence.
     * @param times
     *         The desired number of occurrences.
     *
     * @return This value (fluent interface).
     *
     * @see #includesExactly(CharSequence, long)
     */
    public StringValue includesMoreThan(final @Nullable CharSequence desired, final long times) {
        return append(
                SequenceConditions.includesMoreThan(asString(desired), times, stringOperations)
        );
    }

    /**
     * Checks if the value includes the supplied character sequence more than or
     * equals to supplied number of times. For more information about the
     * inclusion and exclusion logic, see
     * {@link #includesExactly(CharSequence, long)}.
     *
     * @param desired
     *         The desired character sequence.
     * @param times
     *         The desired lower bound of occurrences.
     *
     * @return This value (fluent interface).
     *
     * @see #includesExactly(CharSequence, long)
     */
    public StringValue includesMoreThanOrEqualTo(final @Nullable CharSequence desired, final long times) {
        return append(
                SequenceConditions.includesMoreThanOrEqualTo(asString(desired), times, stringOperations)
        );
    }

    /**
     * Checks if the value includes the supplied character sequence less than
     * supplied number of times. For more information about the inclusion and
     * exclusion logic, see {@link #includesExactly(CharSequence, long)}.
     *
     * @param desired
     *         The desired character sequence.
     * @param times
     *         The desired upper bound of occurrences.
     *
     * @return This value (fluent interface).
     *
     * @see #includesExactly(CharSequence, long)
     */
    public StringValue includesLessThan(final @Nullable CharSequence desired, final long times) {
        return append(
                SequenceConditions.includesLessThan(asString(desired), times, stringOperations)
        );
    }

    /**
     * Checks if the value includes the supplied character sequence less than or
     * equal to supplied number of times. For more information about the
     * inclusion and exclusion logic, see
     * {@link #includesExactly(CharSequence, long)}.
     *
     * @param desired
     *         The desired character sequence.
     * @param times
     *         The desired upper bound of occurrences.
     *
     * @return This value (fluent interface).
     *
     * @see #includesExactly(CharSequence, long)
     */
    public StringValue includesLessThanOrEqualTo(final @Nullable CharSequence desired, final long times) {
        return append(
                SequenceConditions.includesLessThanOrEqualTo(asString(desired), times, stringOperations)
        );
    }

    /**
     * Checks if the value has the supplied character sequence at the supplied
     * index. This check abides by the following rules:
     * <ul>
     *     <li><strong>Overlap</strong>: The index of the match marks the first
     *     occurrence of the match. For example, if the value is {@code "aaa"}
     *     and the desired sequence is {@code "aa"}, the (first) index would be
     *     {@code 0} and the last index would also be {@code 0}.</li>
     *     <li><strong>Empty character sequence</strong>: A supplied empty
     *     character sequence always results in the character being found at
     *     every index up to and including the length of the value. For example,
     *     given the value {@code "aaa"}, {@code hasAtIndex("", ...)} will pass
     *     for every index value in the range {@code [0, 4]}.</li>
     *     <li><strong>{@code null} character sequence</strong>: A {@code null}
     *     character sequence is never found and will fail for every index.</li>
     * </ul>
     *
     * @param desired
     *         The desired character sequence.
     * @param index
     *         The desired index.
     *
     * @return This value (fluent interface).
     */
    public StringValue hasAtIndex(final @Nullable CharSequence desired, final int index) {
        return append(
                OrderedSequenceConditions.hasAtIndex(asString(desired), index, stringOperations)
        );
    }

    /**
     * Checks if the value does not have an index of the supplied character
     * sequence matching the supplied index. For more information about the
     * indexing logic, see {@link #hasAtIndex(CharSequence, int)}.
     *
     * @param undesired
     *         The character sequence that should not have an index matching the
     *         supplied index.
     * @param index
     *         The undesired index.
     *
     * @return This value (fluent interface).
     *
     * @see #hasAtIndex(CharSequence, int)
     */
    public StringValue doesNotHaveAtIndex(final @Nullable CharSequence undesired, final int index) {
        return append(
                OrderedSequenceConditions.doesNotHaveAtIndex(asString(undesired), index, stringOperations)
        );
    }

    /**
     * Checks if the value has a last index of the supplied character sequence
     * matching the supplied index. For more information about the indexing
     * logic, see {@link #hasAtIndex(CharSequence, int)}.
     *
     * @param desired
     *         The character sequence that should have a last index matching the
     *         supplied index.
     * @param index
     *         The desired last index.
     *
     * @return This value (fluent interface).
     *
     * @see #hasAtIndex(CharSequence, int)
     */
    public StringValue lastIndexOfIs(final @Nullable CharSequence desired, final int index) {
        return append(
                OrderedSequenceConditions.lastIndexOfIs(asString(desired), index, stringOperations)
        );
    }

    /**
     * Checks if the value does not have a last index of the supplied character
     * sequence matching the supplied index. For more information about the
     * indexing logic, see {@link #hasAtIndex(CharSequence, int)}.
     *
     * @param undesired
     *         The character sequence that should not have a last index matching
     *         the supplied index.
     * @param index
     *         The undesired last index.
     *
     * @return This value (fluent interface).
     *
     * @see #hasAtIndex(CharSequence, int)
     */
    public StringValue lastIndexOfIsNot(final @Nullable CharSequence undesired, final int index) {
        return append(
                OrderedSequenceConditions.lastIndexOfIsNot(asString(undesired), index, stringOperations)
        );
    }

    /**
     * Checks if the value starts with the supplied character sequence.
     *
     * @param desired
     *         The desired character sequence.
     *
     * @return This value (fluent interface).
     */
    public StringValue startsWith(final @Nullable CharSequence desired) {
        return append(
                OrderedSequenceConditions.startsWith(asString(desired), stringOperations)
        );
    }

    /**
     * Checks if the value does not start with the supplied character sequence.
     *
     * @param undesired
     *         The undesired character sequence.
     *
     * @return This value (fluent interface).
     */
    public StringValue doesNotStartWith(final @Nullable CharSequence undesired) {
        return append(
                OrderedSequenceConditions.doesNotStartWith(asString(undesired), stringOperations)
        );
    }

    /**
     * Checks if the value ends with the supplied character sequence.
     *
     * @param desired
     *         The desired character sequence.
     *
     * @return This value (fluent interface).
     */
    public StringValue endsWith(final @Nullable CharSequence desired) {
        return append(
                OrderedSequenceConditions.endsWith(asString(desired), stringOperations)
        );
    }

    /**
     * Checks if the value does not end with the supplied character sequence.
     *
     * @param undesired
     *         The undesired character sequence.
     *
     * @return This value (fluent interface).
     */
    public StringValue doesNotEndWith(final @Nullable CharSequence undesired) {
        return append(
                OrderedSequenceConditions.doesNotEndWith(asString(undesired), stringOperations)
        );
    }

    private static @Nullable String asString(final @Nullable CharSequence sequence) {
        return sequence == null ? null : sequence.toString();
    }

    /**
     * Checks if the supplied regular expression matches the subject.
     *
     * @param regex
     *         The regular expression to match.
     *
     * @return This value (fluent interface).
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
     * Checks if the supplied regular expression does not match the subject.
     *
     * @param regex
     *         The regular expression to not match.
     *
     * @return This value (fluent interface).
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
     * Checks if the supplied argument equals the subject, ignoring case.
     *
     * @param other
     *         The string to match.
     *
     * @return This value (fluent interface).
     */
    public StringValue isCaseInsensitiveEqualTo(final @Nullable String other) {
        return append(
                expression(s -> s.equalsIgnoreCase(other))
                        .name("caseInsensitiveEqualTo[" + stringify(other) + "]")
                        .expected(stringify(other))
                        .build()
        );
    }

    /**
     * Checks if the last index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *         The needle to look for in the subject.
     * @param index
     *         The expected last index.
     *
     * @return This value (fluent interface).
     */
    public StringValue hasAtLastIndex(final char needle, final int index) {
        return lastIndexOfIs((Character) needle, index);
    }

    /**
     * Checks if the last index of the supplied needle in the subject matches
     * the supplied index.
     *
     * @param needle
     *         The needle to look for in the subject.
     * @param index
     *         The expected last index.
     *
     * @return This value (fluent interface).
     */
    public StringValue hasAtLastIndex(final @Nullable String needle, final int index) {
        return lastIndexOfIs((CharSequence) needle, index);
    }

    /**
     * Checks if the last index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *         The needle to look for in the subject.
     * @param index
     *         The index expected not to match.
     *
     * @return This value (fluent interface).
     */
    public StringValue doesNotHaveAtLastIndex(final char needle, final int index) {
        return lastIndexOfIsNot((Character) needle, index);
    }

    /**
     * Checks if the last index of the supplied needle in the subject does not
     * match the supplied index.
     *
     * @param needle
     *         The needle to look for in the subject.
     * @param index
     *         The index expected not to match.
     *
     * @return This value (fluent interface).
     */
    public StringValue doesNotHaveAtLastIndex(final @Nullable String needle, final int index) {
        return lastIndexOfIsNot((CharSequence) needle, index);
    }

    private static final class StringOperations implements OrderedSequenceOperations<Character, String> {

        @Override
        public Observed<Character> lastElement(final String haystack, final @Nullable Character desired) {

            if (haystack.isEmpty()) {
                return Observed.missing();
            }
            else {
                return Observed.found(haystack.charAt(haystack.length() - 1));
            }
        }

        @Override
        public Observed<Character> atIndex(final String haystack, final int index, final @Nullable Character desired) {

            if (index >= 0 && index < haystack.length()) {
                return Observed.found(haystack.charAt(index));
            }
            else {
                return Observed.missing();
            }
        }

        @Override
        public int indexOf(final String haystack, final @Nullable Character needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.indexOf(needle);
            }
        }

        @Override
        public int lastIndexOf(final String haystack, final @Nullable Character needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.lastIndexOf(needle);
            }
        }

        @Override
        public int occurrences(final String haystack, final @Nullable Character needle) {

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
        public int size(final String haystack) {
            return haystack.length();
        }

        @Override
        public boolean includes(final String haystack, final @Nullable Character needle) {
            return indexOf(haystack, needle) >= 0;
        }
    }

    private static final class StringOnStringOperations implements OrderedSequenceOperations<String, String> {

        @Override
        public Observed<String> lastElement(final String haystack, final @Nullable String desired) {

            final int length = lengthOf(desired);

            if (haystack.isEmpty() && length > 0) {
                return Observed.missing();
            }
            else {
                return Observed.found(haystack.substring(Math.max(haystack.length() - length, 0)));
            }
        }

        @Override
        public Observed<String> atIndex(final String haystack, final int index, final @Nullable String desired) {

            final int length = lengthOf(desired);

            // An empty sequence can be found at the end of the haystack (where index equals the length)
            if (index < 0 || index > haystack.length() || (index == haystack.length() && length > 0)) {
                return Observed.missing();
            }
            else {
                return Observed.found(haystack.substring(index, Math.min(index + length, haystack.length())));
            }
        }

        @Override
        public int indexOf(final String haystack, final @Nullable String needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.indexOf(needle);
            }
        }

        @Override
        public int lastIndexOf(final String haystack, final @Nullable String needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.lastIndexOf(needle);
            }
        }

        @Override
        public int occurrences(final String haystack, final @Nullable String needle) {

            if (needle == null) {
                return 0;
            }
            else if (needle.isEmpty()) {
                return haystack.length() + 1;
            }

            int count = 0;
            int index = haystack.indexOf(needle);

            while (index >= 0) {
                count++;
                index = haystack.indexOf(needle, index + needle.length());
            }

            return count;
        }

        @Override
        public int size(final String haystack) {
            return haystack.length();
        }

        @Override
        public boolean includes(final String haystack, final @Nullable String needle) {
            return indexOf(haystack, needle) >= 0;
        }

        // A null desired value is compared against a single character so that it is reported as not matching the
        // element at the index (rather than as an empty match or an out-of-bounds index)
        private static int lengthOf(final @Nullable String desired) {
            return desired == null ? 1 : desired.length();
        }
    }
}
