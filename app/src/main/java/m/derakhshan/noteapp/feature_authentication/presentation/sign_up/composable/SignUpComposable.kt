package m.derakhshan.noteapp.feature_authentication.presentation.sign_up.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import m.derakhshan.noteapp.R
import m.derakhshan.noteapp.core.presentation.composable.LoadingButton
import m.derakhshan.noteapp.feature_authentication.presentation.sign_up.SignUpEvents
import m.derakhshan.noteapp.feature_authentication.presentation.sign_up.SignUpViewModel
import m.derakhshan.noteapp.ui.theme.spacing


@ExperimentalAnimationApi
@Composable
fun SignUpForm(
    viewModel: SignUpViewModel,
    message: (String) -> Unit
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()

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
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.large,
                    start = MaterialTheme.spacing.large,
                    end = MaterialTheme.spacing.large
                )
                .verticalScroll(scrollState)
        ) {

            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = state.username,
                maxLines = 1,
                onValueChange = {
                    viewModel.onEvent(SignUpEvents.ChangeUsername(it))
                },
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.username))
                })

            OutlinedTextField(
                value = state.phoneNumber,
                maxLines = 1,
                onValueChange = {
                    viewModel.onEvent(SignUpEvents.ChangePhoneNumber(it))
                },
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.phone_number))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = state.email,
                maxLines = 1,
                onValueChange = {
                    viewModel.onEvent(SignUpEvents.ChangeEmail(it))
                },
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = state.password,
                maxLines = 1,
                onValueChange = {
                    viewModel.onEvent(SignUpEvents.ChangePassword(it))
                },
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
                visualTransformation = if (state.isPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.onEvent(SignUpEvents.TogglePasswordVisibility) }) {
                        Icon(
                            if (state.isPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = "Hide and show password"
                        )
                    }
                }

            )

            OutlinedTextField(
                value = state.confirmPassword,
                maxLines = 1,
                onValueChange = {
                    viewModel.onEvent(SignUpEvents.ChangeConfirmPassword(it))
                },
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.extraSmall
                    )
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.confirm_password))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            LoadingButton(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.medium
                    )
                    .align(Alignment.CenterHorizontally),
                isExpanded = !state.waitingServerResponse,
                text = stringResource(id = R.string.sign_up)
            ) {
                viewModel.onEvent(SignUpEvents.SingUp)
            }


        }
    }

}