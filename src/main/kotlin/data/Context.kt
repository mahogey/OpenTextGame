package data

import events.Event

data class Context (
    var verbId : String,
    var objId : String,
    var roomId : String,
    @Transient var verb : Event? = null, // the last event selected by the last command
    @Transient var obj : GameObject? = null, // the last object selected by the last command
    @Transient var room : GameObject? = null // the parent to search for an object within (the room)
)