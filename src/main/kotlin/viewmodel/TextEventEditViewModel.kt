package viewmodel

import data.Context
import data.GameObject
import data.Player
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

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }

}

class TextEventController : Controller() {

    private var event : TextEvent = TextEvent()
    val model : TextEventEditViewModel by inject()

    fun init() {
        model.keyword.value = event.keyword
        model.result.value = event.result
    }

}