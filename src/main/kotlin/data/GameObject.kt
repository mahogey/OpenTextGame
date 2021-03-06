package data

import events.Event
import views.GameObjectEditView

data class GameObject(
    override val id : String = "NONE", // identifier in storage (global)
    override var parentId: String? = "GAME", // what game object does this belong to?
    var name: String = "NONE", // identifier in the game (game-relative)
    @Transient val events: HashMap< String, Event > = HashMap< String, Event >(), // stores events contained by this object by name
    @Transient val objects: HashMap< String, GameObject > = HashMap< String, GameObject >() // store objects contained by this object by name
) : Instance