package viewmodel

import data.Instance
import events.Event
import events.TextEvent
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class TextEventEditViewModel : GameEditFragmentViewModel() {

    private val controller : EventController by inject()

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }


    override fun commit() {
        controller.event.keyword = keyword.value
        controller.event.result = keyword.value
    }

    override fun reset() {
        keyword.value = controller.event.keyword
        result.value = controller.event.result
    }

}

class EventController : GameEditFragmentController() {

    var event : Event = TextEvent()

    override fun init( instance : Instance )  {
        event = instance as Event
    }

}