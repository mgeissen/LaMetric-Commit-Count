package de.matzemedia.lametric.commitcount

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestApplication::class])
class GithubCommitServiceDebugTest {

    @Autowired
    private lateinit var githubCommitService: GithubCommitService

    @Test
    @Disabled("Only for local testing against real github api")
    fun `should fetch github commit count`() {
        print(githubCommitService.getCommitCount("mgeissen", "LaMetric-Commit-Count"))
    }
}
