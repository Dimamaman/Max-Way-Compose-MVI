package uz.gita.dimaa.mymaxway.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.gita.dimaa.mymaxway.R

@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    Box(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))


    ) {
        val trailingIconView = @Composable {
            IconButton(
                onClick = {
                    onValueChange("")
                }
            ) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = search,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF050505)
            ),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = ""
                )
            },
            trailingIcon = if (search.isNotBlank()) trailingIconView else null,
            placeholder = { Text(text = "Search...", color = Color.Gray) }
        )
    }
}