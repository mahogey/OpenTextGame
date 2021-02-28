package views

import data.GameObject
import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.GameEditFragment
import viewmodel.GameObjectEditViewModel

class GameObjectEditView() : GameEditFragment( "Object" ) {

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
                model.onChildSelect( it )
            }
        }
    }

    override fun onDock() {
        super.onDock()
        model.reset()
    }

}
