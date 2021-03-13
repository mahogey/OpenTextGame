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
import main.writeObjectToFileSystem
import tornadofx.*
import views.*
import java.io.File

class GameEditViewModel : Controller() {

    private val play : GamePlayViewModel by inject()

    var game : Game = Game()
    lateinit var obj : Instance

    private val children : HashMap< GameEditFragment, String > = HashMap()
    private var path : String? = null

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

    fun init( game : Game) {
        this.game = game
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

    fun onOpen() {
        val chooser = FileChooser()
        chooser.extensionFilters.add( FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json" ) )
        if( path != null ) {
            chooser.initialDirectory = File( path )
        }


        try {
            val file : File = chooser.showOpenDialog( primaryStage )
            path = file.parent
            workspace.viewStack.clear()
            init( Game().loadFromJson( readObjectFromFileSystem( file ) ).build() )
        } catch ( e: NullPointerException ) { }
    }

    fun onPlayGame() {
        play.init( game.reset() )
        find< GamePlayView >().openModal()
    }

    fun onSave() {
        focus.model.commit()
        focus.model.onSave()
    }

    fun onSaveAs() {
        val chooser = FileChooser()
        chooser.extensionFilters.add( FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json" ) )
        if( path != null ) {
            chooser.initialDirectory = File( path )
        }

        try {
            val file : File = chooser.showSaveDialog( primaryStage )
            path = file.parent
            writeObjectToFileSystem( game, file )
        } catch ( e: NullPointerException ) { }
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