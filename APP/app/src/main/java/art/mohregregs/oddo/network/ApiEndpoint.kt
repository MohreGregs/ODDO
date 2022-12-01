package art.mohregregs.oddo.network


import android.content.Context
import art.mohregregs.oddo.network.models.OrderModel
import art.mohregregs.oddo.network.models.ProductModel
import art.mohregregs.oddo.network.models.addModels.AddOrderModel
import com.android.volley.Request
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.lang.reflect.Type


class ApiEndpoint {
    companion object ApiEndpoint {
        val Url = "http://10.0.2.2:5000"

        private fun <T> getRequest(context: Context, type: Type, controller: String, action: String = "", callback: VolleyCallbacl) {
            val url = "$Url/$controller/$action"

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

        }

        private fun <T,R> postRequest(context: Context, type: Type, controller: String, action: String = "", body: R, callback: VolleyCallbacl){
            val url = "$Url/$controller/$action"

            val request = object: StringRequest(
                Request.Method.POST,
                url,
                {response ->
                    try {
                        var gson = Gson()
                        callback.onSuccessCallback(gson.fromJson<T>(response.toString(), type))
                    } catch (e: JSONException) {

                    }
                },
                {error ->
                    println(error.toString())
                }
            ){
                override fun getBody(): ByteArray{
                    var gson = Gson()
                    return gson.toJson(body).toByteArray()
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
            OddoRequestQueue.getInstance(context).addToRequestQueue(request)
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

        fun addOrder(context: Context, body: AddOrderModel, onSuccess: (result: OrderModel?) -> Unit){
            val type = object : TypeToken<OrderModel?>() {}.type

            postRequest<OrderModel?, AddOrderModel>(context, type, "order", "add", body, object: VolleyCallbacl{
                override fun <T> onSuccessCallback(result: T) {
                    onSuccess(result as OrderModel?)
                }
            })
        }
    }
}