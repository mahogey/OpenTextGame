package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.RoomChangeEventEditViewModel
import viewmodel.TextEventEditViewModel

class RoomChangeEventEditView() : EventEditView() {

    override val model : RoomChangeEventEditViewModel by inject()

    override val root: Parent = vbox {
        hbox {
            label( "Keyword" )
            textfield( model.keyword )
        }
        hbox {
            label( "Result" )
            textfield( model.result )
        }
        hbox {
            label( "Room" )
            combobox< String > ( model.selectedLink , model.links )
        }
    }

}
