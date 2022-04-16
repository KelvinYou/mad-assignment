package com.example.assignment.data

import com.google.firebase.firestore.DocumentId
import java.util.*

data class TutorialComment (
//    @DocumentId
    var id : String = "",
    var content : String = "",
    var date : Date = Date(),
    var userID : String = "",
    var tutorialID : String = ""
)