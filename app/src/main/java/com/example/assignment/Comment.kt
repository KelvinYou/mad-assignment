package com.example.assignment

/*data class Comment(
    var ansQuestionTitle: String ? = null,
    var ansComment: String ? = null,
    var ansDate: String ? = null,
    var ansIdName: String ? = null
)*/

class Comment {
    /*var ansQuestionTitle = ""
    var ansComment = ""
    var ansDate = ""
    var ansIdName = ""*/

    var ansQuestionTitle: String ? = null
    var ansComment: String ? = null
    var ansDate: String ? = null
    var ansIdName: String ? = null

    constructor(){

    }

    constructor(ansQuestionTitle:String,ansComment:String, ansDate:String, ansIdName:String){
        this.ansQuestionTitle = ansQuestionTitle
        this.ansComment = ansComment
        this.ansDate = ansDate
        this.ansIdName = ansIdName
    }
}