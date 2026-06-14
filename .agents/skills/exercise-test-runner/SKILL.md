---
name: exercise-test-runner
description: Run fpinscala exercise tests by chapter or by chapter and exercise number. Use when the user asks to grade, check, test, or run tests for a Functional Programming in Scala chapter or exercise, such as "chapter 2", "chapter 2 exercise 1", "run chapter n tests", or "grade exercise n.m". This skill runs existing exercise tests only and must not inspect answer keys or answer implementations.
---

# Exercise Test Runner

## Overview

Run the narrowest available test command for the requested fpinscala chapter or exercise. Treat "grade" as "run the repository's tests"; do not compare against answers manually.

## Safety Boundary

- Do not read `answerkey/`.
- Do not read `src/main/scala/fpinscala/answers/`.
- Do not infer correctness from answer files.
- Read exercise source comments and exercise test suites only to map the requested exercise to tests.
- Report test results, not a final answer implementation.

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
   - `rtk scala-cli test . -- 'fpinscala.exercises.<folder>.*'`
5. For exercise-specific requests, find candidate tests:
   - Prefer test names containing `Exercise n.m`, `Exercises n.m`, or `Exercise m`.
   - If tests are not numbered, find `Exercise m` comments in the exercise source and search the suite files for the nearby function, object, or method names.
   - If one exercise has multiple tests, run all matching tests or the narrowest wildcard that covers them.
   - If mapping is genuinely ambiguous, show the candidate tests and ask one concise clarification question.
6. Run tests from the repository root with `rtk scala-cli test`.
7. If Scala CLI fails because Bloop or cache directories outside the sandbox are inaccessible, rerun the same command with escalation approval.
8. Summarize the command and result. Include failing test names and a brief error summary, without exposing generated counterexamples or full assertion payloads. Do not provide the exercise solution.

## Commands

Use fully qualified MUnit test filters when possible:

```bash
rtk scala-cli test . -- 'fpinscala.exercises.<folder>.<SuiteClass>.<test name>'
```

Use a package-level filter for chapter-only requests:

```bash
rtk scala-cli test . -- 'fpinscala.exercises.<folder>.*'
```

Use a wildcard only when several tests intentionally cover the same exercise:

```bash
rtk scala-cli test . -- 'fpinscala.exercises.<folder>.<SuiteClass>.<shared test prefix>*'
```

For exercise-specific requests, use a package-level filter only when the requested exercise cannot be separated more narrowly:

```bash
rtk scala-cli test . -- 'fpinscala.exercises.<folder>.*'
```

## Reporting

Keep the response concise:

- State which test command ran.
- Say whether it passed or failed.
- If a test fails, report the failed test name and a short failure summary, but do not include generated counterexamples, concrete failing inputs, or full assertion payloads.
- If no test exists for the requested exercise, say that clearly and list the closest suite/test names found.
