package events

import data.Context
import main.Game

class PlayerTakeEvent(
    id: String, // for saving to database/file
    parentId: String, // what game object does this belong to?
    keyword: String, // for example, "move"
    result: String, // return string by event
) : Event( id, parentId, "PLAYER_TAKE_EVENT", keyword, result ){

    override fun update( context: Context, game: Game): String {
        game.objects[ context.objectId ]!!.parentId = game.player.id
        return super.update( context, game )
    }

}