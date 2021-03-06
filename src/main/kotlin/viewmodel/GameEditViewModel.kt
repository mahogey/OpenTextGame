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
import views.GameEditFragment
import views.GameObjectEditView
import views.RoomChangeEventEditView
import views.TextEventEditView

class GameEditViewModel : Controller() {

    private val children : HashMap< GameEditFragment, Instance > = HashMap()

    var game : Game = Game()
    lateinit var obj : Instance
    private lateinit var focus : GameEditFragment

    private val goModel : GameObjectEditViewModel by inject()

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

    fun onCreate() {
        focus.model.onCreate()
        /*
        var obj : GameObject = goModel.obj
        var nobj : GameObject = GameObject( "id" + System.currentTimeMillis(), goModel.obj.id, "" )

        obj.objects[ nobj.id ] = nobj
        game.objects[ nobj.id ] = nobj

        goModel.obj = nobj
        workspace.dock< GameObjectEditView >()
         */
    }

    fun onDelete() {
        focus.model.onDelete()
        /*
        var obj : GameObject = goModel.obj
        var nobj : GameObject = game.objects[ obj.parentId ]!!

        nobj.objects.remove( obj.id )
        game.objects.remove( obj.id )

        goModel.obj = nobj
        workspace.dock< GameObjectEditView >()
        */
    }

    fun onSave() {
        focus.model.onSave()
        /*
        val chooser = FileChooser()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson( game )

        chooser.extensionFilters.add( FileChooser.ExtensionFilter( "JSON Files (*.json)", "*.txt" ) )
        val file = chooser.showSaveDialog( primaryStage )

        if( file != null ) {
            file.printWriter().use { out ->
                out.println( json )
            }
        }
        */
    }

    fun dock( type: String, id: String ) {
        when( type ) {
            "Event" -> {
                obj = game.events[ id ]!!
                when( ( obj as Event ).type ) {
                    "TEXT_EVENT" -> workspace.dock< TextEventEditView >()
                    "ROOM_CHANGE_EVENT" -> workspace.dock< RoomChangeEventEditView >()
                }
            }
            "Object" -> {
                obj = game.objects[ id ]!!
                workspace.dock< GameObjectEditView >()
            }
        }
    }

    fun onChildDocked( frag : GameEditFragment ) {
        focus = frag
        if( frag in children ) {
            focus.model.init( children[ frag ]!! )
        } else {
            children[ frag ] = obj
            focus.model.init( obj )
        }
    }

    fun onChildUndocked( frag : GameEditFragment ) {
        frag.model.commit()
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