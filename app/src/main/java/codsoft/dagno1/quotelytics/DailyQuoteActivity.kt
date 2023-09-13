package codsoft.dagno1.quotelytics

import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.QuotelyticsTheme

class DailyQuoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotelyticsTheme {
                QuotePage()
            }
        }
    }
}

@Composable
fun QuotePage() {
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
                .align(Alignment.Center)
                .padding(horizontal = 15.dp)
        ) {
            QuoteCard()
        }
        FloatingActionButton(
            onClick = {

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