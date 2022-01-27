package m.derakhshan.noteapp.feature_authentication.presentation.welcome.composable


import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.launch
import m.derakhshan.noteapp.R
import m.derakhshan.noteapp.core.data.data_source.Setting
import m.derakhshan.noteapp.feature_authentication.presentation.login.LoginEvents
import m.derakhshan.noteapp.feature_authentication.presentation.login.LoginViewModel
import m.derakhshan.noteapp.feature_authentication.presentation.login.composable.LoginForm
import m.derakhshan.noteapp.feature_authentication.presentation.sign_up.SignUpEvents
import m.derakhshan.noteapp.feature_authentication.presentation.sign_up.SignUpViewModel
import m.derakhshan.noteapp.feature_authentication.presentation.sign_up.composable.SignUpForm
import m.derakhshan.noteapp.feature_authentication.presentation.welcome.WelcomeEvent
import m.derakhshan.noteapp.feature_authentication.presentation.welcome.WelcomeViewModel


@ExperimentalAnimationApi
@Composable
fun Welcome(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    setting: Setting,
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState
    ) {
        val state = viewModel.state.value
        Box {

            TriangleBackground(
                scale = state.triangleBackgroundScale,
                rotation = state.triangleBackgroundAngle,
                triangleColor = state.triangleBackgroundColor
            )

            //--------------------(lottie animation and login/sign up button visibility)--------------------//
            AnimatedVisibility(
                visible = viewModel.state.value.isWelcomeShown(),
                enter = fadeIn(animationSpec = tween(1500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Column {
                    LottieBackground()
                    Row {
                        Button(
                            onClick = {
                                viewModel.onEvent(WelcomeEvent.LoginClicked)
                                loginViewModel.onEvent(LoginEvents.ShowForm)
                            },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(10.dp)
                        ) {
                            Text(text = stringResource(id = R.string.login))
                        }

                        Button(
                            onClick = {
                                viewModel.onEvent(WelcomeEvent.SignUpClicked)
                                signUpViewModel.onEvent(SignUpEvents.ShowForm)
                            },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(10.dp)
                        ) {
                            Text(text = stringResource(id = R.string.sign_up))
                        }
                    }
                }
            }

            //--------------------(arrow back icon button visibility)--------------------//
            AnimatedVisibility(
                visible = !viewModel.state.value.isWelcomeShown(),
                enter = slideInVertically(animationSpec = tween(500)),
                exit = slideOutVertically(animationSpec = tween(500))
            ) {
                IconButton(onClick = {
                    viewModel.onEvent(WelcomeEvent.BackPressed)
                    loginViewModel.onEvent(LoginEvents.HideForm)
                    signUpViewModel.onEvent(SignUpEvents.HideForm)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "back"
                    )
                }
            }

            //--------------------(login form. the visibility is handled by itself.)--------------------//
            LoginForm(
                viewModel = loginViewModel,
                setting = setting,
                navController = navController
            ) { message ->
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message, duration = SnackbarDuration.Short
                    )
                }
            }
            //--------------------(sign up form. the visibility is handle by itself.)--------------------//
            SignUpForm(viewModel = signUpViewModel) { message ->
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message, duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}


@Composable
private fun LottieBackground() {

    val animationComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.welcome))
    val progress by animateLottieCompositionAsState(
        composition = animationComposition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = animationComposition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(370.dp),
        alignment = Alignment.TopCenter
    )
}

@Composable
private fun TriangleBackground(
    scale: Float = 1f,
    rotation: Float = 0f,
    triangleColor: Color = Color.LightGray
) {

    val scaleValue by animateFloatAsState(
        targetValue = scale, animationSpec = tween(
            durationMillis = 700
        )
    )

    val colorValue by animateColorAsState(
        targetValue = triangleColor, animationSpec = tween(
            durationMillis = 700
        )
    )

    val rotationValue by animateFloatAsState(
        targetValue = rotation, animationSpec = tween(
            durationMillis = 700
        )
    )

    androidx.compose.foundation.Canvas(modifier = Modifier
        .width(400.dp)
        .height(400.dp)
        .scale(scaleValue)
        .rotate(rotationValue),
        onDraw = {
            val width = size.width
            val height = size.height - 150
            val path = Path().apply {
                moveTo(width / 2.0f, 0.0f)
                lineTo(width, height)
                lineTo(0f, height)
                lineTo(width / 2.0f, 0.0f)
                lineTo(width, height)
            }
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(path = path),
                    paint = Paint().apply {
                        color = colorValue
                        pathEffect = PathEffect.cornerPathEffect(width / 3f)
                    })
            }
        })
}


