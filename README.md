<p align="center">
  <img alt="Zeal Logo" src="/assets/zeal-logo.svg" />
</p>

# Zeal
Zeal is a Java assertion library built on a rich expression language. Simple, extensible, no fuss.

## Adding It
The Maven dependency is:

```xml
<dependency>
    <groupId>io.github.libzeal</groupId>
    <artifactId>zeal-assertion</artifactId>
    <version>0.2.0</version>
</dependency>
```

The Gradle dependency is:

```groovy
implementation 'io.github.libzeal:zeal-assertion:0.2.0'
```

## Using It
A simple Zeal precondition can be written on a single line:

```java
require(value("foo").isNotNull().isNotBlank());
```

The `value` method creates an evaluation from the string `foo`, and the `require` method evaluates that expression. The `isNotNull()` and `isNotBlank()` calls add two predicates to the expression that are checked when it is evaluated.

While many assertion libraries are available, Zeal is designed on the principle that expressions are separated from their evaluation. Thus, the same expression can be evaluated differently -- such as a precondition or a postcondition -- and custom expressions can be supplied to any evaluator. For example, the same expression above can be evaluated as a postcondition using the `ensure` method:

```java
ensure(value("foo").isNotNull().isNotBlank());
```

Likewise, we can evaluate any expression as a precondition:

```java
UnaryExpression<String> expression = // ...
require(expression);
```

## Goals
1. **Simple**: Expression and evaluators clearly document developer's intent.
2. **Portable**: Written in Java 8, OSGi compliant, and under 100 KB
3. **Independent**: No external dependencies
4. **Extensible**: Provides expressions for common types, but makes creating new expressions easy

## The Basics
Zeal is based a few main concepts:
1. Evaluators
2. Expressions
3. Formatters

### Evaluators
An evaluator is a method that evaluates an expression, such as an assertion. Zeal has three basic types of assertions:

```java
confirm(expression)     // Confirmation (simple assertion)
require(expression)     // Precondition
ensure(expression)      // Postcondition
```

All of these are static methods of the following class:

```java
io.github.libzeal.zeal.assertion.Assertions
```

#### Confirmations
A confirmation is the equivalent of a basic `assert` and is created using the `confirm` method, which:
* Throws an `AssertionFailedException` if the expression evaluates to false
* Returns the subject of a `UnaryExpression` if the expression evaluates to true

`AssertionFailedException` is a subclass of [`IllegalStateException`](https://docs.oracle.com/en/java/javase/21/docs/api///java.base/java/lang/IllegalStateException.html). An example of a simple assertion is:

```java
public String processName(String name) {
    
    String firstName = extractFirstName(name);
    
    confirm(value(name).startsWith(firstName));
    
    return "Hello, " + firstName;
}
```

A custom message can be added to the `AssertionFailedException` thrown when the assertion fails:

```java
confirm(value(name).startsWith(firstName), "First name must be first");
```

#### Preconditions
A precondition is created using the `require` method, which:
* Throws a `NullPointerException` (NPE) if the expression fails and the subject is `null`
* Throws a `PreconditionFailedException` if the expression fails for any other reason
* Returns the subject of a `UnaryExpression` if the expression evaluates to true

An example of a precondition is:

```java
class Credentials {

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = require(value(username).isNotNull().isNotBlank());
        this.password = require(value(password).isNotNull().isLongerThan(8), 
            "Password must has sufficient length");
    }

    // ...other methods...
}
```

The [convention](https://stackoverflow.com/q/3881/2403253) is to throw an NPE if an argument is 
`null` when it is expected to be not `null`. Therefore, `require` will throw an NPE if the subject of the expression is `null`. A `PreconditionFailedException` (which is a subclass of 
`IllegalArgumentException`) will be thrown when an expression is evaluated to false in all other cases.

> **Note**: The JDK throws an NPE for `Objects.requireNonNull()` and this has become the convention. There 
> is controversy about whether an NPE or `IllegalArgumentException` should be thrown. The convention of an NPE is 
> used by default and an option is provided for throwing another exception on `null` (see `Requirement.
> thatThrowsOnNull` below).

#### Postconditions
A postcondition is created using the `ensure` method, which:
* Throws a `PostconditionFailedException` if the expression fails for any reason
* Returns the subject of a `UnaryExpression` if the expression evaluates to true

An example of a precondition is:

```java
class Credentials {

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = require(value(username).isNotNull().isNotBlank());
        this.password = require(value(password).isNotNull().isLongerThan(8), 
            "Password must has sufficient length");
    }

    // ...other methods...
}
```

### Expressions
Expressions are the heart of Zeal. An `Expression` is any object that can be evaluated to true or false (`PASSED` or 
`FAILED`, respectively in Zeal). Unary expressions in particular are critical to creating Zeal assertions.

#### Unary Expressions
Unary expressions evaluate a single subject. This focus on a single subject allows Zeal assertions to return the 
subject of an evaluation. For example:

```java
String verifiedName = require(value(name).isLongerThan(8));
```

The `value` method wraps a single object into a unary expression. The unary expression that is created has a set of predicates (such as `isLongerThan`) that can be chained together and used when evaluating the expression. For example:

```java
require(value("foo").isNotBlank().isLongerThan(8));
```

Two predicates are added to the expression:
1. `isNotBlank()`
2. `isLongerThan(8)`

These predicates are evaluated in order and all predicates must evaluate to true for the expression to evaluate to true (predicates are conjunctive). If any predicate in the chain fails, then all subsequent predicate evaluations are skipped.

#### Nullity
There is one exception to the rule that predicates are evaluated in order: `isNotNull()`. This predicate is a unique predicate because all other predicates depend on this one being true. 

For example, if we try to evaluate if a `Double` is greater than some value, it will fail if the subject is `null` (greater than does not make sense for the absence of a value). The evaluation did not fail because the subject was not greater than the supplied value, but because the subject was `null`. 

Therefore, Zeal has a special rule:

> If an expression has a `isNotNull()` predicate, this predicate is evaluated before any other predicate.

This ensures that if an assertion wishes to check if a subject is `null`, then the evaluation fails due to the nullity of the subject, not a extraneous reason (such as `isGreaterThan`). If the expression does not contain an `isNotNull` predicate, the first predicate that fails will be considered the source of the failure (since the subject was not explicitly checked for nullity).

## Supported Types
The following types have equivalent expressions supported in the [core types](/zeal-expression-types-core) module:
* `Object`
* `String`
* `Enum`
* `Long`
* `Integer`
* `Short`
* `Double`
* `Float`
* `Number`
* `Boolean`
* `Byte`
* `Character`
