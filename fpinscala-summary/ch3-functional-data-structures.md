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
