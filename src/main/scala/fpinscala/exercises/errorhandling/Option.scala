package fpinscala.exercises.errorhandling

// Hide std library `Option` since we are writing our own in this chapter
import scala.{Option as _, Some as _, None as _}

enum Option[+A]:
  case Some(get: A)
  case None

  def map[B](f: A => B): Option[B] = this match
    case Some(a) => Some(f(a))
    case None    => None

  def getOrElse[B >: A](default: => B): B = this match
    case Some(a) => a
    case None    => default

  // limit: don't use pattern matching
  def flatMap[B](f: A => Option[B]): Option[B] = this.map(f).getOrElse(None)

  // limit: don't use pattern matching
  def orElse[B >: A](ob: => Option[B]): Option[B] =
    this.map(Some(_)).getOrElse(ob)

  // limit: don't use pattern matching
  def filter(f: A => Boolean): Option[A] =
    this.flatMap(a => if f(a) then Some(a) else None)

object Option:

  def failingFn(i: Int): Int =
    val y: Int =
      throw new Exception(
        "fail!"
      ) // `val y: Int = ...` declares `y` as having type `Int`, and sets it equal to the right hand side of the `=`.
    try
      val x = 42 + 5
      x + y
    catch
      case e: Exception =>
        43 // A `catch` block is just a pattern matching block like the ones we've seen. `case e: Exception` is a pattern that matches any `Exception`, and it binds this value to the identifier `e`. The match returns the value 43.

  def failingFn2(i: Int): Int =
    try
      val x = 42 + 5
      x + ((throw new Exception(
        "fail!"
      )): Int) // A thrown Exception can be given any type; here we're annotating it with the type `Int`
    catch case e: Exception => 43

  def mean(xs: Seq[Double]): Option[Double] =
    if xs.isEmpty then None
    else Some(xs.sum / xs.length)

  // answer implementation: mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))
  // => Before implementing a calculation, describe it using existing function names:
  // “variance is the mean of squared deviations from the mean.”
  def variance(xs: Seq[Double]): Option[Double] =
    mean(xs)
      .map(m => xs.foldLeft(0.0)((acc, x) => acc + math.pow(x - m, 2)))
      .map(_ / xs.size)

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    // first try:
    //     a.map(a => (x => f(a, x))).flatMap(g => b.map(g(_)))
    a.flatMap(aa => b.map(bb => f(aa, bb)))

  def sequence[A](as: List[Option[A]]): Option[List[A]] =
    as.foldRight[Option[List[A]]](Some(Nil))((a, acc) =>
      acc.flatMap(accVal => a.map(aa => aa :: accVal))
    )

  /* Here's an explicit recursive version: */
  def sequence_1[A](as: List[Option[A]]): Option[List[A]] =
    as match
      case Nil    => Some(Nil)
      case h :: t => h.flatMap(hh => sequence(t).map(hh :: _))

  /*
  It can also be implemented using `foldRight` and `map2`. The type annotation on `foldRight` is needed here; otherwise
  Scala wrongly infers the result type of the fold as `Some[Nil.type]` and reports a type error (try it!). This is an
  unfortunate consequence of Scala using subtyping to encode algebraic data types.
   */
  def sequence_2[A](as: List[Option[A]]): Option[List[A]] =
    as.foldRight[Option[List[A]]](Some(Nil))((a, acc) => map2(a, acc)(_ :: _))

  def traverse[A, B](as: List[A])(f: A => Option[B]): Option[List[B]] = ???
