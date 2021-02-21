package views

import javafx.scene.Parent
import tornadofx.*
import viewmodel.GameObjectEditViewController
import viewmodel.TextEventEditViewModel

class TextEventEditView() : View() {

    private val objectEditController : GameObjectEditViewController by inject()
    var model : TextEventEditViewModel = TextEventEditViewModel()

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
