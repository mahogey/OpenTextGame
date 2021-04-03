package viewmodel

import base.GameData
import data.NarrativeElement
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class NarrativeEditViewModel : GameEditFragmentViewModel() {

    var narrative : HashMap< String, NarrativeElement > = HashMap< String, NarrativeElement>()

    // list view
    val items : ObservableList< NarrativeElement > = FXCollections.observableArrayList()

    override fun init( instance : Any ) {
        narrative = instance as HashMap< String, NarrativeElement >
    }

    override fun commit() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        items.clear()
        items.addAll( narrative.values ) //parent.game.narrative.map { "${it.value.event.keyword} ${it.value.obj.name}\n${it.value.passage}" } )
    }

    override fun onCreate() {
        TODO("Not yet implemented")
    }

    override fun onDelete() {
        TODO("Not yet implemented")
    }

    override fun onSave() {
        TODO("Not yet implemented")
    }


}