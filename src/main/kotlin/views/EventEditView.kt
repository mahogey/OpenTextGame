package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import main.UI_EVENT_TAG

abstract class EventEditView : GameEditFragment( UI_EVENT_TAG ) {

    override val creatable: BooleanExpression = SimpleBooleanProperty( false )

}