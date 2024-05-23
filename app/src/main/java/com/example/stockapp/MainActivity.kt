package com.example.stockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockapp.presentation.company_info.CompanyInfoScreen
import com.example.stockapp.presentation.company_listing.CompanyListingScreen
import com.example.stockapp.presentation.company_listing.CompanyListingViewModel
import com.example.stockapp.ui.theme.StockAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            StockAppTheme {
                Surface (modifier = Modifier
                    .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home"){
                            CompanyListingScreen(navController, onItemClick = { symbol ->
                                navController.navigate("company_listing/$symbol")
                            }
                            )
                        }
                        composable("company_listing/{symbol}"){
                            it.arguments?.getString("symbol")
                                ?.let { it1 -> CompanyInfoScreen(symbol = it1, navController = navController) }
                        }
                    }
                }
            }
        }
    }
}
