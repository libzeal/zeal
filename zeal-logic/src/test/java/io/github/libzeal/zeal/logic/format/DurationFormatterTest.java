package io.github.libzeal.zeal.logic.format;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationFormatterTest {

    @Test
    void givenNullDuration_whenFormat_thenEmptyStringReturned() {
        assertEquals("", format(null));
    }

    @Test
    void givenExactlyOneHour_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofHours(1);

        assertEquals("1h", format(duration));
    }

    private String format(final Duration duration) {
        return DurationFormatter.format(duration);
    }

    @Test
    void givenOneHourWithCarry_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofHours(1)
                .plus(Duration.ofMinutes(1))
                .plus(Duration.ofSeconds(1))
                .plus(Duration.ofMillis(1))
                .plus(Duration.ofNanos(1001));

        assertEquals("1h 1m 1s", format(duration));
    }

    @Test
    void givenExactlyOneMinute_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofMinutes(1);

        assertEquals("1m", format(duration));
    }

    @Test
    void givenOneMinuteWithCarry_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofMinutes(1)
                .plus(Duration.ofSeconds(1))
                .plus(Duration.ofMillis(1))
                .plus(Duration.ofNanos(1001));

        assertEquals("1m 1s", format(duration));
    }

    @Test
    void givenExactlyOneSecond_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofSeconds(1);

        assertEquals("1s", format(duration));
    }

    @Test
    void givenOneSecondWithCarry_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofSeconds(1)
                .plus(Duration.ofMillis(1))
                .plus(Duration.ofNanos(1001));

        assertEquals("1s", format(duration));
    }

    @Test
    void givenExactlyOneMillisecond_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofMillis(1);

        assertEquals("1ms", format(duration));
    }

    @Test
    void givenOneMillisecondWithCarry_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofMillis(1)
                .plus(Duration.ofNanos(1001));

        assertEquals("1ms", format(duration));
    }

    @Test
    void givenExactlyOneMicrosecond_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofNanos(1000);

        assertEquals("1us", format(duration));
    }

    @Test
    void givenOneMicrosecondWithCarry_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofNanos(1001);

        assertEquals("1us", format(duration));
    }

    @Test
    void givenExactlyOneNanosecond_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofNanos(1);

        assertEquals("1ns", format(duration));
    }

    @Test
    void givenZero_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ZERO;

        assertEquals("0ns", format(duration));
    }

    @Test
    void givenThreeDigitNanoseconds_whenFormat_thenAllDigitsKept() {

        final Duration duration = Duration.ofNanos(123);

        assertEquals("123ns", format(duration));
    }

    @Test
    void givenMicroseconds_whenFormat_thenRoundedToThreeSignificantFigures() {

        final Duration duration = Duration.ofNanos(12_345);

        assertEquals("12.3us", format(duration));
    }

    @Test
    void givenMicrosecondsWithTrailingZero_whenFormat_thenTrailingZeroStripped() {

        final Duration duration = Duration.ofNanos(9_100);

        assertEquals("9.1us", format(duration));
    }

    @Test
    void givenMilliseconds_whenFormat_thenRoundedToThreeSignificantFigures() {

        final Duration duration = Duration.ofNanos(4_526_800);

        assertEquals("4.53ms", format(duration));
    }

    @Test
    void givenSeconds_whenFormat_thenRoundedToThreeSignificantFigures() {

        final Duration duration = Duration.ofMillis(1_500);

        assertEquals("1.5s", format(duration));
    }

    @Test
    void givenMicrosecondsRoundingUpToTwoDigits_whenFormat_thenNoDecimalShown() {

        final Duration duration = Duration.ofNanos(9_996);

        assertEquals("10us", format(duration));
    }

    @Test
    void givenMicrosecondsRoundingIntoMilliseconds_whenFormat_thenNextUnitUsed() {

        final Duration duration = Duration.ofNanos(999_600);

        assertEquals("1ms", format(duration));
    }

    @Test
    void givenNanosecondsJustBelowOneMicrosecond_whenFormat_thenNanosecondsUsed() {

        final Duration duration = Duration.ofNanos(999);

        assertEquals("999ns", format(duration));
    }

    @Test
    void givenNegative_whenFormat_thenEmptyStringReturned() {

        final Duration duration = Duration.ofNanos(-168);

        assertEquals("", format(duration));
    }
}
