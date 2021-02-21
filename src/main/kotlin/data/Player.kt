package data

data class Player (
    val id : String = "player",
    @Transient val objects : HashMap< String, GameObject > = HashMap< String, GameObject >() // objects held by player
)