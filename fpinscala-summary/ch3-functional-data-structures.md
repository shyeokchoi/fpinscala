# Companion Object

- A companion object has the same name as its data type.
- It provides a natural place for constructors and functions that operate on the type.

```scala
enum List[+A]:
  case Nil
  case Cons(head: A, tail: List[A])

object List:
  def apply[A](values: A*): List[A] =
    if values.isEmpty then Nil
    else Cons(values.head, apply(values.tail*))

  def sum(ints: List[Int]): Int = ints match
    case Nil              => 0
    case Cons(head, tail) => head + sum(tail)

  def product(ds: List[Double]): Double = ds match
    case Nil              => 1.0
    case Cons(0.0, _)     => 0.0
    case Cons(head, tail) => head * product(tail)
```

# Data Sharing

- Immutable data cannot be changed in place.
- A function that updates an immutable data structure returns a new value instead.
- The new value can reuse the unchanged parts of the original structure.
- This technique is called *data sharing*, or *structural sharing*.

```scala
val original = List(2, 3)
val extended = List.Cons(1, original)
```

- `extended` reuses the entire `original` list as its tail.
- `original` remains unchanged and can still be used independently.
- Prepending an element therefore takes constant time and does not copy the original list.

# Multiple Parameter Lists

A Scala method can have multiple parameter lists.
The original `dropWhile` definition has one parameter list:

```scala
def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
  l match
    case Cons(h, xs) if f(h) => dropWhile(xs, f)
    case _                   => l

val xs: List[Int] = List(1, 2, 3, 4, 5)
val ex1 = dropWhile(xs, x => x < 4)
```

We can instead define it with two parameter lists:

```scala
def dropWhile[A](as: List[A])(f: A => Boolean): List[A] =
  as match
    case Cons(h, t) if f(h) => dropWhile(t)(f)
    case _                  => as

val xs: List[Int] = List(1, 2, 3, 4, 5)
val ex1 = dropWhile(xs)(x => x < 4)
```

- `dropWhile(xs)` determines that `A` is `Int`.
- Scala uses this type information when it checks the next argument list.
- Type information therefore flows naturally from left to right at the call site.
