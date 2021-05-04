package de.matzemedia.lametric.commitcount.model

enum class ErrorStatusCodes(val errorCode: Long) {

    MALFORMED_REPO(-1L),
    REPO_NOT_FOUND(-2L),

}
