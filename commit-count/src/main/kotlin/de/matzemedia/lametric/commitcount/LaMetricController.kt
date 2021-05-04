package de.matzemedia.lametric.commitcount

import de.matzemedia.lametric.commitcount.model.ErrorStatusCodes.MALFORMED_REPO
import de.matzemedia.lametric.commitcount.model.ErrorStatusCodes.REPO_NOT_FOUND
import de.matzemedia.lametric.commitcount.model.LaMetricResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LaMetricController(
    private val githubCommitService: GithubCommitService
) {

    @GetMapping("\${lametric-commit-count.path}")
    fun getCount(@RequestParam(required = false) repo: String?): LaMetricResponse {
        if (repo == null) {
            return LaMetricResponse.default()
        }

        val split = repo.split("/")
        if (split.size != 2) {
            return LaMetricResponse.withError(MALFORMED_REPO)
        }
        val username = split[0]
        val repository = split[1]

        val commitCount = githubCommitService.getCommitCount(username, repository)
        return if (commitCount != null) {
            LaMetricResponse.withCount(commitCount)
        } else {
            LaMetricResponse.withError(REPO_NOT_FOUND)
        }
    }

}
