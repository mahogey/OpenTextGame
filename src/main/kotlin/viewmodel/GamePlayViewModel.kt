package viewmodel

import exceptions.ExitCommandException
import exceptions.NoSuchCommandException
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ScrollPane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import game.Game
import main.NULL_VALUE
import tornadofx.*
import views.*

class GamePlayViewModel : ViewModel() {

    private val view : GamePlayView by inject()
    var result: StringProperty = bind{ SimpleStringProperty("") }

    // events combo box
    val events : ObservableList< String > = FXCollections.observableArrayList()
    var eventSelected : StringProperty = bind { SimpleStringProperty( null ) }

    // objects combo box
    val objects : ObservableList< String > = FXCollections.observableArrayList()
    var objectSelected : StringProperty = bind { SimpleStringProperty( null ) }

    private var game : Game = Game()

    fun init( instance : Game) {
        game = instance
        if( game.context.objectId != NULL_VALUE ) {
            objectSelected.value = game.objects[ game.context.verbId ]!!.name
        }
        if( game.context.verbId != NULL_VALUE ) {
            eventSelected.value = game.events[ game.context.verbId ]!!.keyword
        }

        objectSelected.onChange {
            setupEventOptions()
        }
        setupObjectOptions()
    }

    fun onGoButtonClick( flow : TextFlow, scroll : ScrollPane ) {
        try {
            val command : String = eventSelected.value + " " + objectSelected.value
            val actionText : Text  = Text( command + "\n" )
            actionText.fill = Color.GREEN
            flow.children.addAll( actionText )

            val resultText : Text = Text( game.interact( command ) + "\n\n" )
            resultText.fill = Color.WHITE
            flow.children.addAll( resultText )
        }
        catch( e : ExitCommandException ) { }
        catch( e : NoSuchCommandException ) {
            val resultText : Text = Text( e.message + "\n\n" )
            resultText.fill = Color.WHITE
            flow.children.addAll( resultText )
        }
        catch( e : NullPointerException ) {
            e.printStackTrace()
        }
        catch( e : Exception ) {
            e.printStackTrace()
        }

        scroll.layout()
        scroll.vvalue = scroll.vmax
    }

    private fun setupEventOptions() {
        events.clear()
        if( objectSelected.value != null ) {
            events.addAll( game.objects[ game.context.roomId ]!!.objects[ objectSelected.value ]!!.events.keys )
        }
    }

    private fun setupObjectOptions() {
        objects.clear()
        objects.addAll( game.objects[ game.context.roomId ]!!.objects.keys )
    }

}
