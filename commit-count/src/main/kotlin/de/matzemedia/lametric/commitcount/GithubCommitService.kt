package de.matzemedia.lametric.commitcount

import de.matzemedia.lametric.commitcount.config.WebClientConfiguration.Companion.GITHUB_WEB_CLIENT
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.core.publisher.toMono


@Service
class GithubCommitService(
    @Qualifier(GITHUB_WEB_CLIENT)
    private val webClient: WebClient
) {

    companion object {
        private val LOG = LoggerFactory.getLogger(GithubCommitService::class.java)
    }

    fun getCommitCount(username: String, repo: String): Long? {
        val linkHeaderValue = webClient.get()
            .uri("/repos/$username/$repo/commits?per_page=1")
            .exchangeToMono { it.headers().toMono() }
            .block()
            ?.asHttpHeaders()
            ?.get(HttpHeaders.LINK)

        return if (linkHeaderValue != null && linkHeaderValue.size > 0) {
            LOG.debug(linkHeaderValue[0])
            LinkHeaderExtractor.extractTotalPageSize(linkHeaderValue[0])
        } else {
            null
        }
    }

}
