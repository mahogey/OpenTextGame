package data

import main.PLAYER_NAME

data class Player (
    val id : String = PLAYER_NAME,
    @Transient val objects : HashMap< String, GameObject > = HashMap< String, GameObject >() // objects held by player
)