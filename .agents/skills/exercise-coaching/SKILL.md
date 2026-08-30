---
name: exercise-coaching
description: Provide coaching and hints for fpinscala programming exercises. Use when the user shares a solution for review, asks for hints or feedback, or asks about a chapter/exercise such as "chapter 2 exercise 1", "exercise 2.1", or "review chapter 2 exercise 1". When chapter and exercise numbers are known, read the matching answerkey hint file and include it in a separate section.
---

# Exercise Coaching

## Boundary

Coach the user toward their own solution. Do not patch exercise code, rewrite the full solution, or provide `answerkey/*.answer.md` content unless the user explicitly asks for the answer. For normal hint/review requests, use `answerkey/<folder>/<NN>.hint.md`.

## Chapter Map

Use this map from chapter number to exercise package and answerkey folder:

| Chapter | Folder |
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

1. Determine the exercise:
   - If the user gives `chapter n exercise m` or `exercise n.m`, map `n` with the table and zero-pad `m` for answerkey files, for example `01.hint.md`.
   - If the user only shares code or a function name, infer the exercise from `src/main/scala/fpinscala/exercises/` and nearby comments when possible.
   - If the exercise remains ambiguous, ask one concise clarification question.
2. Read only the relevant exercise source and the user's submitted code.
3. When chapter and exercise numbers are known, read `answerkey/<folder>/<NN>.hint.md` and include its content under `Answer-Key Hint`.
4. Analyze the user's solution for behavior, edge cases, Scala style, FP style, stack safety, purity, and unnecessary complexity.
5. Give progressive hints without revealing a complete replacement implementation.

## Hint Format

Use this structure for hint or review responses:

```markdown
**Overall**
A brief assessment of whether the current direction is sound.

**Answer-Key Hint**
Source: `answerkey/<folder>/<NN>.hint.md`

<Include the contents of the repository hint file. If the hint file is missing, say so.>

**Agent Hint**
- The most important observation about the user's code.
- One or two focused hints based on the code and the exercise requirements.
- Any edge cases or properties worth checking.

**Next Step**
<This section is optional. Include it only when there is a clear, meaningful next step that deepens the user's understanding.
Do not force a next step when none is needed.
Do not mention other exercises, especially upcoming ones, because that may reveal hints.>
```
