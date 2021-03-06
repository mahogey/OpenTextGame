package views

import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.*

class GameEditView() : Workspace( title = "Game Creator" ) {

    private val model : GameEditViewModel by inject()

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


abstract class GameEditFragment( type : String ) : Fragment() {

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
