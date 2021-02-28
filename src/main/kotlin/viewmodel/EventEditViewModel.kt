package viewmodel

import data.Instance
import events.Event

abstract class EventEditViewModel : GameEditFragmentViewModel() {

    // workspace variables
    lateinit var event : Event

    override fun init( instance : Instance ) {
        event = instance as Event
    }

    override fun onCreate() { }

    override fun onDelete() {
        parent.game.events.remove( event.id )
    }

    override fun onSave() {
        commit()
        parent.game.events[ event.id ] = event
    }

}