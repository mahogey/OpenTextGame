package views

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleBooleanProperty
import viewmodel.GameEditFragment

abstract class EventEditView : GameEditFragment( "Edit" ) {

    override val creatable: BooleanExpression = SimpleBooleanProperty( false )

}