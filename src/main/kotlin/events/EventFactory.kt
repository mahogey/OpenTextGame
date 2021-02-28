package events

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

class EventFactory {

    companion object Factory {

        val EVENT_TYPE_MAP : HashMap< String, Event > = hashMapOf(
            "PLAYER_TAKE_EVENT" to PlayerTakeEvent( "id" + System.currentTimeMillis() ),
            "ROOM_CHANGE_EVENT" to RoomChangeEvent( "id" + System.currentTimeMillis() ),
            "TEXT_EVENT" to TextEvent( "id" + System.currentTimeMillis() )
        )

        fun fromJsonElementToTypedEvent( json : JsonObject ) : Event {
            return Gson().fromJson( json, EVENT_TYPE_MAP[ json[ "type" ].asString ]!!::class.java )
        }
    }

}