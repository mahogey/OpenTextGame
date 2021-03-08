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

    private val model : GameEditViewModel by inject()

    override fun onDock() {
        super.onDock()
        menubar {
            menu("File") {
                item( "Open...", "Shortcut+O" ).action { model.onOpen() }
                item("Save Game As...","Shortcut+S").action { model.onSaveAs() }
            }
        }
        with( workspace ) {
            button( "Play Game" ) {
                action{ model.onPlayGame() }
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
