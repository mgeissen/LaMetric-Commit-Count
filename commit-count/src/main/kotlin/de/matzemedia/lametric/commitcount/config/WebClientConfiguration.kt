package de.matzemedia.lametric.commitcount.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class WebClientConfiguration {

    companion object {
        const val GITHUB_WEB_CLIENT = "githubWebClient"

        private const val TIMEOUT_MS = 2000L;
    }

    @Bean(GITHUB_WEB_CLIENT)
    fun webClient(@Value("\${lametric-commit-count.github-api-base-url}") githubApiBaseUrl: String): WebClient {
        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_MS.toInt())
            .responseTimeout(Duration.ofMillis(TIMEOUT_MS))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(TIMEOUT_MS, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(TIMEOUT_MS, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .baseUrl(githubApiBaseUrl)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }


}
