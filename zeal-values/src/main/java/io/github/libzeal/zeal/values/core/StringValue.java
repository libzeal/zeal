package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.sequence.*;

/**
 * A value used to evaluate {@link String} instances.
 *
 * @author Justin Albano
 */
public class StringValue extends BaseOrderedSequenceValue<String, Character, StringValue> {

    private final StringOnStringOperations stringOperations = new StringOnStringOperations();

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringValue(final String subject) {
        super(subject, "String value", new StringOperations());
    }

    /**
     * Adds a condition to the expression that checks if the subject is blank (empty or contains only whitespace).
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
     * Adds a condition to the expression that checks if the subject is not blank (empty or contains only whitespace).
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

    // TODO Javadoc: the desired sequence is compared by its content (toString), so any CharSequence (such as a
    //  StringBuilder) matches a string with the same characters; an empty sequence is always included (even in an
    //  empty string) and a null sequence is never included
    public StringValue includes(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.includes(asString(desired), stringOperations)
        );
    }

    public StringValue excludes(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.excludes(asString(desired), stringOperations)
        );
    }

    // TODO Javadoc: occurrences are counted without overlapping (e.g., "aa" is found once in "aaa", not twice); an
    //  empty sequence is found length + 1 times (once before each character and once at the end, matching
    //  includes("") passing); a null sequence is found 0 times
    public StringValue includesExactly(final CharSequence desired, final long times) {
        return append(
            SequenceExpressions.includesExactly(asString(desired), times, stringOperations)
        );
    }

    public StringValue includesOnce(final CharSequence desired) {
        return includesExactly(desired, 1);
    }

    public StringValue includesMoreThan(final CharSequence desired, final long times) {
        return append(
            SequenceExpressions.includesMoreThan(asString(desired), times, stringOperations)
        );
    }

    public StringValue includesMoreThanOrEqualTo(final CharSequence desired, final long times) {
        return append(
            SequenceExpressions.includesMoreThanOrEqualTo(asString(desired), times, stringOperations)
        );
    }

    public StringValue includesLessThan(final CharSequence desired, final long times) {
        return append(
            SequenceExpressions.includesLessThan(asString(desired), times, stringOperations)
        );
    }

    public StringValue includesLessThanOrEqualTo(final CharSequence desired, final long times) {
        return append(
            SequenceExpressions.includesLessThanOrEqualTo(asString(desired), times, stringOperations)
        );
    }

    // TODO Javadoc: checks whether the subject has the desired sequence starting at the index (not a single
    //  character); the actual value is the part of the subject at the index with the same length as the desired
    //  sequence; an empty sequence is found at every index from 0 up to and including the length of the subject; a
    //  null sequence is never found
    public StringValue hasAtIndex(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.hasAtIndex(asString(desired), index, stringOperations)
        );
    }

    public StringValue doesNotHaveAtIndex(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.doesNotHaveAtIndex(asString(desired), index, stringOperations)
        );
    }

    // TODO Javadoc: the last index of an empty sequence is the length of the subject (not length - 1); the last
    //  index of a null sequence is -1
    public StringValue lastIndexOfIs(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIs(asString(desired), index, stringOperations)
        );
    }

    public StringValue lastIndexOfIsNot(final CharSequence desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIsNot(asString(desired), index, stringOperations)
        );
    }

    // TODO Javadoc: every string (including an empty string) starts with an empty sequence; a null sequence is
    //  never a prefix
    public StringValue startsWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.startsWith(asString(desired), stringOperations)
        );
    }

    public StringValue doesNotStartWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.doesNotStartWith(asString(desired), stringOperations)
        );
    }

    // TODO Javadoc: every string (including an empty string) ends with an empty sequence; a null sequence is never
    //  a suffix
    public StringValue endsWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.endsWith(asString(desired), stringOperations)
        );
    }

    public StringValue doesNotEndWith(final CharSequence desired) {
        return append(
            OrderedSequenceExpressions.doesNotEndWith(asString(desired), stringOperations)
        );
    }

    private static String asString(final CharSequence sequence) {
        return sequence == null ? null : sequence.toString();
    }

    /**
     * Adds a condition to the expression that checks if the supplied regular expression matches the subject.
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
     * Adds a condition to the expression that checks if the supplied regular expression does not match the subject.
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
     * Adds a condition to the expression that checks if the supplied argument equals the subject, ignoring case.
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
     * Adds a condition to the expression that checks if the last index of the
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
     * Adds a condition to the expression that checks if the last index of the
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
     * Adds a condition to the expression that checks if the last index of the
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
     * Adds a condition to the expression that checks if the last index of the
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

    // TODO Javadoc: a null character is never included in a string (includes fails, excludes passes, and its index
    //  is -1) rather than causing an exception
    protected static final class StringOperations implements OrderedSequenceOperations<Character, String> {

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
        public int size(final String haystack) {
            return haystack.length();
        }

        @Override
        public boolean includes(final String haystack, final Character needle) {
            return indexOf(haystack, needle) >= 0;
        }
    }

    protected static final class StringOnStringOperations implements OrderedSequenceOperations<String, String> {

        @Override
        public Element<String> lastElement(final String haystack, final String desired) {

            final int length = lengthOf(desired);

            if (haystack.isEmpty() && length > 0) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.substring(Math.max(haystack.length() - length, 0)));
            }
        }

        @Override
        public Element<String> atIndex(final String haystack, final int index, final String desired) {

            final int length = lengthOf(desired);

            // An empty sequence can be found at the end of the haystack (where index equals the length)
            if (index < 0 || index > haystack.length() || (index == haystack.length() && length > 0)) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.substring(index, Math.min(index + length, haystack.length())));
            }
        }

        @Override
        public int indexOf(final String haystack, final String needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.indexOf(needle);
            }
        }

        @Override
        public int lastIndexOf(final String haystack, final String needle) {

            if (needle == null) {
                return -1;
            }
            else {
                return haystack.lastIndexOf(needle);
            }
        }

        @Override
        public int occurrences(final String haystack, final String needle) {

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
        public boolean includes(final String haystack, final String needle) {
            return indexOf(haystack, needle) >= 0;
        }

        // A null desired value is compared against a single character so that it is reported as not matching the
        // element at the index (rather than as an empty match or an out-of-bounds index)
        private static int lengthOf(final String desired) {
            return desired == null ? 1 : desired.length();
        }
    }
}
