package views

import javafx.scene.Parent
import tornadofx.*
import viewmodel.GameEditFragment
import viewmodel.TextEventEditViewModel

class TextEventEditView() : GameEditFragment( "Event" ) {

    override var model : TextEventEditViewModel = TextEventEditViewModel()

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
