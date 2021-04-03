package game

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import data.*
import events.Event
import events.EventFactory
import exceptions.ExitCommandException
import exceptions.NoSuchCommandException
import main.GAME_ID
import main.NULL_VALUE
import main.PLAYER_NAME

class Game (
    val context : Context = Context(), // game context
    val events: HashMap< String, Event > = HashMap< String, Event >(), // all events (key: id)
    val narrative : HashMap< String, NarrativeElement > = HashMap< String, NarrativeElement >(), // narrative
    val objects: HashMap< String, GameObject > = HashMap< String, GameObject >(), // all objects (key: id)
    var player : Player = Player( PLAYER_NAME ) // player object
) {

    fun build() : Game {
        objects[ GAME_ID ] = GameObject( GAME_ID, NULL_VALUE, GAME_ID )
        for( event in events.values ) {
            if( event.parentId in objects ) {
                objects[ event.parentId ]!!.events[ event.keyword ] = event
            }
        }
        for( obj in objects.values ) {
            if( obj.parentId in objects ) {
                objects[ obj.parentId ]!!.objects[ obj.name ] = obj
            } else if ( obj.parentId == player.id ) {
                player.objects[ obj.id ] = obj
            }
        }
        for( element in narrative.values ) {
            if( element.eventId in events ) {
                narrative[ element.id ]!!.event = events[ element.eventId ]!!
                narrative[ element.id ]!!.obj = objects[ narrative[ element.id ]!!.event.parentId ]!!
            }
        }
        return this
    }

    fun loadFromJson( json : String ) : Game {
        val gson = Gson()
        val root : JsonObject = JsonParser.parseString( json ).asJsonObject

        player = gson.fromJson( root[ "player" ], Player::class.java )

        val contextObject : JsonObject = root[ "context" ].asJsonObject
        context.objectId = contextObject[ "objectId" ].asString
        context.roomId = contextObject[ "roomId" ].asString
        context.verbId = contextObject[ "verbId" ].asString

        events.clear()
        for( event in root.getAsJsonObject( "events" ).entrySet() ) {
            val ev : Event = EventFactory.fromJsonElementToTypedEvent( event.value.asJsonObject )
            events[ ev.id ] = ev
        }

        objects.clear()
        for( jsonObj in root.getAsJsonObject( "objects" ).entrySet() ) {
            val obj : GameObject = gson.fromJson( jsonObj.value, GameObject::class.java )
            objects[ obj.id ] = obj
        }

        narrative.clear()
        for( jsonObj in root.getAsJsonObject( "narrative" ).entrySet() ) {
            val obj : NarrativeElement = gson.fromJson( jsonObj.value, NarrativeElement::class.java )
            narrative[ jsonObj.key ] = obj
        }

        return this
    }

    fun reset() : Game {
        context.objectId = NULL_VALUE
        context.roomId = "cell"
        context.verbId = NULL_VALUE
        return this
    }

    /** takes string input from user and modifies context before returning a result string **/
    fun interact( input : String ) : String {
        //get command from input string
        val command : Command = stringToCommand( input )

        if( command.verbKey == "exit" ) {
            throw ExitCommandException()
        }

        // look for object in parent and set object to parent if not found
        if( command.objectName != NULL_VALUE && command.objectName in objects[ context.roomId ]!!.objects ) {
            context.objectId = objects[ context.roomId ]!!.objects[ command.objectName ]!!.id
        } else {
            context.objectId = context.roomId
        }

        // look for event in identified object
        if( command.verbKey in objects[ context.objectId ]!!.events ) {
            context.verbId = objects[ context.objectId ]!!.events[ command.verbKey ]!!.id
        } else {
            throw NoSuchCommandException()
        }

        var ret : String = ""

        // check for narrative element
        if( context.verbId in narrative ) {
            ret = narrative[ context.verbId ]!!.passage + "\n\n"
        }

        // fire event and return output
        return ret + events[ context.verbId ]!!.update( context, this )
    }

}