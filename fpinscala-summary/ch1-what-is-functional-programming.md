# Pure functions

Pure functions are functions without side effects.

- Functional programming restricts how we write programs, but not what programs we can express.
- We can express all of our programs without side effects, even programs that perform I/O, handle errors, and modify data.

# Simple Example - Buying a Coffee

## With side effects
```scala
class Cafe {
    def buyCoffee(cc: CreditCard): Coffee = {
        val cup = new Coffee()
        cc.charge(cup.price)
        cup
    }
}
```

Problems:
1. Testability
2. `CreditCard` knows too much, such as how to contact the credit card company and persist transaction records in the internal system.

## Better modularity, but still with side effects
```scala
class Cafe {
    def buyCoffee(cc: CreditCard, p: Payments): Coffee = {
        val cup = new Coffee()
        p.charge(cc, cup.price)
        cup
    }
}
```

Problems:
1. Testability - We need to mock `Payments`, but we might also want to test whether the internal state of `Payments` is properly mutated. This requires additional tests for the mock.
2. `buyCoffee` is hard to reuse because it handles complicated internal logic. For example, what if we want to buy 12 coffees at once? Calling it 12 times would burden both the credit card company and the internal system.

## Functional solution
```scala
class Cafe {
    def buyCoffee(cc: CreditCard): (Coffee, Charge) {
        val cup = new Coffee()
        (cup, Chard(cc, cup.price))
    }
}
```

- This separates the concern of *creating* a charge from the *processing* or *interpretation* of the charge.

How to implement `buyCoffees` using this functional solution:

```scala
def buyCoffees(cc: Creditcard, n: Int): (List[Coffee], Charge) = {
    val purchases: List[(Coffee, Charge)] = List.fill(n)(buyCoffee(cc))
    val (coffees, charges) = purchases.unzip
    (coffees, charges.reduce((c1, c2) => c1.combine(c2)))
}
```

# What is a Pure Function?
* Referential Transparency
    * A property of an expression that allows it to be replaced with its evaluated result without changing the program's behavior.
* A function is *pure* if calling it with referentially transparent (RT) arguments is also RT.
* Implies: Everything a function *does* is represented by the value it *returns*.

# Substitution Rules
* When reasoning about a program, we can replace any referentially transparent expression with its evaluated result.
* This makes reasoning about the program much easier:
    * Understanding requires only local reasoning.
    * We do not need to mentally track state changes that may occur before or after our function’s execution; we simply look at the function’s definition and substitute the arguments into its body.
* The program becomes much more modular because it consists of components that can be understood and reused independently of the whole.