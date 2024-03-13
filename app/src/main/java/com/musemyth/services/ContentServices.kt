package com.musemyth.services

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.musemyth.model.Character
import com.musemyth.model.Storyline
import com.musemyth.model.UserChar
import com.musemyth.model.UserStoryline
import com.musemyth.screens.char
import com.musemyth.screens.story
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var storylineTables by mutableStateOf(emptyList<Storyline>())
var characterTables by mutableStateOf(emptyList<Character>())
var isLoadingStories by mutableStateOf(false)
var isLoadingCharacters by mutableStateOf(false)

class ContentServices {
    fun saveStoryline(map: Map<String, Any>, navController: NavController) {
        isLoading = true
        FirebaseFirestore.getInstance().collection("users").document(
            FirebaseAuth.getInstance().currentUser!!.uid
        ).collection("generatedStory").add(map)
            .addOnCompleteListener { task ->
                task.result.update("id", task.result.id)
                navController.navigate("userStory") {
                    popUpTo("preGenStory") {
                        inclusive = true
                    }
                    popUpTo("genStory") {
                        inclusive = true
                    }
                }
                isLoading = false
            }
            .addOnFailureListener { exception ->
                showModal = true; fbError = exception.message!!; isLoading = false
            }
    }

    fun deleteStoryline(id: String, scope: CoroutineScope, snackbarHostState: SnackbarHostState){
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("generatedStory")
            .document(id)
            .delete()
            .addOnCompleteListener {
                scope.launch {
                    snackbarHostState
                        .showSnackbar(
                            message = "Storyline Removido com Sucesso!",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short,
                        )
                }
            }
            .addOnFailureListener { exception ->
                showModal = true; fbError =
                exception.message!!; isLoading = false
            }
    }

    fun saveCharacter(map: Map<String, Any>, navController: NavController) {
        isLoading = true
        FirebaseFirestore.getInstance().collection("users").document(
            FirebaseAuth.getInstance().currentUser!!.uid
        ).collection("generatedChar").add(map)
            .addOnCompleteListener { task ->
                task.result.update("id", task.result.id)
                navController.navigate("userChar") {
                    popUpTo("preGenChar") {
                        inclusive = true
                    }
                    popUpTo("genChar") {
                        inclusive = true
                    }
                }
                isLoading = false
            }
            .addOnFailureListener { exception ->
                showModal = true; fbError = exception.message!!; isLoading = false
            }
    }

    fun deleteCharacter(id: String, scope: CoroutineScope, snackbarHostState: SnackbarHostState){
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("generatedChar")
            .document(id)
            .delete()
            .addOnCompleteListener {
                scope.launch {
                    snackbarHostState
                        .showSnackbar(
                            message = "Personagem Removido com Sucesso!",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short,
                        )
                }
            }
            .addOnFailureListener { exception ->
                showModal = true; fbError =
                exception.message!!; isLoading = false
            }
    }
}

fun fetchStorylinesTables() {
    val storylinesList = arrayListOf<Storyline>()
    isLoadingStories = true
    FirebaseFirestore.getInstance().collection("storyline")
        .addSnapshotListener { value, error ->
            isLoadingStories = false
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                storylinesList.clear()

                for (doc in value.documents) {
                    val storyline = doc.toObject(Storyline::class.java)
                    val newStoryline = Storyline(
                        table = storyline?.table
                    )
                    storylinesList.add(newStoryline)
                }
            }

            storylineTables = storylinesList
        }
}

fun fetchUserCharacters(){
    FirebaseFirestore.getInstance()
        .collection("users")
        .document(FirebaseAuth.getInstance().currentUser!!.uid)
        .collection("generatedChar")
        .orderBy("time", Query.Direction.ASCENDING)
        .addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val charsList = mutableListOf<UserChar>()
                for (doc in snapshot.documents) {
                    val storyline = doc.toObject(UserChar::class.java)
                    val newStoryline = UserChar(
                        time = storyline?.time ?: "",
                        generatedChar = storyline?.generatedChar,
                        id = storyline?.id ?: ""
                    )
                    charsList.add(newStoryline)
                }
                char = charsList
                com.musemyth.screens.isLoadingCharacters = false
            }
        }
}

fun fetchUserStorylines(){
    FirebaseFirestore.getInstance()
        .collection("users")
        .document(FirebaseAuth.getInstance().currentUser!!.uid)
        .collection("generatedStory")
        .orderBy("time", Query.Direction.ASCENDING)
        .addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val storylinesList = mutableListOf<UserStoryline>()
                for (doc in snapshot.documents) {
                    val storyline = doc.toObject(UserStoryline::class.java)
                    val newStoryline = UserStoryline(
                        time = storyline?.time ?: "",
                        generatedStory = storyline?.generatedStory,
                        id = storyline?.id ?: ""
                    )
                    storylinesList.add(newStoryline)
                }
                story = storylinesList
                com.musemyth.screens.isLoadingStories = false
            }
        }
}

fun fetchCharactersTables() {
    val charactersList = arrayListOf<Character>()
    isLoadingStories = true
    FirebaseFirestore.getInstance().collection("character")
        .addSnapshotListener { value, error ->
            isLoadingStories = false
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }
            if (value != null) {
                charactersList.clear()

                for (doc in value.documents) {
                    val storyline = doc.toObject(Character::class.java)
                    val newStoryline = Character(
                        table = storyline?.table
                    )
                    charactersList.add(newStoryline)
                }
            }
            characterTables = charactersList
        }
}