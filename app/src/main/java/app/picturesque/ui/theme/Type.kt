package app.picturesque.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.picturesque.R

val sourceSansPro = FontFamily(
    Font(R.font.source_sans_pro_regular),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold),
    Font(R.font.source_sans_pro_light, FontWeight.Thin)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

)