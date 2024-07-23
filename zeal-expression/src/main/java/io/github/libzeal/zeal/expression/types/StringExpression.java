package io.github.libzeal.zeal.expression.types;

/**
 * An expression used to evaluate {@link String} instances.
 */
public class StringExpression extends ObjectExpression<String, StringExpression> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    public StringExpression(String subject) {
        super(subject, "String evaluation");
    }

    public StringExpression isEmpty() {
        return newEvaluation(String::isEmpty)
            .name("isEmpty")
            .expectedValue("true")
            .append();
    }

    public StringExpression isNotEmpty() {
        return newEvaluation(s -> !s.isEmpty())
            .name("isNotEmpty")
            .expectedValue("true")
            .append();
    }

    public StringExpression isBlank() {
        return newEvaluation(s -> s.trim().isEmpty())
            .name("isBlank")
            .expectedValue("true")
            .append();
    }

    public StringExpression isNotBlank() {
        return newEvaluation(s -> !s.trim().isEmpty())
            .name("isNotBlank")
            .expectedValue("true")
            .append();
    }

    public StringExpression hasLengthOf(final long length) {
        return newEvaluation(s -> s.length() == length)
            .name("hasLength[" + length + "]")
            .expectedValue("length := " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    public StringExpression isLongerThan(final long length) {
        return newEvaluation(s -> s.length() > length)
            .name("isLongerThan[" + length + "]")
            .expectedValue("length > " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    public StringExpression isShorterThan(long length) {
        return newEvaluation(s -> s.length() < length)
            .name("isShorterThan[" + length + "]")
            .expectedValue("length < " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    public StringExpression isLongerThanOrEqualTo(long length) {
        return newEvaluation(s -> s.length() >= length)
            .name("isLongerThanOrEqualTo[" + length + "]")
            .expectedValue("length >= " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    public StringExpression isShorterThanOrEqualTo(long length) {
        return newEvaluation(s -> s.length() <= length)
            .name("isShortThanOrEqualTo[" + length + "]")
            .expectedValue("length <= " + length)
            .actualValue(value -> "length := " + value.length())
            .append();
    }

    public StringExpression includes(final char c) {
        return newEvaluation(s -> s.indexOf(c) != -1)
            .name("includes[" + c + "]")
            .expectedValue(c + " is included")
            .actualValue(s -> needleInHaystackActualValue(s, c))
            .hint(s -> needleInHaystackHint(s, c))
            .append();
    }

    private static String needleInHaystackActualValue(String s, char c) {
        return s.indexOf(c) != -1 ? c + " is included" : c + " is excluded";
    }

    private static String needleInHaystackHint(String s, char c) {

        int index = s.indexOf(c);

        if (index != -1) {
            return c + " found at index " + index;
        }
        else {
            return "String does not include " + c;
        }
    }

    public StringExpression includes(final CharSequence sequence) {
        return newEvaluation(s -> s.contains(sequence))
            .name("includes[" + sequence + "]")
            .expectedValue(sequence + " is included")
            .actualValue(s -> needleInHaystackActualValue(s, sequence))
            .hint(s -> needleInHaystackHint(s, sequence))
            .append();
    }

    private static String needleInHaystackActualValue(String s, CharSequence sequence) {
        return s.contains(sequence) ? sequence + " is included" : sequence + " is excluded";
    }

    private static String needleInHaystackHint(String s, CharSequence sequence) {

        int index = s.indexOf(sequence.toString());

        if (index != -1) {
            return sequence + " found at index " + index;
        }
        else {
            return "String does not include " + sequence;
        }
    }

    public StringExpression excludes(final char c) {
        return newEvaluation(s -> s.indexOf(c) == -1)
            .name("excludes[" + c + "]")
            .expectedValue(c + " is excluded")
            .actualValue(s -> needleInHaystackActualValue(s, c))
            .hint(s -> needleInHaystackHint(s, c))
            .append();
    }

    public StringExpression excludes(final CharSequence sequence) {
        return newEvaluation(s -> !s.contains(sequence))
            .name("excludes[" + sequence + "]")
            .expectedValue(sequence + " is excluded")
            .actualValue(s -> needleInHaystackActualValue(s, sequence))
            .hint(s -> needleInHaystackHint(s, sequence))
            .append();
    }

    public StringExpression occurs(final char c, final long times) {
        return newEvaluation(s -> characterCount(s, c) == times)
            .name("occurs[" + c + "]")
            .expectedValue("occurrences := " + times)
            .actualValue(value -> countTimes(value, c))
            .append();
    }

    private static String countTimes(final String value, final char c) {
        return "times := " + characterCount(value, c);
    }

    private static long characterCount(final String s, final char c) {
        return s.chars()
            .filter(ch -> ch == c)
            .count();
    }

    public StringExpression occursLessThan(final char c, final long times) {
        return newEvaluation(s -> characterCount(s, c) < times)
            .name("occurs[" + c + "]")
            .expectedValue("occurrences < " + times)
            .actualValue(value -> countTimes(value, c))
            .append();
    }

    public StringExpression occursMoreThan(final char c, final long times) {
        return newEvaluation(s -> characterCount(s, c) > times)
            .name("occurs[" + c + "]")
            .expectedValue("occurrences > " + times)
            .actualValue(value -> countTimes(value, c))
            .append();
    }

    public StringExpression occursMoreThanOrEqualTo(final char c, final long times) {
        return newEvaluation(s -> characterCount(s, c) >= times)
            .name("occurs[" + c + "]")
            .expectedValue("occurrences >= " + times)
            .actualValue(value -> countTimes(value, c))
            .append();
    }

    public StringExpression occursLessThanOrEqualTo(final char c, final long times) {
        return newEvaluation(s -> characterCount(s, c) <= times)
            .name("occurs[" + c + "]")
            .expectedValue("occurrences <= " + times)
            .actualValue(value -> countTimes(value, c))
            .append();
    }

    public StringExpression startsWith(final String prefix) {
        return newEvaluation(s -> s.startsWith(prefix))
            .name("startsWith[" + prefix + "]")
            .expectedValue("starts with \"" + prefix + "\"")
            .append();
    }

    public StringExpression doesNotStartWith(final String prefix) {
        return newEvaluation(s -> !s.startsWith(prefix))
            .name("doesNotStartWith[" + prefix + "]")
            .expectedValue("not[starts with \"" + prefix + "\"]")
            .append();
    }

    public StringExpression endsWith(final String suffix) {
        return newEvaluation(s -> s.endsWith(suffix))
            .name("endsWith[" + suffix + "]")
            .expectedValue("ends with \"" + suffix + "\"")
            .append();
    }

    public StringExpression doesNotEndWith(final String suffix) {
        return newEvaluation(s -> !s.endsWith(suffix))
            .name("doesNotEndWith[" + suffix + "]")
            .expectedValue("not[ends with \"" + suffix + "\"]")
            .append();
    }

    public StringExpression matches(final String regex) {
        return newEvaluation(s -> s.matches(regex))
            .name("matches[" + regex + "]")
            .expectedValue("matches[" + regex + "]")
            .append();
    }

    public StringExpression doesNotMatch(final String regex) {
        return newEvaluation(s -> !s.matches(regex))
            .name("doesNotMatch[" + regex + "]")
            .expectedValue("not[matches[" + regex + "]]")
            .append();
    }

    public StringExpression isCaseInsensitiveEqualTo(final String other) {
        return newEvaluation(s -> s.equalsIgnoreCase(other))
            .name("caseInsensitiveEqualTo[" + other + "]")
            .expectedValue(other)
            .append();
    }

    public StringExpression hasAtIndex(final char needle, final int index) {
        return newEvaluation(s -> s.indexOf(needle) == index)
            .name("indexOf[" + needle + "] := " + index)
            .expectedValue(s -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    public StringExpression hasAtIndex(final String needle, final int index) {
        return newEvaluation(s -> s.indexOf(needle) == index)
            .name("indexOf[" + needle + "] := " + index)
            .expectedValue(s -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    public StringExpression doesNotHaveAtIndex(final char needle, final int index) {
        return newEvaluation(s -> s.indexOf(needle) != index)
            .name("indexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + s.indexOf(needle) + "]")
            .append();
    }

    public StringExpression doesNotHaveAtIndex(final String needle, final int index) {
        return newEvaluation(s -> s.indexOf(needle) != index)
            .name("indexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + s.indexOf(needle) + "]")
            .append();
    }

    public StringExpression hasAtLastIndex(final char needle, final int index) {
        return newEvaluation(s -> s.lastIndexOf(needle) == index)
            .name("lastIndexOf[" + needle + "] := " + index)
            .expectedValue(s -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    public StringExpression hasAtLastIndex(final String needle, final int index) {
        return newEvaluation(s -> s.lastIndexOf(needle) == index)
            .name("lastIndexOf[" + needle + "] := " + index)
            .expectedValue(s -> String.valueOf(s.indexOf(needle)))
            .append();
    }

    public StringExpression doesNotHaveAtLastIndex(final char needle, final int index) {
        return newEvaluation(s -> s.lastIndexOf(needle) != index)
            .name("lastIndexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + s.indexOf(needle) + "]")
            .append();
    }

    public StringExpression doesNotHaveAtLastIndex(final String needle, final int index) {
        return newEvaluation(s -> s.lastIndexOf(needle) != index)
            .name("lastIndexOf[" + needle + "] != " + index)
            .expectedValue(s -> "not[" + s.indexOf(needle) + "]")
            .append();
    }
}
