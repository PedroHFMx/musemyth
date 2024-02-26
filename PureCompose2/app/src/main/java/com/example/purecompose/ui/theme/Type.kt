package com.example.purecompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.purecompose.R

//val provider = GoogleFont.Provider(
//    providerAuthority = "com.google.android.gms.fonts",
//    providerPackage = "com.google.android.gms",
//    certificates = R.array.com_google_android_gms_fonts_certs
//)

val poppins = FontFamily(
    Font(R.font.poppins_thin, FontWeight.W200),
    Font(R.font.poppins_medium, FontWeight.W400),
    Font(R.font.poppins_regular, FontWeight.W500),
    Font(R.font.poppins_semibold, FontWeight.W600),
    Font(R.font.poppins_bold, FontWeight.W700),
    Font(R.font.poppins_extrabold, FontWeight.W800),
    Font(R.font.poppins_black, FontWeight.W900),
)

val PoppinsTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W900,
        fontSize = 24.sp,
    ),
)

//val fontName = GoogleFont("Poppins")
//
//val fontFamily = FontFamily(
//    Font(
//        googleFont = fontName,
//        fontProvider = provider,
//    )
//)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Black,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)