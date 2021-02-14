package events

import model.Context
import main.Game

class TextEvent(
    id: String, // for saving to database/file
    parentId: String, // what game object does this belong to?
    keyword: String, // for example, "move"
    result: String, // return string by event
) : Event( id, parentId, "TEXT_EVENT", keyword, result ){

    override fun update( context: Context, game: Game): String {
        return super.update( context, game )
    }

}