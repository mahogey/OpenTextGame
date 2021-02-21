package views

import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.*

class GameEditView() : Workspace( title = "Game Creator" ) {

    private val controller : GameEditViewController by inject()

    override fun onCreate() {
        controller.onCreate()
        super.onCreate()
    }

    override fun onDelete() {
        controller.onDelete()
        super.onDelete()
    }

    override fun onSave() {
        controller.onSave()
        super.onSave()
    }

}
