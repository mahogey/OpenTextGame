package events

import data.Context
import model.Game

abstract class Event(
    val id: String, // for saving to database/file
    var parentId: String, // what game object does this belong to?
    val type: String, // what type of event is this?
    var keyword: String, // for example, "move"
    var result: String, // return string by event
) {

    /** logic of event, updates game context **/
    open fun update( context : Context, game : Game) : String { return result }

}