package aybat.net.boardgame.recommender.model

data class AnalysisResponse(
        val matchingResults: List<MatchingResult>,
        val nonMatchingResults: Set<String>
)
