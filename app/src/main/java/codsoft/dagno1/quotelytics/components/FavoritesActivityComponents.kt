package codsoft.dagno1.quotelytics.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codsoft.dagno1.quotelytics.FavoritesActivity
import codsoft.dagno1.quotelytics.R
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.GreenBlue
import codsoft.dagno1.quotelytics.ui.theme.interFamily
import codsoft.dagno1.quotelytics.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown( onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Sort By") }
    val items = listOf("Quote", "Author")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { /* Handle value change if needed */ },
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .menuAnchor()
                .width(200.dp)
                .height(50.dp),
            textStyle = TextStyle(
                fontSize = 13.sp,
                fontFamily = interFamily
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreenBlue,
                unfocusedBorderColor = DarkStateGray,
                unfocusedLeadingIconColor = DarkStateGray,
                focusedLeadingIconColor = GreenBlue
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.drop_down),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(vertical = 0.dp)
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        onItemSelected(item)
                    },
                    modifier = Modifier.height(50.dp)
                )
            }
        }
    }
}

@Composable
fun AppBar(favoritesActivity: FavoritesActivity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                favoritesActivity.finish()
            },
            modifier = Modifier
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Back Icon",
                tint = DarkStateGray
            )
        }
        Text(
            text = "Favorites",
            color = DarkStateGray,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = interFamily,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}
