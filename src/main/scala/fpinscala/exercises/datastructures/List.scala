package fpinscala.exercises.datastructures

/** `List` data type, parameterized on a type, `A`. */
enum List[+A]:
  /** A `List` data constructor representing the empty list. */
  case Nil

  /** Another data constructor, representing nonempty lists. Note that `tail` is
    * another `List[A]`, which may be `Nil` or another `Cons`.
    */
  case Cons(head: A, tail: List[A])

object List: // `List` companion object. Contains functions for creating and working with lists.
  def sum(ints: List[Int]): Int =
    ints match // A function that uses pattern matching to add up a list of integers
      case Nil => 0 // The sum of the empty list is 0.
      case Cons(x, xs) =>
        x + sum(
          xs
        ) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.

  def product(doubles: List[Double]): Double = doubles match
    case Nil          => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs)  => x * product(xs)

  def apply[A](as: A*): List[A] = // Variadic function syntax
    if as.isEmpty then Nil
    else Cons(as.head, apply(as.tail*))

  @annotation.nowarn // Scala gives a hint here via a warning, so let's disable that
  val result = List(1, 2, 3, 4, 5) match
    case Cons(x, Cons(2, Cons(4, _)))          => x
    case Nil                                   => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t)                            => h + sum(t)
    case _                                     => 101

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match
      case Nil        => a2
      case Cons(h, t) => Cons(h, append(t, a2))

  def foldRight[A, B](
      as: List[A],
      acc: B,
      f: (A, B) => B
  ): B = // Utility functions
    as match
      case Nil         => acc
      case Cons(x, xs) => f(x, foldRight(xs, acc, f))

  def sumViaFoldRight(ns: List[Int]): Int =
    foldRight(ns, 0, (x, y) => x + y)

  def productViaFoldRight(ns: List[Double]): Double =
    foldRight(
      ns,
      1.0,
      _ * _
    ) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar

  def tail[A](l: List[A]): List[A] = l match {
    case Cons(h, xs) => xs
    case Nil         => throw Exception()
  }

  def setHead[A](l: List[A], h: A): List[A] = Cons(h, tail(l))

  @annotation.tailrec
  def drop[A](l: List[A], n: Int): List[A] =
    if n <= 0 then l
    else
      l match
        case Cons(_, xs) => drop(xs, n - 1)
        case Nil         => Nil

  @annotation.tailrec
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match
      case Cons(h, xs) if f(h) => dropWhile(xs, f)
      case _                   => l

  // def init[A](l: List[A]): List[A] =
  // l match
  // case Nil                    => throw Exception()
  // case Cons(_, Nil)           => Nil
  // case Cons(h1, Cons(h2, xs)) => Cons(h1, init(Cons(h2, xs)))

  // tail recursive version
  def init[A](l: List[A]): List[A] =
    @annotation.tailrec
    def go(remaining: List[A], acc: List[A]): List[A] =
      remaining match
        case Nil          => throw Exception()
        case Cons(_, Nil) => acc
        case Cons(h, rem) => go(rem, Cons(h, acc))

    val reversed = go(l, Nil)

    @annotation.tailrec
    def reverse(l: List[A], acc: List[A]): List[A] =
      l match
        case Nil         => acc
        case Cons(h, xs) => reverse(xs, Cons(h, acc))

    reverse(reversed, Nil)

  def length[A](l: List[A]): Int = foldRight(l, 0, (_, b) => 1 + b)

  @annotation.tailrec
  def foldLeft[A, B](l: List[A], acc: B, f: (B, A) => B): B =
    l match
      case Nil         => acc
      case Cons(h, xs) => foldLeft(xs, f(acc, h), f)

  def sumViaFoldLeft(ns: List[Int]): Int =
    foldLeft(ns, 0, (b, a) => b + a)

  def productViaFoldLeft(ns: List[Double]): Double =
    foldLeft(ns, 1.0, (b, a) => b * a)

  def lengthViaFoldLeft[A](l: List[A]): Int =
    foldLeft(l, 0, (b, _) => 1 + b)

  def reverse[A](l: List[A]): List[A] =
    foldLeft[A, List[A]](
      l,
      Nil,
      (acc, x) => Cons(x, acc)
    )

  def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] =
    foldRight(l, r, (x, acc) => Cons(x, acc))

  def concat[A](l: List[List[A]]): List[A] =
    foldRight(l, Nil: List[A], append)

  def incrementEach(l: List[Int]): List[Int] =
    foldRight(l, Nil: List[Int], (x, acc) => Cons(x + 1, acc))

  def doubleToString(l: List[Double]): List[String] =
    foldRight(l, Nil: List[String], (x, acc) => Cons(x.toString, acc))

  def map[A, B](l: List[A], f: A => B): List[B] =
    foldRight(l, Nil: List[B], (x, acc) => Cons(f(x), acc))

  def filter[A](as: List[A], f: A => Boolean): List[A] =
    foldRight(as, Nil: List[A], (x, acc) => if (f(x)) Cons(x, acc) else acc)

  def flatMap[A, B](as: List[A], f: A => List[B]): List[B] = ???

  def filterViaFlatMap[A](as: List[A], f: A => Boolean): List[A] = ???

  def addPairwise(a: List[Int], b: List[Int]): List[Int] = ???

  // def zipWith - TODO determine signature

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = ???
