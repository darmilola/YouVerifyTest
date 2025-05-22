package com.assignment.youverifytest.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignment.craftsilicontest.component.IconButtonComponent
import com.assignment.youverifytest.R
import com.assignment.youverifytest.presentation.Shop.ShopActivity
import com.assignment.youverifytest.ui.theme.YouVerifyTestTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private var mainActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        mainActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!, onAuthSuccessful = {
                    startActivity(Intent(this@MainActivity, ShopActivity::class.java))
                }, onAuthFailed = {})
            } catch (e: ApiException) {}
        }
        setContent {
            YouVerifyTestTheme {
                Scaffold(
                    topBar = {},
                    content = { innerPadding ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(color = Color.Yellow)) {

                            val buttonStyle = Modifier
                                .padding(bottom = 15.dp, top = 15.dp)
                                .fillMaxWidth(0.90f)
                                .height(45.dp)
                            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Google", borderStroke = BorderStroke(0.8.dp, Color.White), iconSize = 20, colors = ButtonDefaults.buttonColors(containerColor = Color.White), fontSize = 16, shape = CircleShape, textColor = Color.Black, style = MaterialTheme.typography.labelSmall, iconRes = R.drawable.google_icon){

                                firebaseAuth = FirebaseAuth.getInstance()
                                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken("673946805092-f3i5cdd1m35f1j7on29bqk7cnd3eljda.apps.googleusercontent.com")
                                    .requestEmail()
                                    .requestProfile()
                                    .build()

                                val mGoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)
                                val signInIntent = mGoogleSignInClient.signInIntent
                                mainActivityResultLauncher!!.launch(signInIntent)

                            }

                        }
                    })
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String,onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth!!.currentUser
                    onAuthSuccessful(user?.email!!)
                } else {
                    onAuthFailed()
                }
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YouVerifyTestTheme {
        Greeting("Android")
    }
}