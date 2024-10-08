package com.example.kotlin_android_crypto_monitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_android_crypto_monitor.ui.theme.KotlinandroidcryptomonitorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinandroidcryptomonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BitcoinPriceScreen()
                }
            }
        }
    }
}

@Composable
fun BitcoinPriceScreen() {
    val bitcoinPrice = remember { mutableStateOf("Carregando...") }

    LaunchedEffect(Unit) {
        getBitcoinPrice(bitcoinPrice)
    }

    Text(
        text = bitcoinPrice.value,
        modifier = Modifier.fillMaxSize()
    )
}

private fun getBitcoinPrice(bitcoinPrice: MutableState<String>) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.getBitcoinPrice()
            if (response.isSuccessful) {
                val price = response.body()?.bitcoin?.usd ?: 0.0
                bitcoinPrice.value = "Preço do Bitcoin: $price USD"
            } else {
                bitcoinPrice.value = "Erro ao obter preço: ${response.code()}"
            }
        } catch (e: Exception) {
            bitcoinPrice.value = "Erro ao obter preço"
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
    KotlinandroidcryptomonitorTheme {
        Greeting("Android")
    }
}
