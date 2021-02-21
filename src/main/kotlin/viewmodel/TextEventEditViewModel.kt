package viewmodel

import data.Context
import data.GameObject
import data.Player
import events.Event
import events.TextEvent
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import model.Game
import main.readObjectFromFileSystem
import tornadofx.*
import views.GameObjectEditView

class TextEventEditViewModel : ViewModel() {

    private val controller : EventController by inject()

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }

    fun reset() {
        keyword.value = controller.event.keyword
        result.value = controller.event.result
    }

    fun commit() {
        controller.event.keyword = keyword.value
        controller.event.result = keyword.value
    }

}

class EventController : Controller() {

    var event : Event = TextEvent()
    val model : TextEventEditViewModel by inject()

    fun init( instance : Event ) {
        event = instance
    }

}