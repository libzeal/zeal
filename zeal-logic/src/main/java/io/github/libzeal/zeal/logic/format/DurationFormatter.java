package io.github.libzeal.zeal.logic.format;

import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;

final class DurationFormatter {

    private static final Duration ONE_MINUTE = Duration.ofMinutes(1);
    private static final MathContext THREE_SIGNIFICANT_FIGURES = new MathContext(3);
    private static final BigDecimal NANOS_PER_MICRO = BigDecimal.valueOf(1_000);
    private static final BigDecimal NANOS_PER_MILLI = BigDecimal.valueOf(1_000_000);
    private static final BigDecimal NANOS_PER_SECOND = BigDecimal.valueOf(1_000_000_000);

    private DurationFormatter() {
    }

    /**
     * Formats the supplied duration as a string.
     * <p>
     * Durations under one minute are rounded to three significant figures and
     * displayed in the largest unit that keeps the value at or above one:
     * nanoseconds ({@code ns}), microseconds ({@code us}), milliseconds
     * ({@code ms}), or seconds ({@code s}). Trailing zeros are removed. The
     * duration is rounded before the unit is selected, so a duration that
     * rounds up into the next unit is displayed in that unit. For example:
     * <ul>
     *     <li>{@code 123ns} is displayed as {@code 123ns}</li>
     *     <li>{@code 9,100ns} is displayed as {@code 9.1us}</li>
     *     <li>{@code 4,526,800ns} is displayed as {@code 4.53ms}</li>
     *     <li>{@code 999,600ns} is displayed as {@code 1ms}, not
     *     {@code 1000us}</li>
     *     <li>{@code 1,500ms} is displayed as {@code 1.5s}</li>
     * </ul>
     * <p>
     * Durations of one minute or more are displayed as whole hours, minutes,
     * and seconds, omitting any part that is zero and discarding fractions of
     * a second (e.g., {@code 1h 1m 1s}, {@code 1m}).
     * <p>
     * A zero duration is displayed as {@code 0ns}.
     *
     * @param duration
     *         The duration to format.
     *
     * @return The formatted duration, or an empty string if the supplied
     * duration is {@code null} or negative.
     */
    static String format(final @Nullable Duration duration) {

        if (duration == null || duration.isNegative()) {
            return "";
        }

        if (duration.compareTo(ONE_MINUTE) < 0) {
            return formatSubMinute(duration);
        }

        return formatMinutesOrMore(duration);
    }

    private static String formatSubMinute(final Duration duration) {

        // Rounding is done before the unit is selected so that a duration that
        // rounds up into the next unit (e.g., 999.6us) is displayed in that
        // unit (1ms) rather than as 1000us.
        final BigDecimal nanos = BigDecimal.valueOf(duration.toNanos())
                .round(THREE_SIGNIFICANT_FIGURES);

        if (nanos.compareTo(NANOS_PER_MICRO) < 0) {
            return inUnit(nanos, BigDecimal.ONE, "ns");
        }
        else if (nanos.compareTo(NANOS_PER_MILLI) < 0) {
            return inUnit(nanos, NANOS_PER_MICRO, "us");
        }
        else if (nanos.compareTo(NANOS_PER_SECOND) < 0) {
            return inUnit(nanos, NANOS_PER_MILLI, "ms");
        }
        else {
            return inUnit(nanos, NANOS_PER_SECOND, "s");
        }
    }

    private static String inUnit(
            final BigDecimal nanos,
            final BigDecimal nanosPerUnit,
            final String unit
    ) {
        return nanos.divide(nanosPerUnit)
                .stripTrailingZeros()
                .toPlainString() + unit;
    }

    private static String formatMinutesOrMore(final Duration duration) {

        final StringBuilder builder = new StringBuilder();
        final long hoursPart = duration.toHours();
        final long minutesPart = duration.toMinutesPart();
        final long secondsPart = duration.toSecondsPart();

        if (hoursPart > 0) {
            builder.append(hoursPart)
                    .append("h ");
        }

        if (minutesPart > 0) {
            builder.append(minutesPart)
                    .append("m ");
        }

        if (secondsPart > 0) {
            builder.append(secondsPart)
                    .append("s ");
        }

        return builder.toString().trim();
    }
}
