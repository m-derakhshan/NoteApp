package m.derakhshan.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import m.derakhshan.noteapp.core.data.data_source.Setting
import m.derakhshan.noteapp.core.presentation.composable.MainNavGraph
import m.derakhshan.noteapp.feature_authentication.presentation.welcome.composable.Welcome
import m.derakhshan.noteapp.feature_note.presentation.add_edit_note.composable.AddEditNoteScreen
import m.derakhshan.noteapp.feature_note.presentation.notes.composable.NoteScreen
import m.derakhshan.noteapp.ui.theme.NoteAppTheme
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var setting: Setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startPoint =
            if (setting.isUserLoggedIn) MainNavGraph.NoteScreen.route
            else MainNavGraph.WelcomeScreen.route

        setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startPoint) {
                    composable(route = MainNavGraph.WelcomeScreen.route) {
                        Welcome(setting = setting, navController = navController)
                    }
                    composable(route = MainNavGraph.NoteScreen.route) {
                        NoteScreen(navController = navController)
                    }
                    composable(route = MainNavGraph.AddEditNoteScreen.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(name = "noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) { backStack ->
                        val color = backStack.arguments?.getInt("noteColor")
                        AddEditNoteScreen(navController = navController, noteColor = color)
                    }
                }
            }
        }
    }
}