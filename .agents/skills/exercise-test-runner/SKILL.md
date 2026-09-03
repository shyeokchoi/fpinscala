---
name: exercise-test-runner
description: Run fpinscala exercise tests by chapter or by chapter and exercise number. Use when the user asks to grade, check, test, or run tests for a Functional Programming in Scala chapter or exercise, such as "chapter 2", "chapter 2 exercise 1", "run chapter n tests", or "grade exercise n.m". This skill runs existing exercise tests only and must not inspect answer keys or answer implementations.
---

# Exercise Test Runner

## Overview

Run the narrowest available test command for the requested fpinscala chapter or exercise. Treat "grade" as "run the repository's tests"; do not compare against answers manually. Report the exact command used.

## Safety Boundary

- Do not infer correctness from answer files.
- Read exercise source comments and exercise test suites only to map the requested exercise to tests.
- Report only pass/fail status; never explain or hint at why a failure happened.

## Chapter Map

Use this map from chapter number to exercise package folder:

| Chapter | Exercise Package |
| --- | --- |
| 2 | `gettingstarted` |
| 3 | `datastructures` |
| 4 | `errorhandling` |
| 5 | `laziness` |
| 6 | `state` |
| 7 | `parallelism` |
| 8 | `testing` |
| 9 | `parsing` |
| 10 | `monoids` |
| 11 | `monads` |
| 12 | `applicative` |
| 13 | `iomonad` |
| 14 | `localeffects` |
| 15 | `streamingio` |

## Workflow

1. Parse the request:
   - If the request gives only chapter `n`, run every test under that chapter's exercise package.
   - If the request gives both chapter `n` and exercise `m`, run the narrowest available tests for exercise `m` within chapter `n`.
   - If the request gives exercise `n.m`, treat `n` as the chapter and `m` as the exercise number within that chapter.
2. Map chapter `n` to its folder with the chapter map.
3. Inspect only these paths:
   - `src/main/scala/fpinscala/exercises/<folder>/`
   - `src/test/scala/fpinscala/exercises/<folder>/`
4. For chapter-only requests, run the package-level filter:
   - `scala-cli test . -- 'fpinscala.exercises.<folder>.*'`
5. For exercise-specific requests, find candidate tests:
   - Prefer test names containing `Exercise n.m`, `Exercises n.m`, or `Exercise m`.
   - If tests are not numbered, find `Exercise m` comments in the exercise source and search the suite files for the nearby function, object, or method names.
   - If one exercise has multiple tests, run all matching tests or the narrowest wildcard that covers them.
   - If mapping is genuinely ambiguous, ask one concise clarification question without listing candidate test names.
6. Run tests from the repository root with `scala-cli test` in an environment that can access Scala CLI's Bloop cache. Request the required cache access before running the command when necessary.
7. Summarize only the result and the exact `scala-cli test` command used. If the run fails, say only that the requested test run failed and provide the command. Do not include failing test names, error summaries, exception types, line numbers, stack traces, assertion details, generated counterexamples, concrete failing inputs, or any interpretation of the likely cause. Do not provide the exercise solution.

## Commands

Use fully qualified MUnit test filters when possible:

```bash
scala-cli test . -- 'fpinscala.exercises.<folder>.<SuiteClass>.<test name>'
```

Use a package-level filter for chapter-only requests:

```bash
scala-cli test . -- 'fpinscala.exercises.<folder>.*'
```

Use a wildcard only when several tests intentionally cover the same exercise:

```bash
scala-cli test . -- 'fpinscala.exercises.<folder>.<SuiteClass>.<shared test prefix>*'
```

For exercise-specific requests, use a package-level filter only when the requested exercise cannot be separated more narrowly:

```bash
scala-cli test . -- 'fpinscala.exercises.<folder>.*'
```

## Reporting

Keep the response concise:

- For a passing run, say that the requested test run passed.
- For a failing run, say only that the requested test run failed.
- Include the exact `scala-cli test` command used for every passing or failing run.
- Do not state failing test names, suite names, function names from failures, error messages, exception types, stack traces, line numbers, assertion payloads, generated counterexamples, concrete failing inputs, or why the failure likely happened.
- If no test exists for the requested exercise, say only that no matching test exists.
