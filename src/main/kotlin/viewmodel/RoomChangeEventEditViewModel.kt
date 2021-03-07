package viewmodel

import data.GameObject
import data.Instance
import events.RoomChangeEvent
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import views.RoomChangeEventEditView

class RoomChangeEventEditViewModel : EventEditViewModel() {

    val view : RoomChangeEventEditView by inject()

    // object properties
    var keyword: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty() }

    // list view
    val links : ObservableList< String > = FXCollections.observableArrayList()

    // selected link
    var selectedLink : StringProperty = bind { SimpleStringProperty( ) }

    override fun init( instance: Instance ) {
        super.init( instance )
        links.clear()
        links.addAll( parent.game.objects[ "GAME" ]!!.objects.map{ it.value.name } )
        selectedLink.value = parent.game.objects[ ( event as RoomChangeEvent ).linkId ]!!.name
    }

    override fun commit() {
        event.keyword = keyword.value
        event.result = result.value
        ( event as RoomChangeEvent ).linkId = parent.game.objects[ "GAME" ]!!.objects[ selectedLink.value ]!!.id
    }

    override fun reset() {
        keyword.value = event.keyword
        result.value = event.result
        links.clear()
        links.addAll( parent.game.objects[ "GAME" ]!!.objects.map{ it.value.name } )
        selectedLink.value = parent.game.objects[ ( event as RoomChangeEvent ).linkId ]!!.name
    }

}