package com.kira.android.filipinorecipe.features.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kira.android.filipinorecipe.R

@Composable
fun RoundedTextWithIcon(text: String, icon: ImageVector, backgroundColor: Long, textColor: Long) {
    Row(
        modifier = Modifier
            .background(
                color = Color(backgroundColor),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Time",
            tint = Color(textColor),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            fontFamily = Font(R.font.roboto_medium).toFontFamily(),
            fontSize = 10.sp,
            text = text,
            color = Color(textColor)
        )
    }
}