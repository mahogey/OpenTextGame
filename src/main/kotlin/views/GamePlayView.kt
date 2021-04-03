package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Paint
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow
import tornadofx.*
import viewmodel.GamePlayViewModel
import viewmodel.TextEventEditViewModel
import java.awt.Color

class GamePlayView() : Fragment( "Game Play Window" ) {

    private val model : GamePlayViewModel by inject()

    override val root: Parent = vbox {
        lateinit var flow : TextFlow
        val scroll = scrollpane( true ) {
            minHeight = 450.0
            minWidth = 300.0
            flow = textflow() {
                background = Background( BackgroundFill( Paint.valueOf( "000000" ), CornerRadii.EMPTY, Insets.EMPTY ) )
                minHeight = 445.0
                minWidth = 280.0
            }
        }
        vbox {
            hbox{
                combobox< String > ( model.objectSelected, model.objects ) { minWidth = 120.0 }
                combobox< String > ( model.eventSelected, model.events ) { minWidth = 120.0 }
                button( "Go" ) {
                    action { model.onGoButtonClick( flow, scroll ) }
                    minWidth = 40.0
                }
            }
        }
    }

}
