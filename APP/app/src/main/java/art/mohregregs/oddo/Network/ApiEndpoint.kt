package art.mohregregs.oddo.Network


import android.content.Context
import art.mohregregs.oddo.Network.Models.ProductModel
import com.android.volley.Request
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException


class ApiEndpoint {
    companion object ApiEndpoint {
        val Url = "https://10.0.2.2:7062"

        private fun <T> getRequest(context: Context, controller: String, action: String = "", callback: VolleyCallbacl): T? {
            val url = "$Url/$controller/$action"

            var data: T? = null

            val request = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    try {
                        var gson = Gson()
                        val itemType = object : TypeToken<T>() {}.type
                         callback.onSuccess(gson.fromJson<T>(response.toString(), itemType))
                    } catch (e: JSONException) {

                    }
                },
                { error ->
                    println(error.toString())
                }
            )
            request.retryPolicy = object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return 5000
                }

                override fun getCurrentRetryCount(): Int {
                    return 5000
                }

                @Throws(VolleyError::class)
                override fun retry(error: VolleyError) {
                    println(error.toString())
                }
            }
            var x = OddoRequestQueue.getInstance(context).addToRequestQueue(request)

            return data;
        }

        fun <T> getRequestFromServer(context: Context, controller: String, action: String = "", onSuccess: (result: T) -> Unit){
            getRequest<T>(context, controller, action, object: VolleyCallbacl{
                override fun <T> onSuccess(result: T) {
                    onSuccess(result)
                }
            })
        }

        fun getProducts(context: Context, onSuccess: (result: List<ProductModel>?) -> Unit){
            getRequestFromServer(context, "product", "", onSuccess)
        }
    }
}