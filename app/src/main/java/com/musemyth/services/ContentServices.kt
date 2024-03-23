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
import com.musemyth.R
import com.musemyth.model.Characters
import com.musemyth.model.Storylines
import com.musemyth.model.Student
import com.musemyth.model.UserChar
import com.musemyth.model.UserStoryline
import com.musemyth.screens.char
import com.musemyth.screens.isLoadingStudents
import com.musemyth.screens.story
import com.musemyth.screens.studentChar
import com.musemyth.screens.studentStory
import com.musemyth.screens.students
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var storylineTables by mutableStateOf(emptyList<Storylines>())
var characterTables by mutableStateOf(emptyList<Characters>())
var isLoadingStories by mutableStateOf(false)
var isLoadingCharacters by mutableStateOf(false)
var studentId by mutableStateOf(FirebaseAuth.getInstance().currentUser!!.uid)

class ContentServices {
    fun saveStoryline(map: Map<String, Any>, navController: NavController) {
        isLoading = true
        FirebaseFirestore.getInstance().collection("users").document(
            FirebaseAuth.getInstance().currentUser!!.uid
        ).collection("generatedStory").add(map).addOnCompleteListener { task ->
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
        }.addOnFailureListener { exception ->
            showModal = true; fbError = exception.message!!; isLoading = false
        }
    }

    fun deleteStoryline(id: String, scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid).collection("generatedStory")
            .document(id).delete().addOnCompleteListener {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Storyline Removido com Sucesso!",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
            }.addOnFailureListener { exception ->
                showModal = true; fbError = exception.message!!; isLoading = false
            }
    }

    fun deleteStudentStoryline(
        id: String, studentId: String, scope: CoroutineScope, snackbarHostState: SnackbarHostState
    ) {
        FirebaseFirestore.getInstance().collection("users").document(studentId)
            .collection("generatedStory").document(id).delete().addOnCompleteListener {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Storyline Removido com Sucesso!",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
            }.addOnFailureListener { exception ->
                showModal = true; fbError = exception.message!!; isLoading = false
            }
    }

    fun saveCharacter(map: Map<String, Any>, navController: NavController) {
        isLoading = true
        FirebaseFirestore.getInstance().collection("users").document(
            FirebaseAuth.getInstance().currentUser!!.uid
        ).collection("generatedChar").add(map).addOnCompleteListener { task ->
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
        }.addOnFailureListener { exception ->
            showModal = true; fbError = exception.message!!; isLoading = false
        }
    }

    fun deleteStudentCharacter(
        id: String, studentId: String, scope: CoroutineScope, snackbarHostState: SnackbarHostState
    ) {
        FirebaseFirestore.getInstance().collection("users").document(studentId)
            .collection("generatedChar").document(id).delete().addOnCompleteListener {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Personagem Removido com Sucesso!",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
            }.addOnFailureListener { exception ->
                showModal = true; fbError = exception.message!!; isLoading = false
            }
    }

    fun deleteCharacter(id: String, scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid).collection("generatedChar")
            .document(id).delete().addOnCompleteListener {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Personagem Removido com Sucesso!",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
            }.addOnFailureListener { exception ->
                showModal = true; fbError = exception.message!!; isLoading = false
            }
    }
}

fun fetchStorylinesTables() {
    val storylinesList = arrayListOf<Storylines>()
    isLoadingStories = true
    FirebaseFirestore.getInstance().collection("storyline").addSnapshotListener { value, error ->
        isLoadingStories = false
        if (error != null) {
            return@addSnapshotListener
        }
        if (value != null) {
            storylinesList.clear()

            for (doc in value.documents) {
                val storyline = doc.toObject(Storylines::class.java)
                val newStoryline = Storylines(
                    table = storyline?.table
                )
                storylinesList.add(newStoryline)
            }
        }

        storylineTables = storylinesList
    }
}

fun fetchStudents() {
    isLoadingStudents = true
    FirebaseFirestore.getInstance().collection("users").orderBy("name", Query.Direction.ASCENDING)
        .addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val studentsList = mutableListOf<Student>()
                for (doc in snapshot.documents) {
                    val student = doc.toObject(Student::class.java)
                    val newStudent = Student(
                        id = student?.id ?: "",
                        name = student?.name ?: "",
                        email = student?.email ?: "",
                        accountType = student?.accountType ?: "",
                    )
                    if (newStudent.accountType == "aluno") {
                        studentsList.add(newStudent)
                    }
                }
                students = studentsList
                isLoadingStudents = false
            }
        }
}

fun fetchStudentCharacters(id: String) {
    isLoadingCharacters = true
    FirebaseFirestore.getInstance().collection("users").document(id).collection("generatedChar")
        .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val charsList = mutableListOf<UserChar>()
                for (doc in snapshot.documents) {
                    val character = doc.toObject(UserChar::class.java)
                    val newCharacter = UserChar(
                        time = character?.time ?: "",
                        generatedChar = charactersSortMapKeys(character?.generatedChar),
                        id = character?.id ?: ""
                    )
                    charsList.add(newCharacter)
                }
                studentChar = charsList
                isLoadingCharacters = false
            }
        }
}

fun charactersSortMapKeys(generatedChar: Map<String, Any>?): Map<String, Any> {
    val characterOrder = listOf(
        "Nome",
        "Gênero",
        "Personalidade da Personagem",
        "Arcanos Menores",
        "Afinidade Tecnológica",
        "Nível de Instrução",
        "Espiritualidade",
        "Poder Aquisitivo",
        "Arquétipos-Papéis",
    )
    val sortedMap = LinkedHashMap<String, Any>()
    generatedChar?.let {
        for (key in characterOrder) {
            if (it.containsKey(key)) {
                sortedMap[key] = it[key] ?: ""
            }
        }
    }
    return sortedMap
}

fun fetchUserCharacters() {
    isLoadingCharacters = true
    FirebaseFirestore.getInstance().collection("users")
        .document(FirebaseAuth.getInstance().currentUser!!.uid).collection("generatedChar")
        .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val charsList = mutableListOf<UserChar>()
                for (doc in snapshot.documents) {
                    val character = doc.toObject(UserChar::class.java)
                    val newCharacter = UserChar(
                        time = character?.time ?: "",
                        generatedChar = charactersSortMapKeys(character?.generatedChar),
                        id = character?.id ?: ""
                    )
                    charsList.add(newCharacter)
                }
                char = charsList
                isLoadingCharacters = false
            }
        }
}

fun fetchStudentStorylines(id: String) {
    isLoadingStories = true
    FirebaseFirestore.getInstance().collection("users").document(id).collection("generatedStory")
        .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val storylinesList = mutableListOf<UserStoryline>()
                for (doc in snapshot.documents) {
                    val storyline = doc.toObject(UserStoryline::class.java)
                    val newStoryline = UserStoryline(
                        time = storyline?.time ?: "",
                        generatedStory = storylinesSortMapKeys(storyline?.generatedStory),
                        id = storyline?.id ?: "",
                    )
                    storylinesList.add(newStoryline)
                }
                studentStory = storylinesList
                isLoadingStories = false
            }
        }
}

fun iconByKey(key: String): Int {
    when (key) {
        "Mundo Comum" -> return R.drawable.amundo_comum
        "O Chamado a Aventura" -> return R.drawable.b_chamado_a_aventura
        "A Recusa ao Chamado" -> return R.drawable.c_recusa_ao_chamado
        "Encontro Com o Mentor" -> return R.drawable.d_encontro_com_o_mentor
        "A Travessia do Primeiro Limiar" -> return R.drawable.e_travessia_do_primeiro_limiar
        "Provas; Aliados e Inimigos" -> return R.drawable.f_provas_aliados_e_inimigos
        "Aproximação a Caverna Secreta" -> return R.drawable.g_aproximacao_a_carverna_oculta
        "A Provação" -> return R.drawable.h_provacao
        "Recompensa" -> return R.drawable.i_recompensa
        "Caminho de Volta" -> return R.drawable.j_caminho_de_volta
        "Ressureição" -> return R.drawable.k_ressureicao
        "Elixir" -> return R.drawable.l_elixir
    }
    return R.drawable.ic_launcher_foreground
}

fun storylinesSortMapKeys(generatedChar: Map<String, Any>?): Map<String, Any> {
    val storylineOrder = listOf(
        "Mundo Comum",
        "O Chamado a Aventura",
        "A Recusa ao Chamado",
        "Encontro Com o Mentor",
        "A Travessia do Primeiro Limiar",
        "Provas; Aliados e Inimigos",
        "Aproximação a Caverna Secreta",
        "A Provação",
        "Recompensa",
        "Caminho de Volta",
        "Ressureição",
        "Elixir"
    )
    val sortedMap = LinkedHashMap<String, Any>()
    generatedChar?.let {
        for (key in storylineOrder) {
            if (it.containsKey(key)) {
                sortedMap[key] = it[key] ?: ""
            }
        }
    }
    return sortedMap
}

fun fetchUserStorylines() {
    isLoadingStories = true
    FirebaseFirestore.getInstance().collection("users")
        .document(FirebaseAuth.getInstance().currentUser!!.uid).collection("generatedStory")
        .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val storylinesList = mutableListOf<UserStoryline>()
                for (doc in snapshot.documents) {
                    val storyline = doc.toObject(UserStoryline::class.java)
                    val newStoryline = UserStoryline(
                        time = storyline?.time ?: "",
                        generatedStory = storylinesSortMapKeys(storyline?.generatedStory),
                        id = storyline?.id ?: ""
                    )
                    storylinesList.add(newStoryline)
                }
                story = storylinesList
                isLoadingStories = false
            }
        }
}

fun fetchCharactersTables() {
    val charactersList = arrayListOf<Characters>()
    isLoadingCharacters = true
    FirebaseFirestore.getInstance().collection("character").addSnapshotListener { value, error ->
        isLoadingCharacters = false
        if (error != null) {
            // Handle error
            return@addSnapshotListener
        }
        if (value != null) {
            charactersList.clear()

            for (doc in value.documents) {
                val storyline = doc.toObject(Characters::class.java)
                val newStoryline = Characters(
                    table = storyline?.table
                )
                charactersList.add(newStoryline)
            }
        }
        characterTables = charactersList
    }
}