package viewmodel


import events.Event
import events.EventFactory
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ViewModel

class EventSelectorViewModel : ViewModel() {

    private val parent : GameEditViewModel by inject()

    // combo box
    val options : ObservableList<String> = FXCollections.observableArrayList( EventFactory.EVENT_TYPE_MAP.keys )

    fun onChildSelect( child : String ) {
        val event : Event = EventFactory.EVENT_TYPE_MAP[ child ]!!
        event.parentId = parent.obj.id
        parent.game.events[ event.id ] = event
        parent.dock( "Event", event.id )
    }
}