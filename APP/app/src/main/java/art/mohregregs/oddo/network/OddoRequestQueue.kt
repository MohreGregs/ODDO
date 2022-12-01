package art.mohregregs.oddo.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class OddoRequestQueue(context: Context){
    companion object{
        @Volatile
        private var INSTANCE: OddoRequestQueue? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this){
            INSTANCE ?: OddoRequestQueue(context).also{
                INSTANCE = it
            }
        }
    }

    val requestQueue: RequestQueue by lazy{
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>): Request<T>? {
        return requestQueue.add(req)
    }
}