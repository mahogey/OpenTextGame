package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty

abstract class EventEditView : GameEditFragment( "Event" ) {

    override val creatable: BooleanExpression = SimpleBooleanProperty( false )

}