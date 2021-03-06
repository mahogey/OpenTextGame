package events

import data.Context
import main.Game

class RoomChangeEvent(
    id: String, // for saving to database/file
    parentId: String = "NONE", // what game object does this belong to?
    keyword: String = "NONE", // for example, "move"
    result: String = "NONE", // return string by event
    var linkId: String = parentId, // room to change
) : Event( id, parentId, "ROOM_CHANGE_EVENT", keyword, result ){

    override fun update( context: Context, game: Game ): String {
        context.roomId = linkId
        return super.update( context, game )
    }

}