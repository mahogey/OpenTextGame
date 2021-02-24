package viewmodel

import com.google.gson.GsonBuilder
import data.Context
import data.GameObject
import data.Instance
import data.Player
import javafx.stage.FileChooser
import main.Game
import main.readObjectFromFileSystem
import tornadofx.*
import views.GameObjectEditView

class GameEditViewModel : ViewModel() {

    private val children : HashMap< GameEditFragment, String > = HashMap()

    private var game : Game = Game()
    private lateinit var focus : GameEditFragment

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
    }

    fun onDelete() {
        var obj : GameObject = controller.obj
        var nobj : GameObject = game.objects[ obj.parentId ]!!

        nobj.objects.remove( obj.id )
        game.objects.remove( obj.id )

        controller.obj = nobj
        workspace.dock< GameObjectEditView >()
    }

    fun onSave() {
        val chooser = FileChooser()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson( game )

        chooser.extensionFilters.add( FileChooser.ExtensionFilter( "JSON Files (*.json)", "*.txt" ) )
        val file = chooser.showSaveDialog( primaryStage )

        if( file != null ) {
            file.printWriter().use { out ->
                out.println( json )
            }
        }
    }

    fun onChildDocked( frag : GameEditFragment ) {
        focus = frag
        if( frag in children ) {
            focus.controller.init( game.objects[ children[ frag ]!! ]!! )
        } else {
            children[ frag ] = controller.obj.id
        }
    }

    fun onChildUndocked( frag : GameEditFragment ) {
        frag.model.commit()
    }

}

abstract class GameEditFragment( type : String ) : Fragment() {

    abstract val controller : GameEditFragmentController
    abstract val model : GameEditFragmentViewModel
    private val parent : GameEditViewModel by inject()

    override fun onDock() {
        parent.onChildDocked( this )
        super.onDock()
    }

    override fun onUndock() {
        parent.onChildUndocked( this )
        super.onUndock()
    }

}

abstract class GameEditFragmentController : Controller() {

    abstract fun init( instance : Instance )

    /*
    abstract fun onCreate()
    abstract fun onDelete()
    abstract fun onSave()
     */
}

abstract class GameEditFragmentViewModel : ViewModel() {

    abstract fun commit()
    abstract fun reset()

}