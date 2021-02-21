package data

import events.Event

data class GameObject(
    val id : String = "NONE", // identifier in storage (global)
    var parentId: String? = "NONE", // what game object does this belong to?
    val name: String = "NONE", // identifier in the game (game-relative)
    @Transient val events: HashMap< String, Event > = HashMap< String, Event >(), // stores events contained by this object by name
    @Transient val objects: HashMap< String, GameObject > = HashMap< String, GameObject >() // store objects contained by this object by name
)