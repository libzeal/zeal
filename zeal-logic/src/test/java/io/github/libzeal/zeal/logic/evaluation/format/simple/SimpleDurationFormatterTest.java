package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class SimpleDurationFormatterTest {

    private static final SimpleFormatterContext CONTEXT = new SimpleFormatterContext(mock(Cause.class), 0);
    private SimpleDurationFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new SimpleDurationFormatter();
    }

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
        return formatter.format(duration, CONTEXT);
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

        assertEquals("1s 1ms", format(duration));
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

        assertEquals("1ms 1us 1ns", format(duration));
    }

    @Test
    void givenExactlyOneMicrosecond_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofNanos(1000);

        assertEquals("1us", format(duration));
    }

    @Test
    void givenOneMicrosecondWithCarry_whenFormat_thenCorrectFormatIsCorrect() {

        final Duration duration = Duration.ofNanos(1001);

        assertEquals("1us 1ns", format(duration));
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
}
