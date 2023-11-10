package com.example.littlelemon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.AccessController.getContext

class EmailPassword : Activity() {
    private lateinit var auth: FirebaseAuth
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }
var userID: String = ""
    fun signIn(email: String, password: String,navController: NavHostController) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    navController.navigate(Home.route){
                        popUpTo(Login.route) { inclusive = true }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                   /* Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()*/
                }
            }
        // [END sign_in_with_email]
    }
@Composable
fun LoginScreen(navController: NavHostController){
    auth = FirebaseAuth.getInstance()
    var username by rememberSaveable() {
        mutableStateOf("") // Initialize with the value from the ViewModel
    }
    var password by rememberSaveable(){
        mutableStateOf("")
    }
    val passwordFocusRequester = remember { FocusRequester() }
    var valid =false
    val context = LocalContext.current
    var alfaImg by remember {
        mutableStateOf(0f)
    }
    val animateAlfaImg by animateFloatAsState(targetValue = alfaImg,
    animationSpec = tween(durationMillis = 3000)
    )
    var message=""
    LaunchedEffect(Unit) {
        delay(2400)
        alfaImg = 1f // Start the animation by setting alphaImg to 1
    }
    val colorStops = arrayOf(
        0.2f to Color.Transparent,
        1.0f to Color.Black
    )
    Box(modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(colorStops = colorStops)),
        contentAlignment = Alignment.Center
    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UpwardMovementAnimation()
            TextField(
            value = username,
            onValueChange = {username=it},
            label = { Text(text = "Username") },
                modifier = Modifier.alpha(alpha = animateAlfaImg),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)

            )
        TextField(
            value = password,
            onValueChange = {password=it},
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.alpha(alpha = animateAlfaImg),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onGo = {}),
            maxLines = 1
        )
        /*val databaseReference = FirebaseDatabase.getInstance("https://food-delivery-app-355a6-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("user")
        if(username==null || username==" "){username=""}
        val userReference = databaseReference.child(username)
        println(username.toString())
        userReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                val user = dataSnapshot?.getValue(UserFirebaseModel::class.java)
                valid = (user != null) /*&& (password == user.confirm_pass)*/ && username!=""
            } else {
                Log.e("Firebase", "Error getting user data: ${task.exception?.message}")
            }
        }*/
        Row(modifier = Modifier.alpha(alpha = animateAlfaImg)) {
            Text(
                text = "Forgot Password? ",
                modifier = Modifier.clickable { navController.navigate(Register.route) })
            Text(
                text = "Sign up",
                modifier = Modifier.clickable { navController.navigate(Register.route) })
        }

        Button(
            onClick = {
                if(username!="" && password!=""){
                signIn(username,password,navController)}
                /*if(valid==true){
                    userID=username
                    message="Welcome to Little Lemon!"
                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                    navController.navigate(Home.route){
                        popUpTo(Login.route) { inclusive = true }
                    }

                }*/
                      else{
                    message="Invalid username or password"
                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                      }},modifier = Modifier.alpha(alpha = animateAlfaImg),
            colors = ButtonDefaults.buttonColors(
                Color(0xFF495E57)
            )
        ) {
            Text(
                text = "Login",
                color = Color(0xFFEDEFEE),
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(alpha = animateAlfaImg)
            )
        }
    }}
}
@Composable
fun getSharedPreferences(): SharedPreferences {
    val context = LocalContext.current
    return context.getSharedPreferences("LoginState", Context.MODE_PRIVATE)
}
@Composable
fun saveLoginState(username: String,isLoggedIn: Boolean) {
    val sharedPreferences = getSharedPreferences()
    sharedPreferences.edit().putString("username", username).apply()
    sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
}
@Composable
fun getLoginState(): Boolean {
    val sharedPreferences = getSharedPreferences()
    return sharedPreferences.getBoolean("isLoggedIn", false)
}
@Composable
fun getUsername(): String {
    val sharedPreferences = getSharedPreferences()
    return sharedPreferences.getString("username", "") ?: ""
}
    private fun createAccount(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(EmailPassword.TAG, "createUserWithEmail:success")
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(EmailPassword.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(EmailPassword.TAG, "createUserWithEmail:failure", task.exception)
                }
            }
        // [END create_user_with_email]
    }
@Composable
fun Register(){
    val context = LocalContext.current
    var Full_Name by rememberSaveable(){
        mutableStateOf("")
    }
    var username by rememberSaveable(){
        mutableStateOf("")
    }
    var email by rememberSaveable(){
        mutableStateOf("")
    }
    var Password by rememberSaveable(){
        mutableStateOf("")
    }
    var confirm_pass by rememberSaveable(){
        mutableStateOf("")
    }
    var message by rememberSaveable(){
        mutableStateOf("")
    }
    var valid by rememberSaveable() {
        mutableStateOf(false)
    }
    val databaseReference = FirebaseDatabase.getInstance("https://food-delivery-app-355a6-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("user")
    val userReference = databaseReference.child(username)
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)){
        Text(text = "Register", color = Color.Black, fontSize = 30.sp,fontWeight = FontWeight.Bold)
        TextField(
            value = Full_Name,
            onValueChange = {Full_Name=it
                message=""
                           },
            label = { Text(text = "Full Name") },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 10.dp),
            maxLines = 1
        )
        if(message!=""){
        Row (verticalAlignment = Alignment.CenterVertically){

            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Warning",
                tint = Color.Red, modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp))

            Text(
                text = message,
                modifier = Modifier.fillMaxWidth(0.95f)
            )

        }}
        TextField(
            value = username,
            onValueChange = {username=it
                if(Full_Name==""){
                    message="This field cannot be empty"
                }
                else{
                    message=""
                }
                            if(" " in username){
                                val index=username.indexOf(" ")
                                val stringBuilder = StringBuilder(username)
                                stringBuilder.deleteCharAt(index)
                                username = stringBuilder.toString()
                            }
                            if('.' in username){}},
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth(0.95f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            value = email,
            onValueChange = {email=it
                message=""
            },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 10.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
       userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                valid = dataSnapshot.exists() && username!=""
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        if(valid && username!=""){
            Row (verticalAlignment = Alignment.CenterVertically){

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Warning",
                        tint = Color.Red, modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp))

                Text(
                    text = "Email already exists",
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

        }}
        TextField(
            value = Password,
            onValueChange = {Password=it},
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.95f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        )
        TextField(
            value = confirm_pass,
            onValueChange = {confirm_pass=it},
            label = { Text(text = "Confirm Password") },
            modifier = Modifier.fillMaxWidth(0.95f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
       Button(onClick = {
            if(confirm_pass==Password && username!="" && Password!=""){
                if(confirm_pass==Password && username!="" && Password!=""){
                    Toast.makeText(context,username,Toast.LENGTH_SHORT).show()
                    createAccount(email, confirm_pass)}
            val new_user = UserFirebaseModel(username, Full_Name,email)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Dish already exists, update the price
                        val existingDish = dataSnapshot.getValue(DishFirebaseModel::class.java)
                        existingDish?.let {
                        }
                    } else {
                        // Dish does not exist, add it to the database
                        userReference.setValue(new_user)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error: ${error.message}")
                }
            })}
            else{
                Toast.makeText(context,"Requirements does not match",Toast.LENGTH_SHORT).show()
            }
        },colors = ButtonDefaults.buttonColors(
            Color(0xFF495E57)
        ), modifier = Modifier.padding(5.dp)) {
            Text(text = "Register",color = Color(0xFFEDEFEE),
                modifier = Modifier.padding(5.dp))
        }

    }
    }
}
data class UserFirebaseModel(
    val username: String,
    val full_name: String,val email: String){
    // Add a default no-argument constructor
    constructor() : this("", "","")
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun UpwardMovementAnimation() {
    var animate by remember { mutableStateOf(true) }
    val animOffsetY by animateFloatAsState(
        targetValue = if (animate) +70f else 0f,
        animationSpec = tween(durationMillis = 1400, easing = LinearEasing)
    )

    val updatedAnimate = rememberUpdatedState(animate)

    LaunchedEffect(updatedAnimate.value) {
        if (updatedAnimate.value) {
            delay(1500) // Delay for 0.5 seconds
            animate = false // Stop the animation after the delay
        }
    }
        Image(
            painter = painterResource(id = R.drawable.littlelemonlogo),
            contentDescription = "Animated Image",
            modifier = Modifier
                .graphicsLayer { translationY = animOffsetY.dp.toPx() }
                .padding(10.dp)
        )

}
    companion object {
        private const val TAG = "EmailPassword"
    }
}
