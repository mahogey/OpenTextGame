package main

import com.google.gson.Gson
import data.Command
import data.Context
import data.GameObject
import events.TextEvent
import java.io.File
import java.util.*
import kotlin.collections.HashMap

const val EXIT_KEYWORD = "exit"

fun main() {

    val cell : GameObject = GameObject( "cell", null, "cell" )
    val desk : GameObject = GameObject( "desk", "cell", "desk" )
    val locket : GameObject = GameObject( "locket", "cell", "locket" )
    val examineDesk : TextEvent = TextEvent( "examineDesk", "desk", "examine", "A gorgeous wooden desk." )
    val examineLocket : TextEvent = TextEvent( "examineLocket", "locket", "examine", "A beautiful brass locket." )

/*
    printObject( locket )
    println()
    printObject( desk )
*/

    val game : Game = Game( Context( "NONE", "NONE", "cell" ) )
    game.events[ examineDesk.id ] = examineDesk
    game.events[ examineLocket.id ] = examineLocket

    game.objects[ cell.id ] = cell
    game.objects[ desk.id ] = desk
    game.objects[ locket.id ] = locket

    game.build()

/*
    println( game.events )
    println( game.objects )
    println( game.context )
*/

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

fun writeObjectsToFileSystemTest() {
    val cell : GameObject = GameObject( "cell", null, "cell" )
    val desk : GameObject = GameObject( "desk", "cell", "desk" )
    val locket : GameObject = GameObject( "locket", "cell", "locket" )
    val examineDesk : TextEvent = TextEvent( "examineDesk", "desk", "examine", "A gorgeous wooden desk." )
    val examineLocket : TextEvent = TextEvent( "examineLocket", "locket", "examine", "A beautiful brass locket." )

    desk.objects[ locket.name ] = locket // place locket inside desk
    desk.events[ examineDesk.keyword ] = examineDesk // assign examine event to desk
    locket.events[ examineLocket.keyword ] = examineLocket // assign examine event to locket

    printObject( locket )
    println()
    printObject( desk )

    val context : Context = Context( "examineLocket", "locket", "desk", null, locket, desk )
    val game : Game = Game( context )
    val input : String = "examine locket"
    try {
        println( game.interact( input ) )
    } catch ( e : Exception ) {
        e.printStackTrace()
    }

    val gson = Gson()
    val json = gson.toJson( desk )
    File( "desk.json" ).printWriter().use { out ->
        out.println( json )
    }
    File( "desk.json" ).useLines { lines ->
        lines.forEach {
            println( it )
        }
    }
}

fun printObject( obj : GameObject ) {
    println( "Object (${obj.name}) properties:\n" +
                "id: ${obj.id}\n" +
                "parentId: ${obj.parentId}\n" +
                "name: ${obj.name}\n" +
                "events: ${obj.events}\n" +
                "objects: ${obj.objects}")
}