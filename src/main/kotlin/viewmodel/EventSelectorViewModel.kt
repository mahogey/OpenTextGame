package viewmodel


import events.Event
import events.EventFactory
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import main.UI_EVENT_TAG
import tornadofx.ViewModel

class EventSelectorViewModel : ViewModel() {

    private val parent : GameEditViewModel by inject()


    // combo box
    val options : ObservableList<String> = FXCollections.observableArrayList( EventFactory.EVENT_TYPE_MAP.map { it -> it.value.uiTag } )

    fun onChildSelect( child : String ) {
        val event : Event = EventFactory.EVENT_TYPE_MAP.filter { it.value.uiTag == child }.map { it.value }.first()
        event.parentId = parent.obj.id
        parent.game.events[ event.id ] = event
        parent.dock( UI_EVENT_TAG, event.id )
    }
}