package viewmodel

import com.google.gson.GsonBuilder
import data.Context
import data.GameObject
import data.Instance
import data.Player
import events.Event
import events.RoomChangeEvent
import javafx.stage.FileChooser
import main.Game
import main.readObjectFromFileSystem
import tornadofx.*
import views.*

class GameEditViewModel : Controller() {

    var game : Game = Game()
    lateinit var obj : Instance

    private val children : HashMap< GameEditFragment, String > = HashMap()
    private lateinit var focus : GameEditFragment

    fun dock( type: String, id: String ) {
        setCurrentInstance( type, id )
        when( type ) {
            "Event" -> {
                when( ( obj as Event ).type ) {
                    "TEXT_EVENT" -> workspace.dock< TextEventEditView >()
                    "PLAYER_TAKE_EVENT" -> workspace.dock< PlayerTakeEventEditView >()
                    "ROOM_CHANGE_EVENT" -> workspace.dock< RoomChangeEventEditView >()
                }
            }
            "Object" -> {
                workspace.dock< GameObjectEditView >()
            }
        }
    }

    fun init() {
        game = Game( Context( "NONE", "GAME", "cell" ), Player( "user" ) )
        try {
            game.loadFromJson( readObjectFromFileSystem( "game.json" ) )
            game.build()
        } catch( e : Exception ) {
            e.printStackTrace()
        }

        dock( "Object", "GAME" )
    }

    fun onChildDocked( frag : GameEditFragment ) {
        focus = frag
        if( frag in children ) {
           // println( "here" )
            setCurrentInstance( frag.type, children[ frag ]!! )
            //println( obj )
        } else {
            children[ frag ] = obj.id
        }
        focus.model.init( obj )
    }

    fun onChildUndocked( frag : GameEditFragment ) {
        frag.model.commit()
    }


    fun onCreate() {
        focus.model.onCreate()
    }

    fun onDelete() {
        focus.model.onDelete()

        val idx : Int = workspace.viewStack.indexOf( workspace.dockedComponent )
        var counter : Int = workspace.viewStack.size - 1
        while( counter != idx ) {
            workspace.viewStack.removeAt( counter )
            counter -= 1
        }
        workspace.navigateBack()
        workspace.viewStack.removeAt( idx )
    }

    fun onSave() {
        focus.model.commit()
        focus.model.onSave()
    }

    private fun setCurrentInstance( type : String, id : String ) {
        when( type ) {
            "Event" -> {
                obj = game.events[ id ]!!
            }
            "Object" -> {
                obj = game.objects[ id ]!!
            }
        }
    }

}

abstract class GameEditFragmentViewModel : ViewModel() {

    val parent : GameEditViewModel by inject()

    abstract fun init( instance : Instance )
    abstract fun commit()
    abstract fun reset()

    abstract fun onCreate()
    abstract fun onDelete()
    abstract fun onSave()

}