package views

import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Paint
import main.UI_NARRATIVE_TAG
import tornadofx.*
import viewmodel.GameEditFragmentViewModel
import viewmodel.NarrativeEditViewModel
import java.awt.Color

class NarrativeEditView() : GameEditFragment( UI_NARRATIVE_TAG ) {

    override val model: NarrativeEditViewModel by inject()

    override val root = vbox {

        listview( model.items ) {
            cellFormat {
                graphic = cache {
                    background = Background( BackgroundFill( Paint.valueOf( "000000" ), CornerRadii.EMPTY, Insets.EMPTY ) )
                    vbox{
                        label {
                            text = "${it.event.keyword} ${it.obj.name}"
                            textFill = Paint.valueOf( "00FF00" )
                        }

                        label {
                            text = "${it.passage}"
                            textFill = Paint.valueOf( "FFFFFF" )
                        }
                    }
                }
            }
        }

    }

}