package exceptions

class NoSuchCommandException (
    message : String = "Can't do that!"
)
    : Exception( message ) {}