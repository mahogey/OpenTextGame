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

class GameEditViewController : Controller() {

    private var game : Game = Game()
    private val children : HashMap< GameEditFragment, String > = HashMap()

    private val controller : GameObjectEditViewController by inject()

    fun init() {
        game = Game( Context( "NONE", "NONE", "cell" ), Player( "user" ) )
        try {
            game.loadFromJson( readObjectFromFileSystem( "game.json" ) )
            game.build()
        } catch( e : Exception ) {
            e.printStackTrace()
        }

        controller.init( game.objects[ "NONE" ] !! )
        workspace.dock< GameObjectEditView >()
    }

    fun onCreate() {
        var obj : GameObject = controller.obj
        var nobj : GameObject = GameObject( "id" + System.currentTimeMillis(), controller.obj.id, "" )

        obj.objects[ nobj.id ] = nobj
        game.objects[ nobj.id ] = nobj

        controller.obj = nobj
        workspace.dock< GameObjectEditView >()
        //controller.resetView()
    }

    fun onDelete() {
        var obj : GameObject = controller.obj
        var nobj : GameObject = game.objects[ obj.parentId ]!!

        nobj.objects.remove( obj.id )
        game.objects.remove( obj.id )

        controller.obj = nobj
        workspace.dock< GameObjectEditView >()
        //controller.resetView()
    }

    fun onSave() {
    }

    fun onChildDocked( frag : GameEditFragment ) {
        if( frag in children ) {
            controller.obj = game.objects[ children[ frag ]!! ]!!
        } else {
            children[ frag ] = controller.obj.id
        }
    }

}

abstract class GameEditFragment ( type : String ) : Fragment() {

    private val parent : GameEditViewController by inject()

    override fun onDock() {
        parent.onChildDocked( this )
        super.onDock()
    }

}