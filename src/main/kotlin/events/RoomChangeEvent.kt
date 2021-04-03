package events

import game.Context
import game.Game
import main.NULL_VALUE

const val ROOM_CHANGE_EVENT_TAG = "ROOM_CHANGE_EVENT"
const val ROOM_CHANGE_EVENT_UI_TAG = "ROOM CHANGE EVENT"

class RoomChangeEvent(
    id: String, // for saving to database/file
    parentId: String = NULL_VALUE, // what game object does this belong to?
    keyword: String = NULL_VALUE, // for example, "move"
    result: String = NULL_VALUE, // return string by event
    var linkId: String = parentId, // room to change
) : Event( id, parentId, keyword, result, ROOM_CHANGE_EVENT_TAG, ROOM_CHANGE_EVENT_UI_TAG ){

    override fun update(context: Context, game: Game): String {
        context.roomId = linkId
        return super.update( context, game )
    }

}