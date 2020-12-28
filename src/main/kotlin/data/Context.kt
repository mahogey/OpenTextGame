package data

import events.Event

data class Context(
    var verb : Event?, // the last event selected by the last command
    var obj : GameObject?, // the last object selected by the last command
    var parent: GameObject // the parent to search for an object within (the room)
)