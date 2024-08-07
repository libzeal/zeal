<p align="center">
  <img src="https://github.com/user-attachments/assets/48f0719f-22a1-4934-85be-faa556e4f8b6" />
</p>

# Zeal
Zeal is a Java assertion library built on a rich expression language. Zeal is designed to be simple but extensible and can be added to nearly any Java project without fuss.

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

The `that` method creates an evaluation from the string `foo` and the `require` method evaluates that expression. The `isNotNull()` and `isNotBlank()` calls add two predicates to the expression that are checked when it is evaluated. These three concepts are part of every Zeal assertion:
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
1. **Simple**: Simplicity in design creates code that is easily readable by developers and clearly documents an assertion's intent. Simplicity in implementation means that Zeal is fast, and the source code can be easily extended.
2. **Portable**: Zeal is written in Java 8 and is OSGi compliant, making it usable in nearly all Java projects.
3. **Independent**: Zeal has no dependencies, allowing it to be added to a project without introducing a complex dependency chain.
4. **Extensible**: Zeal provides a foundational set of common expressions but makes creating an expression that suits a new use case easy.
