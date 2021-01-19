package main

import data.Command
import data.Context
import data.GameObject
import data.stringToCommand
import events.Event

class Game (
    val context : Context = Context( "NONE", "NONE", "NONE" ),
    val events: HashMap< String, Event > = HashMap< String, Event >(), // all events (key: id)
    val objects: HashMap< String, GameObject > = HashMap< String, GameObject >() // all objects (key: id)
) {

    fun build() {
        for( event in events.values ) {
            if( event.parentId in objects ) {
                objects[ event.parentId ]!!.events[ event.keyword ] = event
            }
        }
        for( obj in objects.values ) {
            if( obj.parentId in objects ) {
                objects[ obj.parentId ]!!.objects[ obj.name ] = obj
            }
        }
        context.verb = events[ context.verbId ]
        context.obj = objects[ context.objId ]
        context.room = objects[ context.roomId ]
    }

    /** takes string input from user and modifies context before returning a result string **/
    fun interact( input : String ) : String {
        //get command from input string
        println(  "string: $input" )
        val command : Command = stringToCommand( input )

        println( "verb: ${command.verb}, object: ${command.obj}" )

        // look for object in parent and set object to parent if not found
        if( command.obj in context.room!!.objects ) {
            context.obj = context.room!!.objects[ command.obj ]
        } else {
            context.obj = context.room
        }

        // look for event in identified object
        if( command.verb in context.obj!!.events ) {
            context.verb = context.obj!!.events[ command.verb ]
        }

        // fire event and return output
        return context.verb!!.update( context, this )
    }

}