package views

import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.*

class GameEditView() : Workspace( title = "Game Creator" ) {

    private val play : GamePlayViewModel by inject()
    private val model : GameEditViewModel by inject()

    override fun onDock() {
        super.onDock()
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
