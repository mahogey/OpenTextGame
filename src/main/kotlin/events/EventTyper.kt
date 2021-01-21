package events

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

class EventTyper {

    companion object Factory {

        private val EVENT_TYPE_MAP : HashMap< String, Class< out Event > > = hashMapOf(
            "TEXT_EVENT" to TextEvent::class.java,
            "ROOM_CHANGE_EVENT" to RoomChangeEvent::class.java
        )

        fun fromJsonElementToTypedEvent( json : JsonObject ) : Event {
            return Gson().fromJson( json, EVENT_TYPE_MAP[ json[ "type" ].asString ] )
        }
    }

}