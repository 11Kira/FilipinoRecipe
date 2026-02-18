package com.kira.android.filipinorecipe.features.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kira.android.filipinorecipe.R
import com.kira.android.filipinorecipe.utils.ColorUtils

@Composable
fun DetailsListSection(text: String, isAnchorHeader: Boolean, protein: String, list: List<String>) {
    Text(
        textAlign = TextAlign.Start,
        fontSize = if (isAnchorHeader) 22.sp else 16.sp,
        fontFamily = if (isAnchorHeader) Font(R.font.roboto_bold).toFontFamily() else Font(R.font.roboto_medium).toFontFamily(),
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 16.dp),
        text = text,
        color = if (isAnchorHeader) Color.White else ColorUtils().getSubHeaderColor(protein)
    )
    NumberedList(list)
}