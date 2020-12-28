package main

import data.Command
import data.Context
import data.stringToCommand

class Game(
    var context : Context // game context at initialization
) {

    /** takes string input from user and modifies context before returning a result string **/
    fun interact( input : String ) : String {
        //get command from input string
        val command : Command = stringToCommand( input )

        // look for object in parent and set object to parent if not found
        if( command.obj in context.parent.objects ) {
            context.obj = context.parent.objects[ command.obj ]
        } else {
            context.obj = context.parent
        }

        // look for event in identified object
        if( command.verb in context.obj!!.events ) {
            context.verb = context.obj!!.events[ command.verb ]
        }

        println( "verb: ${context.verb}")
        // fire event and return output
        return context.verb!!.update( context )
    }

}