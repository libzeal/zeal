package io.github.libzeal.zeal.logic.evaluation.format;

import java.time.Duration;

final class DurationFormatter {

    private DurationFormatter() {
    }

    static String format(final Duration duration) {

        if (duration == null) {
            return "";
        }

        if (duration.equals(Duration.ZERO)) {
            return "0ns";
        }

        final StringBuilder builder = new StringBuilder();
        final long hoursPart = duration.toHours();
        final long minutesPart = duration.toMinutes() - (hoursPart * 60);
        final long secondsPart = (duration.toMillis() / 1000) - (hoursPart * 3600) - (minutesPart * 60);
        final long millisPart =
                duration.toMillis() - (hoursPart * 3600_000L) - (minutesPart * 60_000L) - (secondsPart * 1000);
        final long remainingNanos = duration.toNanos() - (hoursPart * 3600_000_000_000L) - (minutesPart * 60_000_000_000L) - (secondsPart * 1000_000_000L) - (millisPart * 1000_000L);
        final long microsPart = remainingNanos / 1000;
        final long nanosPart =
                duration.toNanos() - (hoursPart * 3600_000_000_000L) - (minutesPart * 60_000_000_000L) - (secondsPart * 1000_000_000L) - (millisPart * 1000_000L) - (microsPart * 1000);

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

        if (hoursPart == 0 && minutesPart == 0) {

            if (millisPart > 0) {
                builder.append(millisPart)
                        .append("ms ");
            }

            if (secondsPart == 0) {
                if (microsPart > 0) {
                    builder.append(microsPart)
                            .append("us ");
                }

                if (nanosPart > 0) {
                    builder.append(nanosPart)
                            .append("ns ");
                }
            }
        }

        return builder.toString().trim();
    }
}
