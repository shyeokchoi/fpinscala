# Non-strictness (or Laziness) and Strictness

## Definition

- A non-strict function may choose *not* to evaluate one or more of its arguments.
- A strict function always evaluates its arguments.
- Formal definition:
  - If evaluating an expression runs forever or throws an error instead of returning a definite value, the expression does not terminate, or it evaluates to bottom.
  - A function `f` is strict if the expression `f(x)` evaluates to bottom for all `x` that evaluate to bottom.
  - `x = ⊥ ⇒ f(x) = ⊥`

## Example Implementation in Scala

```scala
def if2[A](cond: Boolean, onTrue: () => A, onFalse: () => A): A = {
  if cond then onTrue() else onFalse()
}
```

A `thunk` is a parameterless function that represents a delayed computation.
Here, `onTrue` and `onFalse` are explicit thunks of type `() => A`.
Creating a thunk does not evaluate its body; calling it with `()` does.

This implementation is clear, but this pattern is common in Scala.
Therefore, Scala provides simpler syntax:

```scala
def if3[A](cond: Boolean, onTrue: => A, onFalse: => A): A = {
  if cond then onTrue else onFalse
}
```

Neither syntax caches the result of the delayed computation.
An explicit thunk is evaluated each time it is called, and a call-by-name argument is evaluated each time it is referenced.

Example:

```scala
scala> def maybeTwice(b: Boolean, i: => Int) = if (b) i+i else 0
maybeTwice: (b: Boolean, i: => Int)Int

scala> val x = maybeTwice(true, { println("hi"); 1+41 })
hi
hi
x: Int = 84
```

We can cache the value explicitly with a `lazy val`.

Example:

```scala
scala> def maybeTwice2(b: Boolean, i: => Int) = {
    | lazy val j = i
    | if (b) j+j else 0
    | }
maybeTwice2: (b: Boolean, i: => Int)Int

scala> val x = maybeTwice2(true, { println("hi"); 1+41 })
hi
x: Int = 84
```
