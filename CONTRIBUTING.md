# Contributing guidelines

## When submitting pull requests

* Explain your thinking in why a change or addition is needed.
    * Is it a requested change or feature?

* Split up multiple unrelated changes in multiple pull requests.

* If your PR is for a issue that has not been reported please create an issue and the PR at the same time and reference it in your PR.

## Formatting

### Follow the current syntax design

* Indent type: Spaces

* Indent size: 4

* Opening curly braces `{` at the end of the same line as the statement/condition.

* If, while, do, for, switch, etc. statements should have a space between the statement and the opening parenthesis `(`.

* Methods should **not** have a space between the method name and the opening parenthesis `(`.

* No hungarian notation. ie. `IInterface` should be `Interface`.

## General guidelines

* Don't force a programming style. Use object-oriented, functional, data oriented, etc., where it's suitable.

* Use descriptive names for variables.

* Use comments if not very obvious what your code is doing.

* If using the logger functions, be sensible, only call it if something of importance has changed.

* Benchmark your code and look for alternatives if they cause a noticeable negative impact.

## License

* Code submitted to this project shall fall under the same license as the project.

* Any code taken from another existing project should maintain the license that comes it AND follow the terms of that license.


## Contact

If you have any questions, feel free to join our Discord at [discord.gg/terrarium](https://discord.gg/terrarium)