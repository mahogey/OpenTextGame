package game

import main.NULL_VALUE

data class Command ( val verbKey : String, val objectName: String )

fun stringToCommand( input : String ) : Command {
    var words : List< String > = input.split( " ", limit = 2 )
    return Command( words[ 0 ], if ( words.size > 1 ) words[ 1 ] else NULL_VALUE )
}