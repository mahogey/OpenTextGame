package viewmodel

import events.*
import game.Game
import javafx.stage.FileChooser
import main.*
import tornadofx.*
import views.*
import java.io.File

class GameEditViewModel : ViewModel() {

    private val play : GamePlayViewModel by inject()

    var game : Game = Game()
    lateinit var focus : Any

    private val children : HashMap< GameEditFragment, Any > = HashMap()
    private var path : String? = null

    private lateinit var fragment : GameEditFragment

    fun dock( type: String, id: String = NULL_VALUE ) {
        when( type ) {
            UI_EVENT_TAG -> {
                focus = game.events[ id ]!!
                when( ( focus as Event ).type ) {
                    TEXT_EVENT_TAG -> workspace.dock< TextEventEditView >()
                    PLAYER_TAKE_EVENT_TAG -> workspace.dock< PlayerTakeEventEditView >()
                    ROOM_CHANGE_EVENT_TAG -> workspace.dock< RoomChangeEventEditView >()
                }
            }
            UI_OBJECT_TAG -> {
                focus = game.objects[ id ]!!
                workspace.dock< GameObjectEditView >()
            }
            UI_NARRATIVE_TAG -> {
                focus = game.narrative
                workspace.dock< NarrativeEditView >()
            }
        }
    }

    fun init( game : Game ) {
        this.game = game
        dock( UI_OBJECT_TAG, GAME_ID )
    }

    fun onChildDocked( frag : GameEditFragment ) {
        fragment = frag
        if( frag in children ) {
            focus = children[ frag ]!!
        } else {
            children[ frag ] = focus
        }
        fragment.model.init( focus )
    }

    fun onChildUndocked( frag : GameEditFragment ) { }

    fun onCreate() {
        fragment.model.onCreate()
    }

    fun onDelete() {
        fragment.model.onDelete()
        val idx : Int = workspace.viewStack.indexOf( workspace.dockedComponent )
        var counter : Int = workspace.viewStack.size - 1
        while( counter != idx ) {
            workspace.viewStack.removeAt( counter )
            counter -= 1
        }
        workspace.navigateBack()
        workspace.viewStack.removeAt( idx )
    }

    fun onNarrative() {
        dock( UI_NARRATIVE_TAG )
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
        fragment.model.commit()
        fragment.model.onSave()
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

    /*
    private fun setCurrentInstance( type : String, id : String ) {
        when( type ) {
            UI_EVENT_TAG -> {
                focus = game.events[ id ]!!
            }
            UI_OBJECT_TAG -> {
                focus = game.objects[ id ]!!
            }
            UI_NARRATIVE_TAG -> {
                focus = game.narrative
            }
        }
    }
    */
}

abstract class GameEditFragmentViewModel : ViewModel() {

    val parent : GameEditViewModel by inject()

    abstract fun init( obj : Any )
    abstract fun commit()
    abstract fun reset()

    abstract fun onCreate()
    abstract fun onDelete()
    abstract fun onSave()

}