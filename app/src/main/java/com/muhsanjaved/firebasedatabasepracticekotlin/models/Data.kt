package com.muhsanjaved.firebasedatabasepracticekotlin.models

class Data{
    var data: String? = null
    var key: String? = null

    constructor()

    constructor(data: String?, key:String?){
        this.data = data
        this.key = key
    }

    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
