package events

import data.Context
import main.Game

class RoomChangeEvent(
    id: String, // for saving to database/file
    parentId: String, // what game object does this belong to?
    keyword: String, // for example, "move"
    result: String, // return string by event
    private val newParentId: String, //
) : Event( id, parentId, "PARENT_EVENT", keyword, result ){

    override fun update( context: Context, game: Game ): String {
        context.room = game!!.objects[ newParentId ]!!
        return super.update( context, game )
    }

}