package com.kira.android.filipinorecipe.features.component

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kira.android.filipinorecipe.R

@Composable
fun DetailsListSection(title: String, list: List<String>) {
    Text(
        textAlign = TextAlign.Start,
        fontSize = 20.sp,
        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
        modifier = Modifier
            .wrapContentWidth(),
        text = title,
        color = Color.White
    )
    NumberedList(list)
}