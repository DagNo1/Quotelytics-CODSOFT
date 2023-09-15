package codsoft.dagno1.quotelytics.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codsoft.dagno1.quotelytics.R
import codsoft.dagno1.quotelytics.activities.ui.theme.QuotelyticsTheme
import codsoft.dagno1.quotelytics.components.QuoteCard
import codsoft.dagno1.quotelytics.data.DBHelper
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.GreenBlue

class FavoritesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val dbHelper = DBHelper(context, null)
        val favoriteQuotes = dbHelper.getFavoriteQuotesSorted()


        setContent {
            QuotelyticsTheme {
                var searchField by remember {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = searchField,
                        onValueChange = { text ->
                            searchField = text
                        },
                        placeholder = { Text(text = "Search favorites") } ,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp).padding(
                                    vertical = 0.dp
                                )
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = GreenBlue,
                            unfocusedBorderColor = DarkStateGray,
                            unfocusedLeadingIconColor = DarkStateGray,
                            focusedLeadingIconColor = GreenBlue
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Black
                        ),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        modifier = Modifier.padding(
                            vertical = 0.dp,
                            horizontal = 30.dp
                        ).fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp),
                        )
                    Divider(
                        color = GreenBlue
                    )
                    LazyColumn {
                        items(favoriteQuotes) { favoriteQuote ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 15.dp)
                            ) {
                                QuoteCard(quote = favoriteQuote, context = context)
                            }
                        }
                    }
                }
            }
        }
    }

}


