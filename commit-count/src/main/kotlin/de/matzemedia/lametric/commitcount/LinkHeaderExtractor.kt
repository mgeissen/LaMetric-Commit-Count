package de.matzemedia.lametric.commitcount

object LinkHeaderExtractor {

    /**
     * Sample link header from github api:
     * Link: <https://api.github.com/repositories/92684406/commits?per_page=1&page=2>; rel="next", <https://api.github.com/repositories/92684406/commits?per_page=1&page=41>; rel="last"
     */

    fun extractTotalPageSize(linkHeaderValue: String): Long? {
        return linkHeaderValue.split(",")
            .firstOrNull { it.contains("rel=\"last\"") }
            ?.split("<", ">")
            ?.get(1)
            ?.split("?")
            ?.lastOrNull()
            ?.split("&")
            ?.firstOrNull { it.startsWith("page=") }
            ?.split("=")
            ?.lastOrNull()
            ?.toLong()
    }

}
