package art.mohregregs.oddo.network


import android.content.Context
import art.mohregregs.oddo.network.models.ProductModel
import com.android.volley.Request
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.lang.reflect.Type


class ApiEndpoint {
    companion object ApiEndpoint {
        val Url = "http://10.0.2.2:5000"

        private fun <T> getRequest(context: Context, type: Type, controller: String, action: String = "", callback: VolleyCallbacl): T? {
            val url = "$Url/$controller/$action"

            var data: T? = null

            val request = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    try {
                        var gson = Gson()
                        callback.onSuccessCallback(gson.fromJson<T>(response.toString(), type))
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

        fun <T> getRequestFromServer(context: Context, type: Type, controller: String, action: String = "", onSuccess: (result: T) -> Unit){
            getRequest<T>(context, type, controller, action, object: VolleyCallbacl{
                override fun <T> onSuccessCallback(result: T) {

                }
            })
        }

        fun getProducts(context: Context, onSuccess: (result: List<ProductModel>?) -> Unit){
            val type = object : TypeToken<List<ProductModel>?>() {}.type
            //getRequestFromServer(context, type, "product", "", onSuccess)

            getRequest<List<ProductModel>?>(context, type, "product", "", object: VolleyCallbacl{
                override fun <T> onSuccessCallback(result: T) {
                    onSuccess(result as List<ProductModel>?)
                }
            })
        }
    }
}