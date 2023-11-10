package com.example.littlelemon

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun UpperPanel(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(LittleLemonColor.green)
            .verticalScroll(rememberScrollState())
            .padding(start = 12.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.yellow
        )
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 24.sp,
            color = LittleLemonColor.cloud
        )
        Row(
            modifier = Modifier
                .padding(top = 18.dp)
        ) {
            Text(
                text = stringResource(id = R.string.description),
                color = LittleLemonColor.cloud,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            )
            Image(
                painter = painterResource(id = R.drawable.upperpanelimage),
                contentDescription = "Upper Panel Image",
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            )
        }
        Button(
            onClick = {Toast.makeText(context,"Order received. Thank you!",Toast.LENGTH_SHORT).show()},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF4CE14))
        ) {
            Text(
                text = stringResource(id = R.string.orderbuttontext),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpperPanelPreview(){
    UpperPanel()
}