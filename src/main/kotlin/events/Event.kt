package events

import model.Context
import main.Game

abstract class Event(
    val id: String, // for saving to database/file
    val parentId: String, // what game object does this belong to?
    val type: String, // what type of event is this?
    val keyword: String, // for example, "move"
    private val result: String, // return string by event
) {

    /** logic of event, updates game context **/
    open fun update( context : Context, game : Game) : String { return result }

}