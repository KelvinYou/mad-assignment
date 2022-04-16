package com.example.assignment.data

import com.google.firebase.firestore.DocumentId
import java.util.*

data class TutorialLike (
//    @DocumentId
    var id : String = "",
    var date : Date = Date(),
    var tutorialID : String = "",
    var userID : String = "",
)