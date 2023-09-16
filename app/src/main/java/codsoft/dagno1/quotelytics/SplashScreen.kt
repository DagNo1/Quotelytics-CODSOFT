package codsoft.dagno1.quotelytics

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import codsoft.dagno1.quotelytics.components.SetStatusBarColor
import codsoft.dagno1.quotelytics.ui.theme.Mint
import codsoft.dagno1.quotelytics.ui.theme.QuotelyticsTheme

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotelyticsTheme {

                SetStatusBarColor(color = Mint)
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .height(100.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(R.drawable.name),
                            contentDescription = "Name",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                    }
                }
            }
        }
        Handler().postDelayed({
            val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)

            val targetActivityClass = if (isFirstLaunch) {
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_first_launch", false)
                editor.apply()
                OnboardingActivity::class.java
            } else {
                DailyQuoteActivity::class.java
            }

            val intent = Intent(this, targetActivityClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }, 2000)
    }
}
