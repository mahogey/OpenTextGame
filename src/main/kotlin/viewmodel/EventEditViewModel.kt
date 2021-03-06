package viewmodel

import data.Instance
import events.Event

abstract class EventEditViewModel : GameEditFragmentViewModel() {

    // workspace variables
    lateinit var event : Event

    override fun init( instance : Instance ) {
        event = instance as Event
    }

    final override fun onCreate() { }

    final override fun onDelete() {
        parent.game.events.remove( event.id )
        parent.game.objects[ event.parentId ]!!.events.remove( event.keyword )
    }

    final override fun onSave() {
        commit()
        parent.game.events[ event.id ] = event
    }

}