package views

import javafx.scene.Parent
import tornadofx.*
import viewmodel.EventSelectorViewModel

class EventSelectorFragment : Fragment( "Event Type" ) {

    private val model : EventSelectorViewModel by inject()

    override var root : Parent = vbox{
        listview( model.options ) {
            onUserSelect {
                model.onChildSelect( it )
                close()
            }
        }
    }

}