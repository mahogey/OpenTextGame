package data

import tornadofx.Fragment
import tornadofx.ViewModel

interface Instance {
    abstract val id : String
    abstract var parentId : String?
}