package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.text.TextAlignment
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
                textAlignment = TextAlignment.LEFT
                padding = Insets(5.0 )
            }
        }
        vbox {
            textfield( model.action ) {
                promptText = "Action"
            }
            button( "Go" ) {
                action { model.onGoButtonClick() }
            }
        }
    }

}
