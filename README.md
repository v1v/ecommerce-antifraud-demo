Add readme.md


### Enable hooks

```bash
cp post-hook.sh .git/hooks/pre-push
chmod 755 .git/hooks/pre-push
```

### Baseline

The original state of the demo is stored in the `baseline-demo`

You can reset your branch to the original state with the below command:

```bash
git reset --hard baseline-demo
```

### Demo

The demo relies on the `demo` branch, so you can commit and push your changes in order
to kick off builds in the CI.

#### First commit

* Edit `pom.xml` to bump the version to `0.0.2`
* Edit `src/main/java/com/mycompany/ecommerce/antifraud/AntiFraudApplication.java` to say `bye`

#### Second commit

* Edit `pom.xml` file to bump the DoS Snyk vulnerability

#### Third commit

* Edit `pom.xml` to bump the version to `0.0.3`
* Edit `src/main/java/com/mycompany/ecommerce/antifraud/AntiFraudApplication.java` to say fail always if version `0.0.3`
* Edit `versions.properties` to `0.0.3` and `0.0.2`

#### Forth commit

* Edit `Jenkinsfile`, to hack the GitHub token.
