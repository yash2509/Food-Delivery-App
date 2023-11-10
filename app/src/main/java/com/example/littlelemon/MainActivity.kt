package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.math.round


class MainActivity : ComponentActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // [END declare_auth]
            // [START initialize_auth]
            // Initialize Firebase Auth
            auth = FirebaseAuth.getInstance()
           val currentUser = auth.currentUser
            // [END initialize_auth]

        lifecycleScope.launch{
            val menuItems= getMenu("Salads")
            runOnUiThread {
            menuItemsLiveData.value=menuItems}

        }
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        setContent {
            val context = LocalContext.current
            Toast.makeText(context,currentUser.toString(), Toast.LENGTH_SHORT).show()
            // Check if user is signed in (non-null) and update UI accordingly.
            if (auth!=null && currentUser != null) {
                Toast.makeText(context,currentUser.uid.toString(), Toast.LENGTH_SHORT).show()
            }
            print(currentUser.toString())
            if (currentUser != null) {
                App()
            }
            else{
                Login()
            }
            }
            }
        }


@Composable
fun HomeScreen(navController: NavHostController) {
        Column {
            UpperPanel()
            LowerPanel(navController)
        }


}
@Composable
fun Settings(navController: NavHostController){

        Column {
            Text(text = "Settings")
        }

}
@Composable
fun Login(){
    val myInstance = EmailPassword()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =Login.route){
        composable(Login.route){
            myInstance.LoginScreen(navController)
        }
        composable(Home.route){
           myInstance.saveLoginState("i",true)
            App()
        }
        composable(Register.route){
            myInstance.Register()
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun bottom(navController: NavHostController){
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = Color.Black,
        sheetPeekHeight = 128.dp,
        sheetElevation=10.dp,
        drawerShape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp),
        drawerGesturesEnabled = true,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_horizontal_rule_24),
                    contentDescription = "Upper Panel Image",
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sheet content")
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch { scaffoldState.bottomSheetState.expand() }
                    }
                ) {
                    Text("Click to collapse sheet")
                }
            }
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            /*LowerPanel(navController)*/
        }
    }
}
var dest=""
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun App() {
    val navcontroller = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scope1 = rememberCoroutineScope()
    val scaffoldState1 = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState1,
        sheetPeekHeight = 128.dp,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet")
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sheet content")
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope1.launch { scaffoldState1.bottomSheetState.expand() }
                    }
                ) {
                    Text("Click to collapse sheet")
                }
            }
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Text("Scaffold Content")
        }
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    // Create an instance of the Room database
    val currentRoute = navcontroller.currentBackStackEntry?.destination?.route

    Scaffold(scaffoldState = scaffoldState,
        drawerContent = { if (dest!="Login" && dest!="Search"){DrawerPanel(scaffoldState = scaffoldState, scope = scope,navcontroller)} },
        bottomBar = {if (dest!="Login"){MyNavigation(navController = navcontroller)}},
        drawerGesturesEnabled = dest != "Login") {
        Box(Modifier.padding(it)) {
            NavHost(navController = navcontroller, startDestination = Home.route) {
                composable(Home.route) {
                    Scaffold(scaffoldState = scaffoldState,
                        drawerContent = { if (dest!="Login" && dest!="Search"){DrawerPanel(scaffoldState = scaffoldState, scope = scope,navcontroller)} },
                        topBar = {if (!currentRoute.equals("Search")){
                            TopAppBar(
                                scaffoldState = scaffoldState,
                                scope = scope,
                                navController = navcontroller
                            )}
                        }){
                    HomeScreen(navcontroller)}
                }
                composable(Search.route) {
                    dest="Search"
                    var searchPhase by rememberSaveable(){
                        mutableStateOf("")
                    }

                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter){
                    Column(modifier = Modifier.padding(20.dp)) {
                        TextField(value = searchPhase,
                            onValueChange = {searchPhase=it},
                            label={
                                Row(){
                                    if(searchPhase==""){
                                    Icon(imageVector = Icons.Default.Search, contentDescription = "")}
                                    Text(text = "Search")}

                            }, shape = RoundedCornerShape(20.dp)
                        )
                        var items= menuItemsLiveData.observeAsState(emptyList())
                        val fill=items.value.filter { it.contains(searchPhase.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }) }
                        for(i in fill){
                           Text(text = i)
                    }


                }}
                    }
                composable(DishDetails.route + "/{${DishDetails.argDishId}}",
                    arguments = listOf(navArgument(DishDetails.argDishId) {
                        type = NavType.IntType
                    })
                ) {
                    val id =
                        requireNotNull(it.arguments?.getInt(DishDetails.argDishId)) { "Dish id is null" }
                    DishDetails(id, navcontroller)
                }
                composable(Cart.route){
                    Cart()
                }
                composable(Login.route){
                    dest="Login"
                    scope?.launch{scaffoldState?.drawerState?.close()}
                    Login()
                    //saveLoginState("",false)
                }
            }
        }
    }
}
@Composable
/*Navigation*/
fun MyNavigation(navController: NavHostController){
    val destinationList= listOf<Destination>(
        Home,
        Search
    )
    val selectedIndex= rememberSaveable{
        mutableStateOf(0)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val newIndex = destinationList.indexOfFirst { it.route == destination.route }
            if (newIndex != -1 && newIndex != selectedIndex.value) {
                selectedIndex.value = newIndex
            }
        }
    }
    BottomNavigation(backgroundColor = Color.Black) {
        destinationList.forEachIndexed{index,destination->
            BottomNavigationItem(label = { Text(text = destination.title)}, selectedContentColor = Color.White, unselectedContentColor = Color.DarkGray,icon={ Icon(
                imageVector = destination.icon,
                contentDescription = destination.title
            )}, selected = index==selectedIndex.value,
                onClick = {
                    selectedIndex.value=index
                    destinationList.forEachIndexed{index,destination->
                        dest=destination.title}
                    navController.navigate(destinationList[index].route){
                        popUpTo(Home.route)
                        launchSingleTop=true
                    }
                })
        }
    }
}


