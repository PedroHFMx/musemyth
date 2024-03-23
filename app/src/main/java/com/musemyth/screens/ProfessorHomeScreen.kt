package com.musemyth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areSystemBarsVisible
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.musemyth.model.Student
import com.musemyth.services.fetchStudentCharacters
import com.musemyth.services.fetchStudentStorylines
import com.musemyth.services.studentId
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.tertiary

var isLoadingStudents by mutableStateOf(false)
var students by mutableStateOf(emptyList<Student>())
var studentName by mutableStateOf("")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfessorHomeScreen(navController: NavController) {
    val bottomSheetState = remember { mutableStateOf(false) }

    if (bottomSheetState.value) {
        Dialog(
            onDismissRequest = { bottomSheetState.value = false }, properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(Modifier.fillMaxSize(), Alignment.BottomEnd) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable { bottomSheetState.value = false })
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(2.dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Box(Modifier.weight(1f))
                            Text(
                                text = "Escolha quais gerações de\n${studentName} quer ver:",
                                textAlign = TextAlign.Center
                            )
                            IconButton({ bottomSheetState.value = false }, Modifier.weight(1f)) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Dismiss Modal"
                                )
                            }
                        }
                        Row {
                            Button(
                                onClick = {
                                    bottomSheetState.value = false
                                    fetchStudentStorylines(studentId)
                                    navController.navigate("userStory")
                                },
                                Modifier
                                    .height(50.dp)
                                    .border(0.1.dp, Color.Black, RoundedCornerShape(10.dp))
                                    .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                                    .weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = secondary
                                )
                            ) {
                                Text(text = "Storylines", fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.padding(4.dp))
                            Button(
                                onClick = {
                                    bottomSheetState.value = false
                                    fetchStudentCharacters(studentId)
                                    navController.navigate("userChar")
                                },
                                Modifier
                                    .height(50.dp)
                                    .border(0.1.dp, Color.Black, RoundedCornerShape(10.dp))
                                    .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                                    .weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primary
                                )
                            ) {
                                Text(text = "Personagens", fontSize = 14.sp)
                            }
                        }
                        if (WindowInsets.areSystemBarsVisible) {
                            Spacer(
                                Modifier.windowInsetsBottomHeight(
                                    WindowInsets.systemBars
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    Column(
        Modifier
            .background(tertiary)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Lista de Alunos:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        if (!isLoadingStudents) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                if(students.isNotEmpty()) Arrangement.spacedBy(10.dp) else Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                if (students.isNotEmpty()) {
                    students.forEachIndexed { index, student ->
                        val nameSplit = student.name!!.split(" ")
                        val isEven = index % 2 == 0
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.White, RoundedCornerShape(10.dp)
                                )
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    studentId = student.id!!
                                    studentName = student.name
                                    bottomSheetState.value = true
                                }) {
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                Arrangement.spacedBy(16.dp),
                                Alignment.CenterVertically
                            ) {
                                Box(
                                    Modifier
                                        .clip(CircleShape)
                                        .size(55.dp)
                                        .background(if (!isEven) primary else secondary),
                                    Alignment.Center
                                ) {
                                    Text(
                                        "${nameSplit[0][0]}${nameSplit[1][0]}",
                                        Modifier.padding(16.dp),
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                }
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "${student.name}",
                                    fontFamily = Poppins,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )

                            }
                        }
                    }
                } else {
                    Box(Modifier.fillMaxSize(), Alignment.Center){
                        Text(text = "Nenhum aluno cadastrado!", textAlign = TextAlign.Center)
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator(color = primary)
            }
        }
    }

}