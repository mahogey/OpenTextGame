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
        viewStack.clear()
        model.init( Game().loadFromJson( readObjectFromFileSystem( chooser.showOpenDialog( primaryStage ) ) ).build() )
    }

    private fun onSaveAs() {
        val chooser = FileChooser()
        chooser.extensionFilters.add( FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json" ) )
        writeObjectToFileSystem( model.game, chooser.showSaveDialog( primaryStage ) )
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
