package io.github.libzeal.zeal.logic.format;

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

        if (duration.isNegative()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();
        final long hoursPart = duration.toHours();
        final long minutesPart = duration.toMinutesPart();
        final long secondsPart = duration.toSecondsPart();
        final long millisPart = duration.toMillisPart();
        final long microsPart = (duration.toNanosPart() / 1_000) % 1_000;
        final long nanosPart = duration.toNanosPart() % 1_000;

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
