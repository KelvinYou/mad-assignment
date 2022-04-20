package com.example.assignment

class Ask {
    var askTitle = ""
    var askBody = ""
    var askTags = ""
    //var askImage = 0
    var userID = ""
    constructor(askTitle:String,askBody:String, askTags:String, userID :String){
        this.askTitle = askTitle
        this.askBody = askBody
        this.askTags = askTags
       // this.askImage = askImage
        this.userID = userID
    }
}