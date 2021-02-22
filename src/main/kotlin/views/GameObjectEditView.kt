package views

import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.GameEditFragment
import viewmodel.GameObjectEditViewController
import viewmodel.GameObjectEditViewModel

class GameObjectEditView() : GameEditFragment( "Object" ) {

    private val controller : GameObjectEditViewController by inject()
    override val model : GameObjectEditViewModel by inject()

    override val root: Parent = vbox {
        hbox {
            label( "Name" )
            textfield( model.name )
        }
        combobox< String > ( model.selected, FXCollections.observableArrayList( model.options ) ) {

        }
        listview( model.items ) {
            onUserSelect {
                controller.onChildSelect( it )
            }
        }
    }

    override fun onDock() {
        super.onDock()
        model.reset()
    }

}
