package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@link Character}s.
 *
 * @author Justin Albano
 */
public final class BoxedCharacterValue extends BaseValue<Character, BoxedCharacterValue> {

    private static final String IS_LOWER_CASE = "isLowerCase";
    private static final String IS_UPPER_CASE = "isUpperCase";
    private static final String IS_LETTER = "isLetter";
    private static final String IS_DIGIT = "isDigit";
    private static final String IS_LETTER_OR_DIGIT = "isLetterOrDigit";
    private static final String IS_WHITESPACE = "isWhitespace";

    /**
     * Creates a new Character value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedCharacterValue(final @Nullable Character subject) {
        super(subject, "Character value");
    }

    /**
     * Checks if the subject is lower case.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isLowerCase(char)
     */
    public BoxedCharacterValue isLowerCase() {
        return append(
                expression(Character::isLowerCase)
                        .name(IS_LOWER_CASE)
                        .expected(IS_LOWER_CASE)
                        .build()
        );
    }

    /**
     * Checks if the subject is upper case.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isUpperCase(char)
     */
    public BoxedCharacterValue isUpperCase() {
        return append(
                expression(Character::isUpperCase)
                        .name(IS_UPPER_CASE)
                        .expected(IS_UPPER_CASE)
                        .build()
        );
    }

    /**
     * Checks if the subject is a letter.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isLetter(char)
     */
    public BoxedCharacterValue isLetter() {
        return append(
                expression(Character::isLetter)
                        .name(IS_LETTER)
                        .expected(IS_LETTER)
                        .build()
        );
    }

    /**
     * Checks if the subject is not a letter.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isLetter(char)
     */
    public BoxedCharacterValue isNotLetter() {
        return append(
                expression(s -> !Character.isLetter(s))
                        .name("isNotLetter")
                        .expected("not[" + IS_LETTER + "]")
                        .build()
        );
    }

    /**
     * Checks if the subject is a digit.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isDigit(char)
     */
    public BoxedCharacterValue isDigit() {
        return append(
                expression(Character::isDigit)
                        .name(IS_DIGIT)
                        .expected(IS_DIGIT)
                        .build()
        );
    }

    /**
     * Checks if the subject is not a digit.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isDigit(char)
     */
    public BoxedCharacterValue isNotDigit() {
        return append(
                expression(s -> !Character.isDigit(s))
                        .name("isNotDigit")
                        .expected("not[" + IS_DIGIT + "]")
                        .build()
        );
    }

    /**
     * Checks if the subject is a letter or a digit.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isLetterOrDigit(char)
     */
    public BoxedCharacterValue isLetterOrDigit() {
        return append(
                expression(Character::isLetterOrDigit)
                        .name(IS_LETTER_OR_DIGIT)
                        .expected(IS_LETTER_OR_DIGIT)
                        .build()
        );
    }

    /**
     * Checks if the subject is not a letter or a digit.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isLetterOrDigit(char)
     */
    public BoxedCharacterValue isNotLetterOrDigit() {
        return append(
                expression(s -> !Character.isLetterOrDigit(s))
                        .name("isNotLetterOrDigit")
                        .expected("not[" + IS_LETTER_OR_DIGIT + "]")
                        .build()
        );
    }

    /**
     * Checks if the subject is a whitespace.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isWhitespace(char)
     */
    public BoxedCharacterValue isWhitespace() {
        return append(
                expression(Character::isWhitespace)
                        .name(IS_WHITESPACE)
                        .expected(IS_WHITESPACE)
                        .build()
        );
    }

    /**
     * Checks if the subject is not a whitespace.
     *
     * @return This value (fluent interface).
     *
     * @see Character#isWhitespace(char)
     */
    public BoxedCharacterValue isNotWhitespace() {
        return append(
                expression(s -> !Character.isWhitespace(s))
                        .name("isNotWhitespace")
                        .expected("not[" + IS_WHITESPACE + "]")
                        .build()
        );
    }
}
