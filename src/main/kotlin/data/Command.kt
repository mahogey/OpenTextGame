package data

data class Command ( val verb : String, val obj: String )

fun stringToCommand( input : String ) : Command {
    var words : List< String > = input.split( " ", limit = 2 )
    return Command( words[ 0 ], words[ 1 ] )
}