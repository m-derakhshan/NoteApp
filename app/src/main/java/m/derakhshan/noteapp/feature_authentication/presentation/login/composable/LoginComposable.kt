package m.derakhshan.noteapp.feature_authentication.presentation.login.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import m.derakhshan.noteapp.R
import m.derakhshan.noteapp.core.data.data_source.Setting
import m.derakhshan.noteapp.core.presentation.composable.LoadingButton
import m.derakhshan.noteapp.core.presentation.composable.MainNavGraph
import m.derakhshan.noteapp.feature_authentication.presentation.login.LoginEvents
import m.derakhshan.noteapp.feature_authentication.presentation.login.LoginViewModel
import m.derakhshan.noteapp.ui.theme.spacing

@ExperimentalAnimationApi
@Composable
fun LoginForm(
    viewModel: LoginViewModel,
    setting: Setting,
    navController: NavController,
    message: (String) -> Unit
) {

    val state = viewModel.state.value

    AnimatedVisibility(
        visible = state.isFormShown,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 1000),
            initialOffsetY = { -it }),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 500),
            targetOffsetY = { -it })
    ) {

        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = stringResource(id = R.string.welcome_back),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = state.username,
                onValueChange = { viewModel.onEvent(LoginEvents.UsernameChanged(it)) },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.username
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(LoginEvents.PasswordChanged(it)) },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.password
                        )
                    )
                },
                visualTransformation =
                if (state.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
                trailingIcon = {
                    val image = if (state.isPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        viewModel.onEvent(LoginEvents.TogglePasswordVisibility)
                    }) {
                        Icon(imageVector = image, "show or hide password")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
            )

            LoadingButton(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.medium
                    )
                    .align(Alignment.CenterHorizontally),
                isExpanded = !state.waitingServerResponse,
                text = stringResource(id = R.string.login)
            ) {
                viewModel.onEvent(LoginEvents.Login)
                setting.isUserLoggedIn = true
                navController.navigate(MainNavGraph.NoteScreen.route){
                    this.popUpTo(MainNavGraph.WelcomeScreen.route){
                        inclusive = true
                    }
                }
            }


            val forgetPass = buildAnnotatedString {
                append(stringResource(id = R.string.forget_password))
            }
            ClickableText(
                text = forgetPass,
                onClick = {
                    viewModel.onEvent(LoginEvents.ForgetPassword)
                },
                style = TextStyle(color = MaterialTheme.colors.error),
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
        }

    }
}