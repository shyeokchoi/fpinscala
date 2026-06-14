# High Order Functions

* Functions are values.
* High order functions (HOFs) accept other functions as arguments or return them.
* Use short parameter names like `f`, `g`, and `h` for parameters to a higher-order function.
    * Since HOFs are generic, they only know their arguments' types, not their specific behavior.

# Polymorphic Functions

* Especially when writing HOFs, we want to write code that works for any given type.
* Polymorphism in FP differs from polymorphism in object-oriented languages.
    * It does not rely on subtyping or inheritance.