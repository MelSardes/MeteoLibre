package com.sardes.meteolibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sardes.meteolibre.ui.theme.DarkBlue
import com.sardes.meteolibre.ui.theme.MeteoLibreTheme
import com.sardes.meteolibre.ui.theme.RegularBlue
import com.sardes.meteolibre.ui.theme.SoftBlue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeteoLibreTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Meteo(onClick = { navController.navigate("week") })
                    }

                    composable("week") {
                        WeekMeteo()
                    }
                }
            }
        }
    }
}

@Composable
fun Meteo(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(10.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            MainInfos()

            Bottom(onClick = { onClick.invoke() })
        }
    }
}

@Composable
private fun Bottom(onClick: () -> Unit) {
    Column(modifier = Modifier.padding(top = 10.dp, start = 25.dp, end = 25.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ajourd'hui", style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                )
            )

            TextButton(
                onClick = { onClick.invoke() }
            ) {
                Text(
                    text = "7 jours >", style = TextStyle(
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TimePrediction(
                "23°",
                R.drawable.rain_cloud,
                "22:00",
                modifier = Modifier.weight(0.7f)
            )

            TimePrediction(
                "21°",
                R.drawable.three_yellow_lightning_bolts,
                "23:00",
                Modifier.weight(0.7f),
                true,
            )

            TimePrediction(
                "22°",
                R.drawable.rain_cloud,
                "00:00",
                modifier = Modifier.weight(0.7f)
            )

            TimePrediction(
                "19°",
                R.drawable.night,
                "01:00",
                modifier = Modifier.weight(0.7f)
            )
        }
    }
}

@Composable
private fun TimePrediction(
    temperature: String,
    weatherPicture: Int,
    time: String,
    modifier: Modifier = Modifier,
    isNow: Boolean = false,
) {
    Column(
        when (isNow) {
            false -> modifier
                .border(
                    0.5.dp,
                    Color.Gray.copy(alpha = 0.3f),
                    RoundedCornerShape(25.dp)
                )
                .padding(horizontal = 1.dp, vertical = 3.dp)

            true -> modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFF4CC2FF))
                .padding(horizontal = 3.dp, vertical = 3.dp)
        },
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = temperature, style = TextStyle(
                color = Color.White,
                fontSize = when (isNow) {
                    true -> 20.sp
                    false -> 15.sp
                },
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
        )

        Image(
            painter = painterResource(id = weatherPicture),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight(0.7f),
            contentScale = ContentScale.Fit
        )

        Text(
            text = time, style = TextStyle(
                color = Color.White,
                fontSize = if (isNow) 15.sp else 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainInfos() {
    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .clip(RoundedCornerShape(20))
            .background(
                brush = Brush.verticalGradient(
                    listOf(SoftBlue, RegularBlue)
                )
            )
            .padding(top = 30.dp, start = 30.dp, end = 30.dp)
    ) {
        Top(
            R.drawable.buttons_group_icon_nofill,
            R.drawable.place_icon,
            R.drawable.more_icon,
            "Libreville"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Update()

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.lightning_cloud),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "21",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 150.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.SansSerif,

                    ),
                )

                Text(
                    text = "Tonneres", style = TextStyle(
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,

                    )
                )

                Text(
                    text = "Lundi, 27 Mars", style = TextStyle(
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Stats()
        }
    }
}

@Composable
private fun Stats() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    ) {
        StatItem(
            "13km/h",
            "Vent",
            R.drawable.wind_icon,
            modifier = Modifier.weight(1f)
        )

        StatItem(
            value = "24%",
            type = "Humidité",
            image = R.drawable.water_drop_icon,
            modifier = Modifier.weight(1f),
            tintColor = Color(0xFF2196F3),
        )

        StatItem(
            value = "87%",
            type = "Chance de pluie",
            image = R.drawable.water_icon,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatItem(
    value: String,
    type: String,
    image: Int,
    modifier: Modifier = Modifier,
    tintColor: Color = Color.White,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = null,
            tint = tintColor
        )

        Text(
            text = value, style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
            )
        )

        Text(
            text = type, style = TextStyle(
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
            )
        )
    }
}

@Composable
private fun Update() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(1.dp, Color.White, CircleShape)
            .padding(10.dp, 3.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(5.dp)
                .clip(CircleShape)
                .background(Color.Yellow)
        )

        Text(text = "Mise a jour", color = Color.White, fontSize = 12.sp)
    }
}

@Composable
private fun Top(leadingIcon: Int, textIcon: Int, trailingIcon: Int, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = leadingIcon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .border(1.dp, Color.White, CircleShape)
                .padding(6.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = textIcon),
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = text, style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }

        Icon(
            painter = painterResource(id = trailingIcon),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp"
)
/*@Preview(
    showBackground = true,
    device = "id:pixel_3", showSystemUi = true, name = "Pixel 3"
)
@Preview(
    showBackground = true,
    device = "id:pixel_2", showSystemUi = true, name = "Pixel 2"
)
@Preview(
    showBackground = true,
    device = "id:pixel_4", showSystemUi = true, name = "Pixel 4"
)
@Preview(
    showBackground = true,
    device = "id:pixel_5", showSystemUi = true, name = "Pixel 5"
)
@Preview(
    showBackground = true,
    device = "id:pixel_6", showSystemUi = true, name = "Pixel 6"
)*/
@Composable
fun GreetingPreview() {
    MeteoLibreTheme {
        Meteo {}
    }
}


@Preview(device = "spec:width=411dp,height=891dp", showBackground = true)
@Composable
fun WeekMeteo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopCard7Days()

            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DayRow(
                    "Lun",
                    R.drawable.rain_cloud,
                    "Pluvieux",
                    "+20°",
                    "+14°"
                )

                DayRow(
                    "Mar",
                    R.drawable.rain_cloud,
                    "Pluvieux",
                    "+22°",
                    "+16°"
                )

                DayRow(
                    "Mer",
                    R.drawable.storm,
                    "Pluie orageuse",
                    "+19°",
                    "+13°"
                )

                DayRow(
                    "Jeu",
                    R.drawable.night,
                    "Doux",
                    "+18°",
                    "+12°"
                )

                DayRow(
                    "Ven",
                    R.drawable.three_yellow_lightning_bolts,
                    "Orage",
                    "+23°",
                    "+19°"
                )

                DayRow(
                    "Sam",
                    R.drawable.rain_cloud,
                    "Pluvieux",
                    "+25°",
                    "+17°"
                )

                DayRow(
                    "Dim",
                    R.drawable.storm,
                    "Pluie orageuse",
                    "+21°",
                    "+18°"
                )
            }
        }
    }
}

@Composable
private fun DayRow(
    day: String,
    weatherIcon: Int,
    weatherTitle: String,
    maxTemperature: String,
    minTemperature: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = day,
            style = TextStyle(
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
            ),
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier
                .weight(2f)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),

            ) {
            Image(
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                text = weatherTitle,
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                ),
            )
        }

        Text(
            buildAnnotatedString {
                withStyle(style = ParagraphStyle(textAlign = TextAlign.Start)) {
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(maxTemperature)
                    }

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.5f), fontSize = 17.sp
                        )
                    ) {
                        append(minTemperature)
                    }
                }
            },
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TopCard7Days() {
    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .clip(RoundedCornerShape(20))
            .background(
                brush = Brush.verticalGradient(
                    listOf(SoftBlue, RegularBlue)
                )
            )
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Top(
            leadingIcon = R.drawable.arrow_back_icon,
            textIcon = R.drawable.date_icon,
            trailingIcon = R.drawable.more_icon,
            text = "7 jours"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.lightning_cloud),
                contentDescription = null,
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillHeight
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Demain",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,
                    ),
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = ParagraphStyle(textAlign = TextAlign.Start)) {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 70.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("21")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White.copy(alpha = 0.5f), fontSize = 25.sp
                                    )
                                ) {
                                    append("/17°")
                                }
                            }
                        }
                    )

                }
                Text(
                    text = "Pluvieux - Nuageux",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,
                        ),
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.2f))
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            StatItem(
                "9km/h",
                "Vent",
                R.drawable.wind_icon,
                modifier = Modifier.weight(1f)
            )
            StatItem(
                value = "31%",
                type = "Humidité",
                image = R.drawable.water_drop_icon,
                tintColor = Color(0xFF2196F3),
                modifier = Modifier.weight(1f)
            )
            StatItem(
                value = "93%",
                type = "Chance de pluie",
                image = R.drawable.water_icon,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
