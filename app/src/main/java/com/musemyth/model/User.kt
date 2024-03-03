package com.musemyth.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.musemyth.services.fetchCharactersTables
import com.musemyth.services.fetchStorylinesTables
import com.musemyth.services.fetchUserProfileData

var isLoadingUser by mutableStateOf(false)
var user by mutableStateOf(User())

class User(
   val name: String? = null,
   val accountType: String? = null,
   val id: String? = null,
   val email: String? = null,
)