package aybat.net.boardgame.recommender.model

data class MatchingResult(
        val name: String,
        val numberOfMatches: Int
) {
    override fun toString(): String {
        return when {
            numberOfMatches == 1 -> "$name (1 match)"
            else -> "$name ($numberOfMatches matches)"
        }
    }
}