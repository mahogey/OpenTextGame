package data

import base.GameData
import events.Event
import main.NULL_VALUE

data class NarrativeElement (
    override val id : String,
    val eventId : String,
    val passage : String,
    @Transient var event : Event,
    @Transient var obj : GameObject
) : GameData {
    override var parentId: String? = NULL_VALUE
}
