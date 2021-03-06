package viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class PlayerTakeEventEditViewModel : EventEditViewModel() {

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }

    override fun commit() {
        event.keyword = keyword.value
        event.result = keyword.value
    }

    override fun reset() {
        keyword.value = event.keyword
        result.value = event.result
    }

}