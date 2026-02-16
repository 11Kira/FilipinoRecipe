package com.kira.android.filipinorecipe.features.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kira.android.filipinorecipe.R

@Composable
fun NumberedList(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "${index + 1}.",
                    fontSize = 15.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(
                    text = item,
                    fontSize = 15.sp,
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}