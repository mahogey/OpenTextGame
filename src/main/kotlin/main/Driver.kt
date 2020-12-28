package main

import data.Command
import data.Context
import data.GameObject
import events.TextEvent

fun main() {
    val desk : GameObject = GameObject( "0", "", "desk" )
    val locket : GameObject = GameObject( "1", "0", "locket" )
    desk.objects[ locket.name ] = locket // place locket inside desk

    val examineLocket : TextEvent = TextEvent( "2", "1", "examine", "A beautiful brass locket." )
    locket.events[ examineLocket.keyword ] = examineLocket // assign examine event to locket

    printObject( locket )
    println()
    printObject( desk )

    val context : Context = Context( null, null, desk )
    val game : Game = Game( context )
    val input : String = "examine locket"
    try {
        println( game.interact( input ) )
    } catch ( e : Exception ) {
        e.printStackTrace()
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