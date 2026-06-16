# High Order Functions

* Functions are values.
* High order functions (HOFs) accept other functions as arguments or return them.
* Use short parameter names like `f`, `g`, and `h` for parameters to a higher-order function.
    * Since HOFs are generic, they only know their arguments' types, not their specific behavior.

# Polymorphic Functions

* Especially when writing HOFs, we want to write code that works for any given type.
* Polymorphism in FP differs from polymorphism in object-oriented languages.
    * It does not rely on subtyping or inheritance.

# Function Literals

* A function literal defines an object with an `apply` method.
* Scala lets objects with `apply` be called like functions.
    * `lessThan(10, 20)` is syntax for `lessThan.apply(10, 20)`.
* Function types are ordinary traits such as `Function1`, `Function2`, and `Function3`.
    * `(Int, Int) => Boolean` is short for `Function2[Int, Int, Boolean]`.
* Since functions are ordinary objects, they are first-class values.

```scala
val lessThan = (a: Int, b: Int) => a < b

lessThan(10, 20)
lessThan.apply(10, 20)
```

# Polymorphic Functions Restrict Implementations

* If a function is polymorphic in type `A`, it cannot use type-specific operations on `A`.
* It can only use operations provided through its parameters.
* This restricts the set of useful implementations.
* For example, a pure implementation of `partial1` has one meaningful shape:

```scala
def partial1[A, B, C](a: A, f: (A, B) => C): B => C =
  b => f(a, b)
```
