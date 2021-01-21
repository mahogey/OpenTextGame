package main

import com.google.gson.*
import data.Command
import data.Context
import data.GameObject
import events.RoomChangeEvent
import events.TextEvent
import java.io.File
import java.util.*
import kotlin.collections.HashMap

const val EXIT_KEYWORD = "exit"

fun main() {

    val cell : GameObject = GameObject( "cell", null, "cell" )
    val desk : GameObject = GameObject( "desk", "cell", "desk" )
    val hallway : GameObject = GameObject( "hallway", null, "hallway" )
    val locket : GameObject = GameObject( "locket", "cell", "locket" )
    val examineDesk : TextEvent = TextEvent( "examineDesk", "desk", "examine", "A gorgeous wooden desk." )
    val examineLocket : TextEvent = TextEvent( "examineLocket", "locket", "examine", "A beautiful brass locket." )
    val leaveRoom : RoomChangeEvent = RoomChangeEvent( "leaveCell", "cell", "leave", "You leave the cell.", "hallway" )

/*
    printObject( locket )
    println()
    printObject( desk )
*/

    val game : Game = Game( Context( "NONE", "NONE", "cell" ) )
    game.events[ examineDesk.id ] = examineDesk
    game.events[ examineLocket.id ] = examineLocket
    game.events[ leaveRoom.id ] = leaveRoom

    game.objects[ cell.id ] = cell
    game.objects[ desk.id ] = desk
    game.objects[ hallway.id ] = hallway
    game.objects[ locket.id ] = locket

    game.build()

    writeObjectToFileSystem( game, "game.json" )
/*
    println( game.events )
    println( game.objects )
    println( game.context )
*/

    try {
        game.loadFromJson( readObjectFromFileSystem( "game.json" ) )
    } catch( e : Exception ) {
        e.printStackTrace()
    }
    println( game.events )
    var input : String?

    with( Scanner( System.`in` ) ){
        do {
            input = ""
            input += this.nextLine( )
            try {
                println( game.interact( input!!.trim() ) )
            } catch( e : Exception ) {
                println( "Can't do that!" )
                e.printStackTrace()
            }
        } while( input != null && input != EXIT_KEYWORD )
    }

}

fun writeObjectToFileSystem( obj : Any, file : String ) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson( obj )
    File( file ).printWriter().use { out ->
        out.println( json )
    }
}

fun readObjectFromFileSystem( file : String ) : String {
    val gson = GsonBuilder().setPrettyPrinting().create()
    var json : String = "";
    File( file ).useLines { lines ->
        lines.forEach {
            json += it
        }
    }
    return json
}