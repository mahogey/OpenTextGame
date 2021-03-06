package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.Parent
import tornadofx.*
import viewmodel.TextEventEditViewModel

class PlayerTakeEventEditView() : EventEditView() {

    override val model : TextEventEditViewModel by inject()

    override val root: Parent = vbox {
        hbox {
            label( "Keyword" )
            textfield( model.keyword )
        }
        hbox {
            label( "Result" )
            textfield( model.result )
        }
    }

}
