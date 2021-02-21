package viewmodel

import data.Context
import data.GameObject
import data.Player
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import model.Game
import main.readObjectFromFileSystem
import tornadofx.*
import views.GameObjectEditView
import views.TextEventEditView
import java.awt.Event

class GameObjectEditViewModel : ViewModel() {

    // combo box
    val options : ObservableList< String > = FXCollections.observableArrayList( "Objects", "Events" )
    var selected : StringProperty = bind { SimpleStringProperty( "Objects" ) }

    // object properties
    var name: StringProperty = bind{ SimpleStringProperty() }

    // list view
    val items : ObservableList< String > = FXCollections.observableArrayList()

}

class GameObjectEditViewController : Controller() {

    var obj : GameObject = GameObject()
    val model : GameObjectEditViewModel by inject()
    private val controller : EventController by inject()

    fun init( instance : GameObject ) {
        obj = instance
        model.selected.onChange { resetListView() }
    }

    fun onChildSelect( child : String ) {
        when( model.selected.value ) {
            "Objects" -> {
                obj = obj.objects[ child ]!!
                workspace.dock< GameObjectEditView >()
            }
            "Events" -> {
                val event = obj.events[ child ]!!
                controller.init( event )
                when( event.type ) {
                    "TEXT_EVENT" -> workspace.dock< TextEventEditView >()
                }

            }
            else -> {
                obj = obj.objects[ child ]!!
                workspace.dock< GameObjectEditView >()
            }
        }

    }

    fun resetView() {
        model.name.value = obj.name
        resetListView()
    }

    private fun resetListView() {
        model.items.clear()
        model.items.addAll( getChildrenKeys() )
    }

    private fun getChildrenKeys() : List< String > {
        return when( model.selected.value ) {
            "Objects" -> ArrayList< String >( obj.objects.keys )
            "Events" -> obj.events.map{ "${it.value.keyword}" }
            else -> ArrayList< String >( obj.objects.keys )
        }
    }

}