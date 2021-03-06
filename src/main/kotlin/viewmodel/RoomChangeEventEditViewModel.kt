package viewmodel

import events.RoomChangeEvent
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class RoomChangeEventEditViewModel : EventEditViewModel() {

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }

    // list view
    val links : ObservableList< String > = FXCollections.observableArrayList()

    // selected link
    var selectedLink : StringProperty = bind { SimpleStringProperty() }

    override fun commit() {
        event.keyword = keyword.value
        event.result = keyword.value
        ( event as RoomChangeEvent ).linkId = selectedLink.value
    }

    override fun reset() {
        keyword.value = event.keyword
        result.value = event.result
        selectedLink.value = ( event as RoomChangeEvent ).linkId
        links.clear()
        links.addAll( parent.game.objects.filterValues { it.parentId == "GAME" }.map{ it.value.name } )
    }

}