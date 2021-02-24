package views

import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import viewmodel.*

class GameEditView() : Workspace( title = "Game Creator" ) {

    private val model : GameEditViewModel by inject()

    override fun onCreate() {
        model.onCreate()
        super.onCreate()
    }

    override fun onDelete() {
        model.onDelete()
        super.onDelete()
    }

    override fun onSave() {
        model.onSave()
        super.onSave()
    }

}
