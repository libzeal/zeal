<p align="center">
  <img src="https://github.com/user-attachments/assets/64060b6e-cee1-4431-a755-bc029c37bb09" />
</p>

# Zeal
Zeal is a Java assertion library built on a rich expression language. Simple, extensible, no fuss.

## Adding It
For Maven, the dependency is:

```xml
<dependency>
    <groupId>io.github.libzeal</groupId>
    <artifactId>zeal-assertion</artifactId>
    <version>0.2.0</version>
</dependency>
```

For Gradle, the dependency is:

```groovy
implementation 'io.github.libzeal:zeal-assertion:0.2.0'
```

## Using It
A simple Zeal precondition can be written on a single line:

```java
require(that("foo").isNotNull().isNotBlank());
```

The `that` method creates an evaluation from the string `foo`, and the `require` method evaluates that expression. The `isNotNull()` and `isNotBlank()` calls add two predicates to the expression that are checked when it is evaluated. These three concepts are part of every Zeal assertion:
1. Expressions
2. Predicates
3. Evaluators

While many assertion libraries are available, Zeal is designed on the principle that expressions are separated from their evaluation. Therefore, the same expression can be evaluated differently, and custom expressions can be supplied to any evaluator. For example, the same expression above can be evaluated as a postcondition using the `ensure` method:

```java
ensure(that("foo").isNotNull().isNotBlank());
```

Likewise, we can evaluate any expression as a precondition:

```java
UnaryExpression<String> expression = // ...
require(expression);
```

## Goals
1. **Simple**: Zeal is simple in design, resulting in expressions and evaluators that clearly document developers' intent. Zeal is also simple in its implementation, making it fast and easily extensible.
3. **Portable**: Zeal is written in Java 8, is OSGi compliant, and is under 100 KB in size, making it usable in nearly all Java projects. Zeal is also distributed under an MIT license, removing any legal burden from its users.
4. **Independent**: Zeal has no dependencies, allowing it to be added to a project without introducing a complex dependency chain.
5. **Extensible**: Zeal provides a foundational set of common expressions but makes creating new expressions easy.

## The Basics
Zeal is based on the concept of an expression, particularly unary expressions. A unary expression is a statement about a single subject that evaluates to true or false. For example, if we look at the sample from before:

```java
require(that("foo").isNotNull().isNotBlank());
```

### Expressions
`that("foo").isNotNull().isNotBlank()` is the expression, while `foo` is the subject of the expression. `isNotNull()` and `isNotBlank()` are predicates, which evaluate a single subject. The `that` method creates a unary expression from the supplied argument. The resulting expression is based on the type of the argument. For example, `that("foo")` results in a `StringUnaryExpression` while `that(1.0)` results in a `BoxedDoubleUnaryExpression`. Each expression has predicates available that can be added to the expression. For example,  `StringUnaryExpression` has `isNotBlank()`, while `BoxedDoubleExpression` has `isGreaterThan(Double other)`.

> **Note**: Calling a predicate method does not perform any evaluation on the subject.

For example, calling `isNotBlank()` does not immediately check if the subject is blank. Instead, calling the predicate method adds a predicate to the predicate chain for that expression. When the expression is evaluated, the predicates are evaluated in order and if a predicate fails, then an assertion is thrown. The types of exceptions thrown will depend on the specific evaluator.

#### Unary Expressions
Unary expressions are a special kind of expression because they allow us to return a single subject. For example, we can obtain the subject of a unary expression by calling the `subject()` method:

```java
UnaryExpression<String> expression = that("foo");
String subject = expression.subject();
```

This may not seem useful, but access to a single subject allows us to return the subject from an evaluator. For example, the subject of an expression can be obtained from the `require` method if all of its predicates pass:

```java
class Credentials {

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = require(that(username).isNotNull().isNotBlank());
        this.password = require(that(password).isNotNull().isLongerThan(8));
    }

    // ...other methods...
}
```

#### The Rule of Nullity
There is one exception to the rule that predicates are evaluated in order: `isNotNull()`. This predicate is a unique predicate because all other predicates depend on this one being true. For example, if we try to evaluate if a `Double` is greater than some value, it will fail if the subject is `null`. It is important to note that the evaluation did not strictly fail because the subject was not greater than the supplied value, but rather, because the subject was `null`. Therefore, Zeal has a special rule:

> Rule of Nullity: If an expression has a `isNotNull()` predicate, this predicate is evaluated before any other predicate.

All other predicates are evaluated in order.

### Preconditions

### Postconditions

### Assertions
