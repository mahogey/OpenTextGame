package events

import data.Context
import main.Game

class TextEvent(
    id: String = "NONE", // for saving to database/file
    parentId: String = "NONE", // what game object does this belong to?
    keyword: String = "NONE", // for example, "move"
    result: String = "NONE", // return string by event
) : Event( id, parentId, "TEXT_EVENT", keyword, result ){

    override fun update( context: Context, game: Game): String {
        return super.update( context, game )
    }

}