package data

import events.Event

data class GameObject(
    val id : String, // identifier in storage (global)
    val parentId: String?, // what game object does this belong to?
    val name: String, // identifier in the game (game-relative)
    @Transient val events: HashMap< String, Event > = HashMap< String, Event >(), // stores events contained by this object
    @Transient val objects: HashMap< String, GameObject > = HashMap< String, GameObject >() // store objects contained by this object
)