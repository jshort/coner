Contributing/Development
===
There are many different ways to help contribute to the Coner project.

* Improving or enhancing our documentation
* Fixing open issues listed in the [issue tracker](https://github.com/carltonwhitehead/coner/issues?state=open)
* Adding new features to the Coner codebase

Guidelines
===
When submitting a pull request, please make sure to fork the repository and create a
separate branch for your feature or fix for an issue.

All contributions are welcome to be submitted for review for inclusion, but before
they will be accepted, we ask that you follow these simple guidelines:

Code style
---
When submitting code, please make every effort to follow existing conventions and
style in order to keep the code as readable as possible. We realize that the style
used in Coner might be different that what is used in your projects, but in the end
 it makes it easier to merge changes and maintain in the future.

Prior to committing, run checkstyle to confirm your changes did not introduce additional checkstyle errors.

```
$ mvn checkstyle:check
```

Testing
---
We kindly ask that all new features and fixes for an issue should include any unit tests.
Even if it is small improvement, adding a unit test will help to ensure no regressions or the
issue is not re-introduced. If you need help with writing a test for your feature, please
don't be shy and ask!

Documentation
---
Up-to-date documentation makes all our lives easier. If you are adding a new feature,
enhancing an existing feature, or fixing an issue, please add or modify the documentation
as needed and include it with your pull request.

New Features
===
If you would like to implement a new feature, please raise an issue before sending a
pull request so the feature can be discussed. **We appreciate the effort and want
to avoid a situation where a contribution requires extensive rework on either side,
it sits in the queue for a long time, or cannot be accepted at all.**