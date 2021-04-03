package views

import javafx.scene.control.Button
import tornadofx.*
import viewmodel.*

class GameEditView() : Workspace( title = "Game Creator" ) {

    private val model : GameEditViewModel by inject()

    override fun onDock() {
        super.onDock()
        menubar {
            menu("File") {
                item( "Open...", "Shortcut+O" ).action { model.onOpen() }
                item("Save Game As...","Shortcut+S").action { model.onSaveAs() }
            }
            menu( "Play" ) {
                item( "Play Game", "Shortcut+G" ).action {  model.onPlayGame() }
            }
        }
        with( workspace ) {
            button( "Narrative" ) {
                action{
                    model.onNarrative()
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
