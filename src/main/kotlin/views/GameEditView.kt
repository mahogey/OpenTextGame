package views

import com.google.gson.GsonBuilder
import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.stage.FileChooser
import main.Game
import main.readObjectFromFileSystem
import main.writeObjectToFileSystem
import tornadofx.*
import viewmodel.*
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter

class GameEditView() : Workspace( title = "Game Creator" ) {

    private val play : GamePlayViewModel by inject()
    private val model : GameEditViewModel by inject()

    private var path : String? = null

    override fun onDock() {
        super.onDock()
        menubar {
            menu("File") {
                item( "Open...", "Shortcut+O" ).action { onOpen() }
                item("Save Game As...","Shortcut+S").action { onSaveAs() }
            }
        }
        with( workspace ) {
            button( "Play Game" ) {
                action{
                    play.init( model.game )
                    find< GamePlayView >().openModal()
                }
            }
        }
    }

    override fun onCreate() {
        model.onCreate()
        super.onCreate()
    }

    override fun onDelete() {
        model.onDelete()
        super.onDelete()
    }

    override fun onSave() {
        model.onSave()
        super.onSave()
    }

    private fun onOpen() {
        val chooser = FileChooser()
        chooser.extensionFilters.add( FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json" ) )
        if( path != null ) {
            chooser.initialDirectory = File( path )
        }
        viewStack.clear()

        try {
            val file : File = chooser.showOpenDialog( primaryStage )
            path = file.parent
            model.init( Game().loadFromJson( readObjectFromFileSystem( file ) ).build() )
        } catch ( e: NullPointerException ) { }
    }

    private fun onSaveAs() {
        val chooser = FileChooser()
        chooser.extensionFilters.add( FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json" ) )
        if( path != null ) {
            chooser.initialDirectory = File( path )
        }

        try {
            val file : File = chooser.showSaveDialog( primaryStage )
            path = file.parent
            writeObjectToFileSystem( model.game, file )
        } catch ( e: NullPointerException ) { }
    }

}


abstract class GameEditFragment( val type : String ) : Fragment() {

    abstract val model : GameEditFragmentViewModel
    private val parent : GameEditViewModel by inject()

    override fun onDock() {
        parent.onChildDocked( this )
        model.reset()
        super.onDock()
    }

    override fun onUndock() {
        parent.onChildUndocked( this )
        super.onUndock()
    }

}
