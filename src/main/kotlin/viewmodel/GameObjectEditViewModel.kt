package viewmodel

import com.google.gson.GsonBuilder
import data.GameObject
import data.Instance
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.stage.FileChooser
import tornadofx.*
import views.EventSelectorFragment
import views.GameObjectEditView
import views.TextEventEditView

class GameObjectEditViewModel : GameEditFragmentViewModel() {

    // combo box
    val options : ObservableList< String > = FXCollections.observableArrayList( "Objects", "Events" )
    var selected : StringProperty = bind { SimpleStringProperty( "Objects" ) }

    // object properties
    var name: StringProperty = bind{ SimpleStringProperty() }

    // list view
    val items : ObservableList< String > = FXCollections.observableArrayList()


    // model
    var obj : GameObject = GameObject()

    override fun commit() {
        obj.name = name.value
    }

    override fun init( instance : Instance ) {
        obj = instance as GameObject
        selected.onChange { reset() }
    }

    override fun reset() {
        name.value = obj.name
        items.clear()
        when( selected.value ) {
            "Objects" -> items.addAll( obj.objects.keys )
            "Events" -> items.addAll( obj.events.map{ "${it.value.keyword}" } )
            else -> items.addAll( obj.objects.keys )
        }
    }

    fun onChildSelect( child : String ) {
        when( selected.value ) {
            "Objects" -> {
                parent.dock( "Object", obj.objects[ child ]!!.id )
            }
            "Events" -> {
                parent.dock( "Event", obj.events[ child ]!!.id )
            }
            else -> {
                parent.dock( "Object", obj.objects[ child ]!!.id )
            }
        }
    }

    override fun onCreate() {
        val id : String = "id" + System.currentTimeMillis()
        when( selected.value ) {
            "Objects" -> {
                parent.game.objects[ id ] = GameObject( id, obj.id, "" )
                parent.dock( "Object", id )
            }
            "Events" -> {
                find< EventSelectorFragment >().openModal()
            }
            else -> {
                parent.game.objects[ id ] = GameObject( id, obj.id, "" )
                parent.dock( "Object", id )
            }
        }
    }

    override fun onDelete() {
        parent.game.objects.remove( obj.id )
    }

    override fun onSave() {
        commit()
        parent.game.objects[ obj.id ] = obj
    }

}