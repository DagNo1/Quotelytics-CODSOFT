package codsoft.dagno1.quotelytics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codsoft.dagno1.quotelytics.components.QuoteCard
import codsoft.dagno1.quotelytics.components.SetStatusBarColor
import codsoft.dagno1.quotelytics.data.DBHelper
import codsoft.dagno1.quotelytics.data.Quote
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.GreenBlue
import codsoft.dagno1.quotelytics.ui.theme.QuotelyticsTheme
import codsoft.dagno1.quotelytics.ui.theme.Silver
import codsoft.dagno1.quotelytics.ui.theme.interFamily
import codsoft.dagno1.quotelytics.ui.theme.poppinsFamily

class FavoritesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val dbHelper = DBHelper(context, null)
        val favoriteQuotes = dbHelper.getFavoriteQuotesSorted()

        setContent {
            QuotelyticsTheme {
                SetStatusBarColor(color = Silver)

                var searchField by remember { mutableStateOf("") }
                var searchedQuotes by remember { mutableStateOf(listOf<Quote>()) }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                finish()
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
                    OutlinedTextField(
                        value = searchField,
                        onValueChange = { text ->
                            searchField = text
                            searchedQuotes = dbHelper.searchQuotes(text)
                        },
                        placeholder = { Text(text = "Search a favorite quote") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(vertical = 0.dp)
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
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Medium,
                            color = Black,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .width(350.dp)
                            .height(58.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = GreenBlue)
                    LazyColumn {
                        items(
                            if (searchField == "") favoriteQuotes else searchedQuotes
                        ) { quote ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 15.dp)
                            ) {
                                QuoteCard(quote = quote, context = context)
                            }
                        }
                    }
                }
            }
        }
    }
}

