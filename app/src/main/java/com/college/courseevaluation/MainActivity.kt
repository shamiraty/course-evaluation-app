package com.college.courseevaluation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.college.courseevaluation.ui.theme.CourseEvaluationScreen
import com.college.courseevaluation.ui.theme.CourseevaluationTheme
import com.college.courseevaluation.ui.theme.DashboardEvaluationScreen
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

// Define Navigation Items with Icons
sealed class NavigationItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object DashboardEvaluation : NavigationItem("dashboard_evaluation", "Analytics", Icons.Filled.BarChart)
    object CourseEvaluation : NavigationItem("course_evaluation", "Course Evaluation", Icons.Filled.RateReview)
    object MyEvaluation : NavigationItem("my_evaluation", "My Evaluation", Icons.Outlined.Assignment)
    object ContinuousAssessment : NavigationItem("continuous_assessment", "Continuous Assessment", Icons.Outlined.Grading)
    object Accommodation : NavigationItem("accommodation", "Accommodation", Icons.Outlined.Hotel)
    object CourseResults : NavigationItem("course_results", "Course Results", Icons.Outlined.Assessment)
    object PaymentsControlNumbers : NavigationItem("payments_control_numbers", "Payments & Control numbers", Icons.Outlined.AttachMoney)
    object Profile : NavigationItem("profile", "Profile", Icons.Outlined.Person)
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourseevaluationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                Spacer(modifier = Modifier.height(12.dp))

                // Use a Column with verticalScroll to make the drawer scrollable
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.DashboardEvaluation.title) },
                        selected = currentRoute(navController) == NavigationItem.DashboardEvaluation.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.DashboardEvaluation.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.DashboardEvaluation.icon,
                                contentDescription = NavigationItem.DashboardEvaluation.title,
                                tint = MaterialTheme.colorScheme.primary // Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )


                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.CourseEvaluation.title) },
                        selected = currentRoute(navController) == NavigationItem.CourseEvaluation.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.CourseEvaluation.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.CourseEvaluation.icon,
                                contentDescription = NavigationItem.CourseEvaluation.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.MyEvaluation.title) },
                        selected = currentRoute(navController) == NavigationItem.MyEvaluation.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.MyEvaluation.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.MyEvaluation.icon,
                                contentDescription = NavigationItem.MyEvaluation.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.ContinuousAssessment.title) },
                        selected = currentRoute(navController) == NavigationItem.ContinuousAssessment.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.ContinuousAssessment.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.ContinuousAssessment.icon,
                                contentDescription = NavigationItem.ContinuousAssessment.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.Accommodation.title) },
                        selected = currentRoute(navController) == NavigationItem.Accommodation.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.Accommodation.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.Accommodation.icon,
                                contentDescription = NavigationItem.Accommodation.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.CourseResults.title) },
                        selected = currentRoute(navController) == NavigationItem.CourseResults.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.CourseResults.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.CourseResults.icon,
                                contentDescription = NavigationItem.CourseResults.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.PaymentsControlNumbers.title) },
                        selected = currentRoute(navController) == NavigationItem.PaymentsControlNumbers.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.PaymentsControlNumbers.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.PaymentsControlNumbers.icon,
                                contentDescription = NavigationItem.PaymentsControlNumbers.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = NavigationItem.Profile.title) },
                        selected = currentRoute(navController) == NavigationItem.Profile.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(NavigationItem.Profile.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                NavigationItem.Profile.icon,
                                contentDescription = NavigationItem.Profile.title,
                                tint = MaterialTheme.colorScheme.primary// Apply primary color
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                }
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = NavigationItem.CourseEvaluation.route,
                    Modifier.padding(paddingValues)
                )

                {
                    composable(route = NavigationItem.CourseEvaluation.route) {
                        CourseEvaluationScreen()
                    }
                    composable(route = NavigationItem.MyEvaluation.route) {
                        SoonScreen(title = NavigationItem.MyEvaluation.title)
                    }
                    composable(route = NavigationItem.ContinuousAssessment.route) {
                        SoonScreen(title = NavigationItem.ContinuousAssessment.title)
                    }
                    composable(route = NavigationItem.Accommodation.route) {
                        SoonScreen(title = NavigationItem.Accommodation.title)
                    }
                    composable(route = NavigationItem.CourseResults.route) {
                        SoonScreen(title = NavigationItem.CourseResults.title)
                    }
                    composable(route = NavigationItem.PaymentsControlNumbers.route) {
                        SoonScreen(title = NavigationItem.PaymentsControlNumbers.title)
                    }
                    composable(route = NavigationItem.Profile.route) {
                        SoonScreen(title = NavigationItem.Profile.title)
                    }
                    composable(route = NavigationItem.DashboardEvaluation.route) {
                        DashboardEvaluationScreen()
                    }

                    composable(route = NavigationItem.DashboardEvaluation.route) {
                        DashboardEvaluationScreen()
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawerHeader(
    imageList: List<Int> = listOf(
        R.drawable.one,
        R.drawable.two,
        R.drawable.three
    ),
    captionList: List<String> = listOf(
        "Institute Of Development Studies",
        "College Of Information Technology",
        "College of Social Sciences"
    ),
    sliderHeight: Dp = 200.dp,
    autoScroll: Boolean = true,
    scrollDuration: Int = 3000 // milliseconds per page
) {
    val pagerState = rememberPagerState(
        pageCount = { imageList.size },
        initialPage = 0
    )

    // Auto-scroll effect
    if (autoScroll && imageList.size > 1) {
        LaunchedEffect(pagerState) {
            while (true) {
                yield()
                kotlinx.coroutines.delay(scrollDuration.toLong())
                val nextPage = (pagerState.currentPage + 1) % imageList.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(sliderHeight)
    ) {
        // Image Slider
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = imageList[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        // Caption Box at center
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            Text(
                text = captionList.getOrNull(pagerState.currentPage) ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        // Caption for current slide
        Text(
            text = captionList.getOrNull(pagerState.currentPage) ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )

        // Pager Indicators at top center
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
        ) {
            repeat(imageList.size) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (selected) 10.dp else 8.dp)
                        .background(
                            color = if (selected) Color.White else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }

        // Title & Subtitle at bottom
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "University of Demo",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Academic Year 2025-2026",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

// Usage in AppDrawer
@Composable
fun AppDrawer() {
    Column {
        DrawerHeader(
            imageList = listOf(
                R.drawable.one,
                R.drawable.two,
                R.drawable.three
            ),
            captionList = listOf(
                "Welcome to Demo",
                "Explore Courses",
                "Join Our Community"
            )
        )
        // ... rest of drawer items
    }
}


@Composable
fun SoonScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$title - Soon",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
