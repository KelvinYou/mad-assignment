package com.example.assignment.data

import android.media.Image
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId

data class UserProfile(
//    @DocumentId
    var email : String = "",
    var ic : String = "",
    var password : String = "",
    var gender : Int = 0,
    var image : Blob = Blob.fromBytes(ByteArray(0)),
    var phone : String = "",
    var role : Int = 0,
    var token : String = "",
)
