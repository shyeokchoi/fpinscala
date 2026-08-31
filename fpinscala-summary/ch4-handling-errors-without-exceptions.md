# Throwing Exceptions Breaks Referential Transparency

```scala
def failingFn(i: Int): Int =
  val y: Int = throw new Exception("fail!")
  try {
    val x = 42 + 5
    x + y
  }
  catch { case e: Exception => 43 }
```

Calling `failingFn` throws the expected exception because `y` is evaluated before the `try` block:

```
scala> failingFn(12)
java.lang.Exception: fail!
  at .failingFn(<console>:8)
...
```

Here, `y` is not referentially transparent because replacing it with its defining expression changes the program's behavior.

If we replace `y` in `x + y` with `throw new Exception("fail!")`, the `try` block catches the exception and returns `43`.

Another way to understand **RT** is that the meaning of an RT expression *does not depend on its context*, so we can reason about it locally.

In contrast, the behavior of `throw new Exception("fail!")` depends on whether its context catches the exception.

## Problems with Exceptions

1. Throwing exceptions breaks referential transparency, so we lose the simple reasoning provided by the substitution model.
2. Scala's function types do not track exceptions. The type `Int => Int` does not reveal that evaluating `failingFn` may throw.

## An Alternative to Exceptions

We want an alternative without those drawbacks, but we still want to **consolidate and centralize error-handling logic**.

We represent errors as values with generic data types such as `Option` and `Either`. These types make possible failures explicit and let callers handle them with ordinary expressions.
