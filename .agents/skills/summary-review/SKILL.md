---
name: summary-review
description: Revise, review, polish, or enrich a Markdown summary, note, chapter summary, or study memo, especially for functional programming or Scala content. Use when the user wants awkward English fixed, incorrect content corrected, or FP/Scala best-practice context added. Provide a concise, learning-oriented report that explains why changed English was awkward, unnatural, or grammatically incorrect.
---

# Summary Review

## Goal

Revise the given Markdown summary in two steps.

- Keep sentences short.
- Prefer bullet points.
- Strictly preserve the author's intent, meaning, and nuance.
- Improve clarity and flow without changing the core message or removing important emphasis.
- Explain meaningful English corrections so the author can learn from them.
- Add functional programming or Scala context only when it truly helps.

## Inputs

- A Markdown document from the user.
- Optional focus areas, such as chapter, topic, or exercise context.
- Optional constraints, such as strict length or no added examples.

## Workflow

### Step 1: English Polish

- Fix awkward English, grammatical errors, and unnatural phrasing.
- Make sentences concise and direct while preserving the exact meaning, logical implications, and tone of the original text.
- Do not alter the core message, developer intent, or prescriptive nuances (e.g., changing "we want to write code..." to passive facts).
- Prefer active voice when it aligns with the author's intent.
- Remove redundant wording.
- Keep terminology consistent.
- Preserve Markdown structure unless a small restructure improves readability.
- Record each meaningful correction with the original text, revised text, issue, and reusable learning point.
- Distinguish grammatical errors from naturalness, clarity, concision, and style improvements.
- Do not describe a stylistic preference as a grammar rule.
- Group repeated instances of the same issue instead of reporting every occurrence.
- Omit trivial formatting or punctuation changes unless explaining them has learning value.

### Step 2: Technical Review and Enrichment

- Check whether the content is technically correct.
- Correct wrong or misleading statements.
- Add short explanations for missing key ideas.
- Add Scala or functional programming best practices when relevant.
- Add theoretical context when it improves understanding.
- Avoid broad rewrites that change the author's scope.

## Output Format

Return a review report.

### Review Report

- Use `templates/review-report.md`.
- Fill every relevant section.
- Keep the report concise.
- Group changes by workflow step.
- Mention only meaningful edits.
- In Step 1, quote only the shortest phrase or sentence needed to explain each correction.
- If no English changes are needed, state that explicitly instead of adding an empty correction table.

## Style Rules

- Write project artifacts in English.
- Use simple, direct sentences.
- Prefer bullets over long paragraphs.
- Keep headings clear.
- Avoid decorative formatting.
- Do not over-explain basic Scala syntax.
- Do not add claims that are not supported by the source text or well-known FP/Scala practice.

## Scala and FP Guidance

- Prefer referential transparency as the central explanation for pure functions.
- Explain immutability as a default design choice, not a special trick.
- Distinguish values, effects, and effect descriptions.
- Prefer total functions when practical.
- Mention pattern matching, algebraic data types, higher-order functions, recursion, and type-driven design only when relevant.
- For Scala examples, prefer small expressions over full applications.

## Final Check

- The revised Markdown is readable on its own.
- The report clearly separates Step 1 and Step 2.
- Every reported English change explains what was wrong or awkward and gives a reusable learning point.
- Added content is accurate and short.
- The original meaning, intent, and key nuances are fully preserved without distortion.
- The final answer does not exceed the user's requested scope.
