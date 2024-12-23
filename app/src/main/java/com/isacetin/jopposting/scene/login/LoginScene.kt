package com.isacetin.jopposting.scene.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.isacetin.jopposting.R
import com.isacetin.jopposting.components.button.JobPrimaryButton
import com.isacetin.jopposting.components.button.JobSocialButton
import com.isacetin.jopposting.components.button.SocialButtonType
import com.isacetin.jopposting.components.field.JobTextField
import com.isacetin.jopposting.components.scaffold.JobScaffold
import com.isacetin.jopposting.models.uistate.UiState
import com.isacetin.jopposting.ui.theme.DarkGray
import com.isacetin.jopposting.ui.theme.customTypography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScene(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val viewState: LoginViewState = rememberLoginViewState()

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.value) {
        if (uiState.value is UiState.Loaded<*>) {
            onNavigateToHome.invoke()
        }
    }

    JobScaffold.WithoutTopBar(
        uiState = uiState.value,
        content = { paddingValues ->
            LoginContent(paddingValues, viewState, viewModel, onNavigateToRegister)
        }
    )
}

@Composable
private fun LoginContent(
    paddingValues: PaddingValues,
    viewState: LoginViewState,
    viewModel: LoginViewModel?,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier =
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier =
                Modifier
                    .height(250.dp)
                    .width(250.dp),
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.login_giris_yap), style = customTypography.titleLarge)

        Text(text = stringResource(R.string.login_sosyal_medya_ile_giris_yap), style = customTypography.titleSmall)

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            horizontalArrangement =
                Arrangement.spacedBy(
                    5.dp,
                    alignment = Alignment.CenterHorizontally
                )
        ) {
            JobSocialButton(icon = SocialButtonType.FACEBOOK)
            JobSocialButton(icon = SocialButtonType.INSTAGRAM)
            JobSocialButton(icon = SocialButtonType.GOOGLE)
        }

        JobTextField(
            label = stringResource(R.string.login_kullanici_adi),
            value = viewState.username.value,
            onValueChange = {
                viewState.username.value = it
            },
            validator = { viewState.usernameValidateInput(it) }
        )

        JobTextField(
            label = stringResource(R.string.login_sifre),
            value = viewState.password.value,
            onValueChange = {
                viewState.password.value = it
            },
            validator = { viewState.passwordValidateInput(it) },
            isPasswordField = true
        )

        Text(
            text = stringResource(R.string.login_sifremi_unuttum),
            style =
                customTypography.bodyMedium.copy(
                    color = DarkGray
                )
        )

        JobPrimaryButton(
            text = stringResource(R.string.login_giris_yap),
            onClick = {
                if (viewState.isValid) {
                    viewModel?.doLogin(
                        viewState.username.value.text,
                        viewState.password.value.text
                    )
                }
            }
        )

        Text(
            modifier =
                Modifier.clickable {
                    viewModel?.resetState()
                    onNavigateToRegister.invoke()
                },
            text = stringResource(R.string.login_kayit_ol),
            style =
                customTypography.bodyMedium.copy(
                    color = DarkGray
                )
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScenePreview() {
    val viewState = LoginViewState()
    LoginContent(
        viewState = viewState,
        viewModel = null,
        paddingValues = PaddingValues(0.dp),
        onNavigateToRegister = {}
    )
}
