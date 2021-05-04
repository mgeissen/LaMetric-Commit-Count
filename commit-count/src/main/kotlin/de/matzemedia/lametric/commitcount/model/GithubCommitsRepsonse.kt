package de.matzemedia.lametric.commitcount.model

data class LaMetricResponse(
    val frames: List<LaMetricFrame>
) {

    companion object {
        fun default(): LaMetricResponse = LaMetricResponse(frames = listOf(LaMetricFrame.default()))
        fun withCount(count: Long) = LaMetricResponse(frames = listOf(LaMetricFrame.default(count)))
        fun withError(error: ErrorStatusCodes) =
            LaMetricResponse(frames = listOf(LaMetricFrame.default(error.errorCode)))
    }

}

data class LaMetricFrame(
    val text: String,
    val icon: String
) {
    companion object {
        private const val GITHUB_ICON_ID = "i2799"

        fun default(count: Long = 0) = LaMetricFrame(
            text = count.toString(),
            icon = GITHUB_ICON_ID
        )
    }
}

