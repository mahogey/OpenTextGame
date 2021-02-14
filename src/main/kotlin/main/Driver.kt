package main

import com.google.gson.*
import model.Context
import model.GameObject
import model.Player
import events.PlayerTakeEvent
import events.RoomChangeEvent
import events.TextEvent
import exceptions.ExitCommandException
import exceptions.NoSuchCommandException
import tornadofx.*
import views.ExampleApp
import java.io.File
import java.util.*

const val EXIT_KEYWORD = "exit"

fun main() {
    launch< ExampleApp >()
}

fun runConsole() {
    val cell : GameObject = GameObject( "cell", null, "cell" )
    val desk : GameObject = GameObject( "desk", "cell", "desk" )
    val hallway : GameObject = GameObject( "hallway", null, "hallway" )
    val locket : GameObject = GameObject( "locket", "cell", "locket" )
    val examineDesk : TextEvent = TextEvent( "examineDesk", "desk", "examine", "A gorgeous wooden desk." )
    val examineLocket : TextEvent = TextEvent( "examineLocket", "locket", "examine", "A beautiful brass locket." )
    val examineHallway : TextEvent = TextEvent( "examineHallway", "hallway", "examine", "An empty hallway." )
    val leaveRoom : RoomChangeEvent = RoomChangeEvent( "leaveCell", "cell", "leave", "You leave the cell.", "hallway" )
    val takeEvent : PlayerTakeEvent = PlayerTakeEvent( "takeLocket", "locket", "take", "You pick up the locket, noticing its pleasing weight as you put it in your pocket." )

    /*
    game.events[ examineDesk.id ] = examineDesk
    game.events[ examineHallway.id ] = examineHallway
    game.events[ examineLocket.id ] = examineLocket
    game.events[ leaveRoom.id ] = leaveRoom
    game.events[ takeEvent.id ] = takeEvent

    game.objects[ cell.id ] = cell
    game.objects[ desk.id ] = desk
    game.objects[ hallway.id ] = hallway
    game.objects[ locket.id ] = locket

    writeObjectToFileSystem( game, "game.json" )
    */

    val game : Game = Game( Context( "NONE", "NONE", "cell" ), Player( "user" ) )

    try {
        game.loadFromJson( readObjectFromFileSystem( "game.json" ) )
        game.build()
    } catch( e : Exception ) {
        e.printStackTrace()
    }

    var input : String?

    with( Scanner( System.`in` ) ){
        do {
            input = ""
            input += this.nextLine( )
            try {
                println( game.interact( input!!.trim() ) )
            }
            catch( e : ExitCommandException ) { }
            catch( e : NoSuchCommandException ) {
                println( e.message )
            }
            catch( e : Exception ) {
                e.printStackTrace()
            }
        } while( input != null && input != EXIT_KEYWORD )
    }

    writeObjectToFileSystem( game, "game.json" )
}

fun writeObjectToFileSystem( obj : Any, file : String ) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson( obj )
    File( file ).printWriter().use { out ->
        out.println( json )
    }
}

fun readObjectFromFileSystem( file : String ) : String {
    var json : String = "";
    File( file ).useLines { lines ->
        lines.forEach {
            json += it
        }
    }
    return json
}