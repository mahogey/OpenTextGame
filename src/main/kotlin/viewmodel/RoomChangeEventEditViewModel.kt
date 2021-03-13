package viewmodel

import data.GameObject
import data.Instance
import events.RoomChangeEvent
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import main.GAME_ID
import main.NULL_VALUE
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
        links.addAll( parent.game.objects[ GAME_ID ]!!.objects.map{ it.value.name } )
        setLinkValue()
    }

    override fun commit() {
        event.keyword = keyword.value
        event.result = result.value
        if( selectedLink.value in parent.game.objects[ GAME_ID ]!!.objects ) {
            ( event as RoomChangeEvent ).linkId = parent.game.objects[ GAME_ID ]!!.objects[ selectedLink.value ]!!.id
        }
    }

    override fun reset() {
        keyword.value = event.keyword
        result.value = event.result
        links.clear()
        links.addAll( parent.game.objects[ GAME_ID ]!!.objects.map{ it.value.name } )
        setLinkValue()
    }

    private fun setLinkValue() {
        if( ( event as RoomChangeEvent ).linkId in parent.game.objects ) {
            selectedLink.value = parent.game.objects[ ( event as RoomChangeEvent ).linkId ]!!.name
        } else {
            selectedLink.value = NULL_VALUE
        }
    }

}