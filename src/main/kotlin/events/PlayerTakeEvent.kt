package events

import game.Context
import game.Game
import main.NULL_VALUE

const val PLAYER_TAKE_EVENT_TAG = "PLAYER_TAKE_EVENT"
const val PLAYER_TAKE_EVENT_UI_TAG = "PLAYER TAKE EVENT"

class PlayerTakeEvent(
    id: String, // for saving to database/file
    parentId: String = NULL_VALUE, // what game object does this belong to?
    keyword: String = NULL_VALUE, // for example, "move"
    result: String = NULL_VALUE, // return string by event
) : Event( id, parentId, keyword, result, PLAYER_TAKE_EVENT_TAG, PLAYER_TAKE_EVENT_UI_TAG ){

    override fun update(context: Context, game: Game): String {
        game.objects[ context.objectId ]!!.parentId = game.player.id
        return super.update( context, game )
    }

}