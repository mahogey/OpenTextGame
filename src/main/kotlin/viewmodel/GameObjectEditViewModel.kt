package viewmodel

import data.GameObject
import data.Instance
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import main.GAME_ID
import main.NEW_VALUE
import main.UI_EVENT_TAG
import main.UI_OBJECT_TAG
import tornadofx.*
import views.EventSelectorFragment

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
        selected.onChange {
            commit()
            reset()
        }
    }

    override fun reset() {
        name.value = obj.name
        items.clear()
        when( selected.value ) {
            "Objects" -> items.addAll( obj.objects.keys )
            "Events" -> items.addAll( obj.events.map{ it.value.keyword } )
            else -> items.addAll( obj.objects.keys )
        }
    }

    fun onChildSelect( child : String ) {
        when( selected.value ) {
            "Objects" -> {
                parent.dock( UI_OBJECT_TAG, obj.objects[ child ]!!.id )
            }
            "Events" -> {
                parent.dock( UI_EVENT_TAG, obj.events[ child ]!!.id )
            }
            else -> {
                parent.dock( UI_OBJECT_TAG, obj.objects[ child ]!!.id )
            }
        }
    }

    override fun onCreate() {
        val id : String = "id" + System.currentTimeMillis()
        when( selected.value ) {
            "Objects" -> {
                parent.game.objects[ id ] = GameObject( id, obj.id, NEW_VALUE )
                parent.dock( UI_OBJECT_TAG, id )
            }
            "Events" -> {
                find< EventSelectorFragment >().openModal()
            }
            else -> {
                parent.game.objects[ id ] = GameObject( id, obj.id, NEW_VALUE )
                parent.dock( UI_OBJECT_TAG, id )
            }
        }
    }

    override fun onDelete() {
        if( obj.id != GAME_ID ) {
            parent.game.objects.remove( obj.id )
            parent.game.objects[ obj.parentId ]!!.objects.remove( obj.name )
        }
    }

    override fun onSave() {
        parent.game.objects[ obj.id ] = obj
        parent.game.objects[ obj.parentId ]!!.objects[ obj.name ] = obj
    }

}