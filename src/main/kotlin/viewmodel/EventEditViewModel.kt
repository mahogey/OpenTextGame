package viewmodel

import base.GameData
import events.Event

abstract class EventEditViewModel : GameEditFragmentViewModel() {

    // workspace variables
    lateinit var event : Event

    override fun init( instance : Any ) {
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
        parent.game.objects[ event.parentId ]!!.events[ event.keyword ] = event
    }

}