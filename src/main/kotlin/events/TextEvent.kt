package events

import data.Context
import main.Game
import main.NULL_VALUE

const val TEXT_EVENT_TAG = "TEXT_EVENT"
const val TEXT_EVENT_UI_TAG = "TEXT EVENT"

class TextEvent(
    id: String, // for saving to database/file
    parentId: String = NULL_VALUE, // what game object does this belong to?
    keyword: String = NULL_VALUE, // for example, "move"
    result: String = NULL_VALUE, // return string by event
) : Event( id, parentId, keyword, result, TEXT_EVENT_TAG, TEXT_EVENT_UI_TAG ) {

    override fun update( context: Context, game: Game): String {
        return super.update( context, game )
    }

}