package com.zeal.examples;

import java.util.UUID;

import static com.zeal.assertion.api.Assertions.*;
import static com.zeal.assertion.api.Evaluators.that;
import static com.zeal.expression.api.eval.StringEvaluations.startsWith;
import static com.zeal.expression.api.ops.LogicalOperations.not;
import static com.zeal.expression.api.ops.StringOperations.prepend;

public class PreAndPostconditions {

    public static void main(String[] args) {

        final String username = "foo";
        final String password = "bar";
        final String prefix = "baz";

        final String newUsername = check(not(that(username).satisfies(startsWith(prefix)).hasHashCode(1))).or(prepend(prefix));

        final Session session = login(newUsername, password);

        ensure(that(session.id()).isNotNull());

        System.out.println("ID: " + session.id());
        System.out.println("Username: " + session.username());
    }

    public static Session login(String username, String password) {

        require(that(username).isNotBlank().isLongerThan(8));
        require(that(password).isNotBlank());

        return new Session(username);
    }

    public static final class Session {

        private final String id;
        private final String username;

        public Session(String username) {
            this.id = UUID.randomUUID().toString();
            this.username = verify(that(username).isNotBlank());
        }

        public String id() {
            return id;
        }

        public String username() {
            return username;
        }
    }
}
