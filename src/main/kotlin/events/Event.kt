package events

import data.Context
import main.Game
import data.Instance

abstract class Event(
    override val id: String, // for saving to database/file
    override var parentId: String?, // what game object does this belong to?
    var keyword: String, // for example, "move"
    var result: String, // return string by event
    val type: String, // what type of event is this?
    val uiTag: String // tag for UI
) : Instance {

    /** logic of event, updates game context **/
    open fun update( context : Context, game : Game) : String { return result }

}