package com.demo.foodorderanddeliveryappkotlin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.foodorderanddeliveryappkotlin.adapter.RestaurantListAdapter
import com.demo.foodorderanddeliveryappkotlin.models.RestaurentModel
import com.google.gson.Gson
import java.io.*
import java.lang.Exception
class MainActivity : AppCompatActivity(),
RestaurantListAdapter.RestaurantListClickListener {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_main)
val actionBar: ActionBar? = supportActionBar
actionBar?.setTitle(&quot;Restaurant List&quot;)
val restaurantModel = getRestaurantData()
initRecyclerView(restaurantModel)
}
private fun initRecyclerView(restaurantList: List&lt;RestaurentModel?&gt;?) {
val recyclerViewRestaurant =
findViewById&lt;RecyclerView&gt;(R.id.recyclerViewRestaurant)
recyclerViewRestaurant.layoutManager = LinearLayoutManager(this)
val adapter = RestaurantListAdapter(restaurantList, this)
recyclerViewRestaurant.adapter =adapter
}
private fun getRestaurantData(): List&lt;RestaurentModel?&gt;? {

val inputStream: InputStream =
resources.openRawResource(R.raw.restaurent)
val writer: Writer = StringWriter()
val buffer = CharArray(1024)
try {
val reader: Reader =
BufferedReader(InputStreamReader(inputStream, &quot;UTF-8&quot;))
var n : Int
while (reader.read(buffer).also { n = it } != -1) {
writer.write(buffer, 0, n)
}
}catch (e: Exception){}
val jsonStr: String = writer.toString()
val gson = Gson()
val restaurantModel =
gson.fromJson&lt;Array&lt;RestaurentModel&gt;&gt;(jsonStr,
Array&lt;RestaurentModel&gt;::class.java).toList()
return restaurantModel
}
override fun onItemClick(restaurantModel: RestaurentModel) {
val intent = Intent(this@MainActivity,
RestaurantMenuActivity::class.java)
intent.putExtra(&quot;RestaurantModel&quot;, restaurantModel)
startActivity(intent)
}
}
