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
import javafx.stage.FileChooser
import main.Game
import main.readObjectFromFileSystem
import tornadofx.*
import views.*

class GamePlayViewModel : ViewModel() {

    var action: StringProperty = bind{ SimpleStringProperty() }
    var result: StringProperty = bind{ SimpleStringProperty("") }


    private var game : Game = Game()

    fun init( game : Game ) {
        this.game = game
    }

    fun onGoButtonClick() {
        try {
            result.value += game.interact( action.value ) + "\n\n"
        }
        catch( e : ExitCommandException) { }
        catch( e : NoSuchCommandException) {
            result.value += e.message
        }
        catch( e : NullPointerException ) {}
        catch( e : Exception ) {
            e.printStackTrace()
        }
    }

}
