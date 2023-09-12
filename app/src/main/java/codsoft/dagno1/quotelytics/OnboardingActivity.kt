package codsoft.dagno1.quotelytics

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codsoft.dagno1.quotelytics.data.OnboardingItem
import codsoft.dagno1.quotelytics.ui.theme.DarkStateGray
import codsoft.dagno1.quotelytics.ui.theme.QuotelyticsTheme
import codsoft.dagno1.quotelytics.ui.theme.interFamily

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onboardingData = listOf(
            OnboardingItem(
                R.drawable.inspiration,
                R.string.onboarding_title0,
                R.string.onboarding_body0
            ),
            OnboardingItem(
                R.drawable.reading,
                R.string.onboarding_title1,
                R.string.onboarding_body1
            ),
            OnboardingItem(
                R.drawable.searching,
                R.string.onboarding_title2,
                R.string.onboarding_body2
            )
        )
        setContent {
            QuotelyticsTheme {
                CustomSlider(onboardingItems = onboardingData, activity = this)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    onboardingItems: List<OnboardingItem>,
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    activity: Activity,
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize(),
                pageCount = onboardingItems.size,
            ) { page ->
                val item = onboardingItems.getOrNull(page)
                if (item != null) {
                    // Display the content of the onboarding item
                    // You can customize this section based on your item structure
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Display the image
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight)
                                .clip(RoundedCornerShape(imageCornerRadius))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Display the title
                        Text(
                            text = stringResource(id = item.titleResId),
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = DarkStateGray
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Display the body text
                        Text(
                            text = stringResource(id = item.bodyResId),
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                color = DarkStateGray
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Dot indicators for navigation
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 75.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(onboardingItems.size) { index ->
                    val color =
                        if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
                    val width = if (pagerState.currentPage == index) 50.dp else 25.dp
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .height(7.dp)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (pagerState.currentPage == 2) {
                    Button(
                        onClick = {
                            val intent = Intent(activity, DailyQuoteActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            activity.startActivity(intent)
                        },
                        modifier = Modifier.height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text(
                            text = stringResource(id = R.string.get_first_quote),
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = DarkStateGray
                            ),
                        )
                        Image(
                            painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "Cart button icon",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(45.dp))
                }
            }
        }


    }
}



