package viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class TextEventEditViewModel : EventEditViewModel() {

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }


    override fun commit() {
        event.keyword = keyword.value
        event.result = result.value
    }

    override fun reset() {
        keyword.value = event.keyword
        result.value = event.result
    }

}