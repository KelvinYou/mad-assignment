package com.example.assignment.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import java.util.*
import kotlin.collections.ArrayList

data class Tutorial (
    @DocumentId
    var id : String = "",
    var title : String = "",
    var content : String = "",
    var status : String = "",
    var createdDate : Date = Date(),
    var modifiedDate : Date = Date(),
    var ownerID : String = "",
    var Image : Blob = Blob.fromBytes(ByteArray(0)),
)