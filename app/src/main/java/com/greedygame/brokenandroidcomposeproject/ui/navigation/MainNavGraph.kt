package com.greedygame.brokenandroidcomposeproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greedygame.brokenandroidcomposeproject.data.Article
import com.greedygame.brokenandroidcomposeproject.ui.DetailScreen
import com.greedygame.brokenandroidcomposeproject.ui.NewsScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()

) {
    NavHost(
        navController = navController,
        startDestination = "news_screen"
    ) {

        composable("news_screen") {

            NewsScreen(
                onNavigateToDetail = { route ->
                    navController.navigate(route)
                },
            )
        }

        composable(route = "item_detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })) {
                backStackEntry ->
            val articleID = backStackEntry.arguments?.getInt("itemId")
            DetailScreen(
                modifier = modifier,
                articleId =articleID
            )
        }

    }
}
