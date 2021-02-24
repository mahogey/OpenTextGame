package views

import javafx.scene.Parent
import tornadofx.*
import viewmodel.EventController
import viewmodel.GameEditFragment
import viewmodel.TextEventEditViewModel

class TextEventEditView() : GameEditFragment( "Event" ) {

    override val controller : EventController by inject()
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

    override fun onDock() {
        super.onDock()
        model.reset()
    }

}
