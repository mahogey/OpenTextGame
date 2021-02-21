package events

import data.Context
import model.Game

class RoomChangeEvent(
    id: String, // for saving to database/file
    parentId: String, // what game object does this belong to?
    keyword: String, // for example, "move"
    result: String, // return string by event
    private val newParentId: String, // room to change
) : Event( id, parentId, "ROOM_CHANGE_EVENT", keyword, result ){

    override fun update( context: Context, game: Game): String {
        context.roomId = newParentId
        return super.update( context, game )
    }

}