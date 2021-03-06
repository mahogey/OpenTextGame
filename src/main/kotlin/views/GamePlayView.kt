package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.Parent
import tornadofx.*
import viewmodel.GamePlayViewModel
import viewmodel.TextEventEditViewModel

class GamePlayView() : Fragment() {

    private val model : GamePlayViewModel by inject()

    override val root: Parent = vbox {
        hbox {
            label( model.result ){
                minHeight = 450.0
                minWidth = 300.0
            }
        }
        hbox {
            label( "Action" )
            textfield( model.action )
            button( "Go" ) {
                action { model.onGoButtonClick() }
            }
        }
    }

}
