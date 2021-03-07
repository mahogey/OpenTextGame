package main

import com.google.gson.GsonBuilder
import java.io.File

fun writeObjectToFileSystem( obj : Any, file : File ) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson( obj )
    file.printWriter().use { out ->
        out.println( json )
    }
}

fun readObjectFromFileSystem( file : File ) : String {
    var json = ""
    file.useLines { lines ->
        lines.forEach {
            json += it
        }
    }
    return json
}