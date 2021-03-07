package views

import javafx.scene.Parent
import tornadofx.*
import viewmodel.RoomChangeEventEditViewModel

class RoomChangeEventEditView() : EventEditView() {

    override val model : RoomChangeEventEditViewModel by inject()

    override val root: Parent = vbox {
        hbox {
            label( "Keyword" )
            textfield( model.keyword )
        }
        hbox {
            label( "Room" )
            combobox< String > ( model.selectedLink, model.links )
        }
        label( "Result" )
        textarea( model.result ) {
            isWrapText = true
        }
    }

}
