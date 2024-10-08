package network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BitcoinApiService {
    @GET("v3/simple/price?ids=bitcoin&vs_currencies=usd")
    suspend fun getBitcoinPrice(): Response<BitcoinPriceResponse>
}

data class BitcoinPriceResponse(val bitcoin: BitcoinInfo)
data class BitcoinInfo(val usd: Double)

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: BitcoinApiService = retrofit.create(BitcoinApiService::class.java)
}
