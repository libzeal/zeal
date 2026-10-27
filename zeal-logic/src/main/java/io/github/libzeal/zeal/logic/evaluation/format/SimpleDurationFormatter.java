package io.github.libzeal.zeal.logic.evaluation.format;

import java.time.Duration;

final class SimpleDurationFormatter {

    private SimpleDurationFormatter() {
    }

    static String format(final Duration elapsedTime) {

        if (elapsedTime == null) {
            return "";
        }

        if (elapsedTime.equals(Duration.ZERO)) {
            return "0ns";
        }

        final StringBuilder builder = new StringBuilder();
        final long hoursPart = elapsedTime.toHours();
        final long minutesPart = elapsedTime.toMinutes() - (hoursPart * 60);
        final long secondsPart = (elapsedTime.toMillis() / 1000) - (hoursPart * 3600) - (minutesPart * 60);
        final long millisPart =
            elapsedTime.toMillis() - (hoursPart * 3600_000L) - (minutesPart * 60_000L) - (secondsPart * 1000);
        final long remainingNanos = elapsedTime.toNanos() - (hoursPart * 3600_000_000_000L) - (minutesPart * 60_000_000_000L) - (secondsPart * 1000_000_000L) - (millisPart * 1000_000L);
        final long microsPart = remainingNanos / 1000;
        final long nanosPart =
            elapsedTime.toNanos() - (hoursPart * 3600_000_000_000L) - (minutesPart * 60_000_000_000L) - (secondsPart * 1000_000_000L) - (millisPart * 1000_000L) - (microsPart * 1000);

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
