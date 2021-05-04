package de.matzemedia.lametric.commitcount

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class LaMetricControllerTest {

    private lateinit var laMetricController: LaMetricController

    @MockK
    private lateinit var githubCommitService: GithubCommitService

    @BeforeEach
    fun setUp() {
        laMetricController = LaMetricController(githubCommitService)
    }

    @Test
    fun `should return lametric response with commit count`() {
        // given
        val givenCommitCount = 42L
        every { githubCommitService.getCommitCount("someUser", "someRepo") } returns givenCommitCount

        // when
        val response = laMetricController.getCount("someUser/someRepo")

        // then
        response.frames shouldHaveSize 1
        response.frames.first().text shouldBe "$givenCommitCount"
        response.frames.first().icon shouldBe "i2799"
    }

    @Test
    fun `should return lametric response with error code malformed`() {
        // when
        val response = laMetricController.getCount("someInvalidStuff")

        // then
        response.frames shouldHaveSize 1
        response.frames.first().text shouldBe "-1"
    }

    @Test
    fun `should return lametric response with not found error code`() {
        // given
        every { githubCommitService.getCommitCount("someUser", "someRepo") } returns null

        // when
        val response = laMetricController.getCount("someUser/someRepo")

        // then
        response.frames shouldHaveSize 1
        response.frames.first().text shouldBe "-2"
    }
}
