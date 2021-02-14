package views

import model.Context
import model.Player
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.stage.Stage
import main.Game
import main.readObjectFromFileSystem
import tornadofx.*
import viewmodel.GameObjectEditViewModel

class ExampleApp : App( GameObjectEditView::class ) {

    private val controller : GameController by inject()

    override fun start( stage: Stage ) {
        super.start( stage )
        controller.init()
    }
}

class GameObjectEditView() : View( title = "Game Creator" ) {

    private val controller : GameController by inject()
    var model : GameObjectEditViewModel = GameObjectEditViewModel()

    override val root: Parent = vbox {
        hbox {
            label( "Name" )
            textfield( model.name )
        }
        combobox< String > ( model.selected, FXCollections.observableArrayList( model.options ) ) {

        }
        listview(  model.items ) {
            onUserSelect {
                controller.onChildSelect( it )
            }
        }
        hbox {
            button( "Remove" )
            button( "Add" )
        }
    }

}

class GameController : Controller() {
    private var game : Game = Game()
    private var focusId : String = "NONE"
    private val view : GameObjectEditView by inject()

    fun init() {
        game = Game( Context( "NONE", "NONE", "cell" ), Player( "user" ) )
        try {
            game.loadFromJson( readObjectFromFileSystem( "game.json" ) )
            game.build()
        } catch( e : Exception ) {
            e.printStackTrace()
        }

        view.model.selected.onChange { resetListView() }

        view.model.name.value = "game"
        resetListView()
    }

    fun onChildSelect( child : String ) {
        when( view.model.selected.value ) {
            "Objects" -> focusId = game.objects.filter { it.value.parentId == focusId }[ child ]!!.id
            "Events" -> game.events.filter { it.value.parentId == focusId }[ child ]!!.id
            else -> game.objects.filter { it.value.parentId == focusId }[ child ]!!.id
        }
        view.model.name.value = child
        resetListView()
    }


    private fun resetListView() {
        view.model.items.clear()
        view.model.items.addAll( getChildren().keys )
    }

    private fun getChildren() : Map< String, Any > {
        return when( view.model.selected.value ) {
            "Objects" -> game.objects.filter { it.value.parentId == focusId }
            "Events" -> game.events.filter { it.value.parentId == focusId }
            else -> game.objects.filter { it.value.parentId == focusId }
        }
    }

}