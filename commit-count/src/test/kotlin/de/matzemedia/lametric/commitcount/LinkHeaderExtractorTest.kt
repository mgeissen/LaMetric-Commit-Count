package de.matzemedia.lametric.commitcount

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LinkHeaderExtractorTest {

    @ParameterizedTest
    @MethodSource("provideLinkHeaderWithExpectedPageCount")
    fun `should extract total page count`(linkHeaderValue: String, expectedPageCount: Long?) {
        // when
        val extractedValue = LinkHeaderExtractor.extractTotalPageSize(linkHeaderValue)

        // then
        extractedValue shouldBe expectedPageCount
    }

    companion object {
        @JvmStatic
        fun provideLinkHeaderWithExpectedPageCount(): Stream<Arguments> = Stream.of(
            of(
                "<https://api.github.com/repositories/92684406/commits?per_page=1&page=2>; rel=\"next\", <https://api.github.com/repositories/92684406/commits?per_page=1&page=41>; rel=\"last\"",
                41L
            ),
            of(
                "<https://api.github.com/repositories/92684406/commits?per_page=1&page=20>; rel=\"last\"",
                20L
            ),
            of(
                "<https://api.github.com/repositories/92684406/commits?per_page=1&page=2>; rel=\"next\"",
                null
            ),
            of(
                "<https://api.github.com/repositories/92684406/commits?per_page=1>; rel=\"last\"",
                null
            )
        )
    }

}
