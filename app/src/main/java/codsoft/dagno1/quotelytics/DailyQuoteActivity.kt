package codsoft.dagno1.quotelytics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import codsoft.dagno1.quotelytics.components.QuoteCard
import codsoft.dagno1.quotelytics.components.SetStatusBarColor
import codsoft.dagno1.quotelytics.data.DBHelper
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.QuotelyticsTheme
import codsoft.dagno1.quotelytics.ui.theme.Silver

class DailyQuoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val dbHelper = DBHelper(context, null)
        val quote = dbHelper.getRandomUnreadQuote()
        quote.isRead = dbHelper.markQuoteAsRead(quote.id)
        Log.d(
            "Read or Not Read",
            "++++++++++++++++++++++++  " +
                    "${quote.id} is read = ${quote.isRead}"
        )
        setContent {
            QuotelyticsTheme {
                SetStatusBarColor(color = Silver)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {

                    Row(
                        modifier = Modifier.align(Alignment.TopStart),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // Add your logo image here
                        Image(
                            painter = painterResource(id = R.drawable.logo_with_name),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .width(200.dp)
                                .height(40.dp)
                        )

                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(horizontal = 5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                val intent = Intent(context, SearchActivity::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "Copy Icon",
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 15.dp)
                    ) {
                        QuoteCard(quote = quote, context = context)
                    }
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(context, FavoritesActivity::class.java)
                            context.startActivity(intent)
                        },
                        containerColor = DarkStateGray,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(vertical = 50.dp, horizontal = 20.dp)
                            .size(68.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.favorites_book),
                            contentDescription = "favorites",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}
