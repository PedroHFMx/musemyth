@file:OptIn(ExperimentalMaterial3Api::class)

package com.musemyth.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.musemyth.R
import com.musemyth.model.UserStoryline
import com.musemyth.services.user
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.tertiary
import java.io.File
import java.io.FileOutputStream

var storyIndex by mutableIntStateOf(0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LookStorylineScreen(navController: NavController? = null) {
    val context = LocalContext.current
    val fakeIndex = storyIndex + 1
    val isEven = storyIndex % 2 == 0
    val systemUiController = rememberSystemUiController()
    lateinit var storyH: Map<Any, Any>
    val generatedStory: MutableMap<Any, Any> = mutableMapOf()
    systemUiController.setSystemBarsColor(if (isEven) secondary else Color(0xFFC05AAA))

    fun storyPathHandle(): List<UserStoryline> {
        return if (user.accountType == "aluno") {
            story
        } else {
            studentStory
        }
    }

    storyPathHandle()[charIndex].generatedStory?.forEach { item ->
        storyH = mapOf(item.key to item.value)
        generatedStory.putAll(storyH)
    }

    fun shareBitmap(context: Context, text: String) {
        val file = File(context.cacheDir, "screenshot.png")
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.flush()
        fileOutputStream.close()

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        context.startActivity(
            Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                },
                "Compartilhar Storyline"
            )
        )
    }

    Scaffold(topBar = {
        Box(
            Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.Black)
                .shadow(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.common_world),
                contentDescription = "character",
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            )
            IconButton(onClick = { navController!!.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = "go back",
                    tint = Color.White
                )
            }
            if (user.accountType == "professor")
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp), Alignment.BottomCenter
                ) {
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .background(if (isEven) secondary else Color(0xFFC05AAA)),
                        Alignment.Center
                    ) {
                        Text(
                            text = "Gerado por: $studentName",
                            modifier = Modifier.padding(20.dp, 12.dp),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp), Alignment.TopEnd
            ) {
                Row(
                    Modifier,
                    Arrangement.spacedBy(if (user.accountType == "aluno") 0.dp else 10.dp)
                ) {
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(if (isEven) secondary else Color(0xFFC05AAA)),
                        Alignment.Center
                    ) {
                        Text(
                            text = fakeIndex.toString().padStart(2, '0'),
                            Modifier.padding(8.dp),
                            fontSize = 20.sp,
                            fontFamily = Poppins,
                            color = Color.White
                        )
                    }
                    if (user.accountType == "professor")
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                                .clickable {
                                    shareBitmap(
                                        context, "Storyline de $studentName: " +
                                                generatedStory
                                                    .toString()
                                                    .replace("{", "")
                                                    .replace("}", "")
                                                    .replace("=", ": ")
                                    )
                                }
                                .background(if (isEven) secondary else Color(0xFFC05AAA)),
                            Alignment.Center
                        ) {
                            Icon(
                                Icons.Rounded.Share, contentDescription = "share this storyline",
                                tint = Color.White
                            )
                        }
                }
            }
        }
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            storyPathHandle()[storyIndex].generatedStory?.forEach { storyline ->
                Row(
                    Modifier.fillMaxSize(), Arrangement.spacedBy(16.dp), Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .size(56.dp)
                            .background(if (isEven) secondary else Color(0xFFC05AAA)),
                        Alignment.Center
                    ) {
                        Text(
                            storyline.key[0] + storyline.key[1].toString().trim().padEnd(
                                1, storyline.key[2]
                            ), Modifier.padding(16.dp), color = Color.White, fontSize = 15.sp
                        )
                    }
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = storyline.key,
                            color = if (isEven) secondary else Color(0xFFC05AAA),
                        )
                        if (storyline.value != "") Text(text = "${storyline.value}")
                        else Divider(Modifier.padding(top = 16.dp))
                    }
                }
            }
        }

    }
}