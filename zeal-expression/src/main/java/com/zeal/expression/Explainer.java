package com.zeal.expression;

public interface Explainer<T> {

    Explanation explain(T value);

    static <T> Builder<T> builder() {
        return new Builder<T>() {

            private String description = "(none)";
            private String expected = "(unknown)";
            private String actual = "(unknown)";
            private boolean isNotNullChecker = false;

            @Override
            public Builder<T> withDescription(String description) {
                this.description = description;
                return this;
            }

            @Override
            public Builder<T> withExpected(String expected) {
                this.expected = expected;
                return this;
            }

            @Override
            public Builder<T> markAsNotNullChecker() {
                this.isNotNullChecker = true;
                return this;
            }

            @Override
            public Explainer<T> build() {
                return actual -> {
                    return new Explanation() {

                        @Override
                        public Explanation not() {
                            return null;
                        }

                        @Override
                        public String description() {
                            return description;
                        }

                        @Override
                        public String expected() {
                            return expected;
                        }

                        @Override
                        public String actual() {
                            return String.valueOf(actual);
                        }

                        @Override
                        public boolean checksForNotNull() {
                            return isNotNullChecker;
                        }
                    };
                };
            }
        };
    }

    interface Builder<T> {
        Builder<T> withDescription(String description);
        Builder<T> withExpected(String expected);
        Builder<T> markAsNotNullChecker();
        Explainer<T> build();
    }
}
