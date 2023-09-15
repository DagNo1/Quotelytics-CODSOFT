package codsoft.dagno1.quotelytics.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import codsoft.dagno1.quotelytics.R
import codsoft.dagno1.quotelytics.data.DBHelper
import codsoft.dagno1.quotelytics.data.Quote
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.FavoriteRed
import codsoft.dagno1.quotelytics.ui.theme.Mint
import codsoft.dagno1.quotelytics.ui.theme.WhiteSmoke
import codsoft.dagno1.quotelytics.ui.theme.interFamily

@Composable
fun QuoteCard(quote: Quote, context: Context) {
    var favorite by remember {
        mutableStateOf(
            if (quote.isFavorite) R.drawable.favorite else R.drawable.favorite_outline
        )
    }
    var iconColor by remember {
        mutableStateOf(
            if (quote.isFavorite) FavoriteRed else DarkStateGray
        )
    }
    val dbHelper = DBHelper(context, null)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Mint, shape = RoundedCornerShape(10.dp))
            .shadow(
                elevation = 30.dp,
                spotColor = Mint,
                shape = RoundedCornerShape(10.dp),
            )
            .defaultMinSize(minHeight = 200.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = WhiteSmoke),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {

        Column(
            modifier = Modifier
        ) {
            Text(
                text = quote.content,
                textAlign = TextAlign.Center,
                fontFamily = interFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "~ ${quote.author ?: "Unknown"}",
                textAlign = TextAlign.End,
                fontFamily = interFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(
                onClick = {
                    if (quote.isFavorite) {
                        quote.isFavorite = !dbHelper.markQuoteAsNotFavorite(quoteId = quote.id)
                        favorite = R.drawable.favorite_outline
                        iconColor = DarkStateGray
                        Log.d(
                            "Favorite situation",
                            "++++++++++++++++++++++++  " +
                                    "${quote.id}${quote.isFavorite}"
                        )

                    } else {
                        quote.isFavorite = dbHelper.markQuoteAsFavorite(quoteId = quote.id)
                        favorite = R.drawable.favorite
                        iconColor = FavoriteRed
                        Log.d(
                            "Favorite situation",
                            "++++++++++++++++++++++++  " +
                                    "${quote.id} is favorite = ${quote.isFavorite}"
                        )
                    }
                },
                modifier = Modifier
                    .size(48.dp),
            ) {
                Icon(
                    painter = painterResource(id = favorite),
                    contentDescription = "Favorite Icon",
                    tint = iconColor
                )
            }
            IconButton(
                onClick = {
                    val quoteToCopy = quote.content + "\n   ~ " + quote.author
                    val clipboardManager =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("quote", quoteToCopy)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(context, "Quote copied to clipboard", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.copy),
                    contentDescription = "Copy Icon",
                )
            }
            IconButton(
                onClick = {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = quote.content + "\n   ~ " + quote.author
                    val shareSubject = "Quote from Quotelytic.(The best app to find quotes)"
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                    startActivity(
                        context,
                        Intent.createChooser(sharingIntent, "Share using"),
                        null
                    )

                },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "Share Icon",
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
