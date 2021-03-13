package viewmodel

import com.google.gson.GsonBuilder
import data.Context
import data.GameObject
import data.Instance
import data.Player
import events.Event
import events.RoomChangeEvent
import exceptions.ExitCommandException
import exceptions.NoSuchCommandException
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import javafx.stage.FileChooser
import main.Game
import main.readObjectFromFileSystem
import tornadofx.*
import views.*

class GamePlayViewModel : ViewModel() {

    var action: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty("") }

    val commands : ObservableList<Node> = FXCollections.observableArrayList()

    private var game : Game = Game()

    fun init( game : Game ) {
        this.game = game
    }

    fun onGoButtonClick( flow : TextFlow, scroll : ScrollPane ) {
        try {
            val actionText : Text  = Text( action.value + "\n" )
            actionText.fill = Color.GREEN
            flow.children.addAll( actionText )

            val resultText : Text = Text( game.interact( action.value ) + "\n\n" )
            resultText.fill = Color.WHITE
            flow.children.addAll( resultText )


        }
        catch( e : ExitCommandException) { }
        catch( e : NoSuchCommandException) {
            val resultText : Text = Text( e.message + "\n\n" )
            resultText.fill = Color.WHITE
            flow.children.addAll( resultText )
        }
        catch( e : NullPointerException ) {}
        catch( e : Exception ) {
            e.printStackTrace()
        }

        scroll.layout()
        scroll.vvalue = scroll.vmax
    }

}
