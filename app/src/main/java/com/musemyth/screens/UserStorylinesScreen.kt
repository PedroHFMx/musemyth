package com.musemyth.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.musemyth.model.UserStoryline

@Composable
fun UserStorylinesScreen(navController: NavController) {

    var isLoadingStories by remember { mutableStateOf(false) }
    var story by remember { mutableStateOf(emptyList<UserStoryline>()) }

    fun fetchStorylines() {
        isLoadingStories = true
        val storylinesList = arrayListOf<UserStoryline>()

        FirebaseFirestore.getInstance().collection("users")
            .document("Mqi76vjdvMbsKvVH9GZUW8tAoky1").collection("generatedStory")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (doc in task.result) {
                        val storyline = doc.toObject(UserStoryline::class.java)
                        val newStoryline = UserStoryline(
                            time = storyline.time, generatedStory = storyline.generatedStory,
                            id = storyline.id
                        )
                        storylinesList.add(newStoryline)
                    }
                    story = storylinesList
                }
            }
    }

    LaunchedEffect(true) {
        fetchStorylines()
    }

    LazyColumn(content = {
        itemsIndexed(story) { _, storyH ->
            Text(text = "${storyH.time}")
        }
    })



}
