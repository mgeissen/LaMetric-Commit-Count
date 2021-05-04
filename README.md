# LaMetric Github Commit Count

Source Code of published Github Commit Count LaMetric App:
[Go to LaMetric-App-Store](https://apps.lametric.com/apps/github_commit_count/2376?product=market&market=en-US)

![LaMetric Commit Count Screen](lametric-screen.png)


## Usage
The library is testet with spring-boot `2.4.5` and Kotlin `1.5.0`.

build.gradle.kts:
```
repositories {    
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/Turnonio/base-kotlin-lib")
        credentials {
            username = "<username>"
            password = "<github token>"
        }
    }
}

dependencies {
	implementation("de.matzemedia.lametric:lametric-commit-count:1.0.0")
}
```

application.yaml:

```
lametric-commit-count:
  path: /lametric/commit-count # or your favorite endpoint path
  github-api-base-url: https://api.github.com
```
Add package to spring scan in your SpringApplication to register Controller:
```
@SpringBootApplication(scanBasePackages = ["de.matzemedia.lametric.commitcount"])
```

