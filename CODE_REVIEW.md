# Code Review – StudentRegistration.java (original)

**Reviewer:** Student B  |  **Branch reviewed:** `main` @ initial commit
**Method:** read the code, then ran it. Observed output of the original program:

```
Student Added!  Name:      Age: -5   Grade: 150.0     <- invalid record accepted
Student Added!  Name: John Age: 20   Grade: 85.0
Student Added!  Name: John Age: 20   Grade: 85.0      <- duplicate accepted
(blank line)
John
John                                                  <- age/grade were never stored
```

## Findings (14)

| # | Category | Severity | Location | Finding | Recommendation |
|---|----------|----------|----------|---------|----------------|
| 1 | Naming | Low | `add(String n, int a, double g)` | Parameters `n`, `a`, `g` are cryptic. Method names `add` / `display` don't say *what* is added or displayed. | `registerStudent(name, age, grade)`, `displayStudents()`. |
| 2 | Logical error | **High** | `students.add(n)` | Only the **name** is stored. Age and grade are printed once and then lost, so the system cannot actually "register" a student. | Create a `Student` class holding name, age, grade; store `Student` objects. |
| 3 | Logical error | **High** | `System.out.println("Student Added!")` | The success message is printed unconditionally, even for clearly invalid data (see #5–#7). The program reports success when it failed. | Print success only after validation passes and the student is stored. |
| 4 | Input validation | **High** | `add("", -5, 150)` | Empty name accepted (also `null`, whitespace-only, 10,000-char names). | Reject null/blank names; enforce a length limit. |
| 5 | Input validation | **High** | `add("", -5, 150)` | Negative age (-5) accepted. | Enforce a realistic range (e.g. 5–120). |
| 6 | Input validation | **High** | `add("", -5, 150)` | Grade 150 accepted; `NaN`/`Infinity` would also pass a naive check. | Enforce 0–100 and reject non-finite values. |
| 7 | Logical error | Medium | `add("John", 20, 85)` ×2 | Exact duplicates are registered twice; `display()` shows "John" twice. | Define a duplicate rule (same normalised name + age) and reject it. |
| 8 | Exception handling | Medium | whole class | No exceptions are thrown or caught. Callers can't tell whether `add` succeeded; `add(null, …)` silently stores `null`. | Throw `IllegalArgumentException` / a duplicate exception; catch and report in `main`. |
| 9 | Duplicate code | Low | 4× `System.out.println` in `add`; 3× `add(...)` in `main` | Output formatting is hard-coded and repeated; calls in `main` repeat the same pattern. | Move formatting into `Student.toString()`; loop over sample data. |
| 10 | Security | Medium | `"Name: " + n` | Unsanitised input is echoed to the console/logs. A name containing `\n` can forge extra log lines (log injection); no allow-list of characters. | Allow-list name characters (letters, space, `.`, `'`, `-`) and cap length. |
| 11 | Maintainability | **High** | `static ArrayList<String> students` | Mutable **global static** state: cannot be reset between tests, not thread-safe, can be changed by any class (package-private). | Make the list a `private final` instance field; expose an unmodifiable view. |
| 12 | Maintainability | Medium | whole class | One class does storage + validation + printing + demo data in `main`. Hard to unit test (writes straight to `System.out`). | Separate `Student` (data/validation) from `StudentRegistration` (service); keep I/O in `main`. |
| 13 | Code formatting | Low | whole file | Stray blank lines after `{`, no Javadoc/comments, magic numbers, declares `ArrayList` instead of `List`. | Tidy layout, add Javadoc, use named constants, program to the `List` interface. |
| 14 | Logical error | Low | `display()` | Prints names only and prints nothing at all for an empty registry. | Print full record, numbered, and "No students registered." when empty. |

## Summary
Critical problems: #2, #3, #4–#6, #11. The program accepts invalid data, loses most of it,
and still reports success. See `PR_REVIEW.md` for the review of the fix.
