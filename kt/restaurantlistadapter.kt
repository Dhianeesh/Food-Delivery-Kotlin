package com.demo.foodorderanddeliveryappkotlin.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.foodorderanddeliveryappkotlin.R
import com.demo.foodorderanddeliveryappkotlin.models.Hours
import com.demo.foodorderanddeliveryappkotlin.models.RestaurentModel
import java.text.SimpleDateFormat
import java.util.*
class RestaurantListAdapter(val restaurantList: List&lt;RestaurentModel?&gt;?,
val clickListener: RestaurantListClickListener):
RecyclerView.Adapter&lt;RestaurantListAdapter.MyViewHolder&gt;() {
override fun onCreateViewHolder(
parent: ViewGroup,
viewType: Int
): RestaurantListAdapter.MyViewHolder {
val view: View =
LayoutInflater.from(parent.context).inflate(R.layout.recycler_restautant_li
st_row, parent, false)
return MyViewHolder(view)
}
override fun onBindViewHolder(holder:
RestaurantListAdapter.MyViewHolder, position: Int) {
holder.bind(restaurantList?.get(position))
holder.itemView.setOnClickListener {
clickListener.onItemClick(restaurantList?.get(position)!!)
}
}
override fun getItemCount(): Int {
return restaurantList?.size!!
}
inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
{
val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
val tvRestaurantName: TextView =
view.findViewById(R.id.tvRestaurantName)
val tvRestaurantAddress: TextView =
view.findViewById(R.id.tvRestaurantAddress)
val tvRestaurantHours: TextView =
view.findViewById(R.id.tvRestaurantHours)
fun bind(restaurentModel: RestaurentModel?) {
tvRestaurantName.text = restaurentModel?.name
tvRestaurantAddress.text = &quot;Address: &quot;+restaurentModel?.address
tvRestaurantHours.text = &quot;Today&#39;s Hours: &quot; +
getTodaysHours(restaurentModel?.hours!!)
Glide.with(thumbImage)
.load(restaurentModel?.image)
.into(thumbImage)
}
}
private fun getTodaysHours(hours: Hours): String? {
val calendar: Calendar = Calendar.getInstance()

val date: Date = calendar.time
val day : String = SimpleDateFormat(&quot;EEEE&quot;,
Locale.ENGLISH).format(date.time)
return when(day) {
&quot;Sunday&quot; -&gt; hours.Sunday
&quot;Monday&quot; -&gt; hours.Monday
&quot;Tuesday&quot; -&gt; hours.Tuesday
&quot;Wednesday&quot; -&gt; hours.Wednesday
&quot;Thursday&quot; -&gt; hours.Thursday
&quot;Friday&quot; -&gt; hours.Friday
&quot;Saturday&quot; -&gt; hours.Saturday
else -&gt; hours.Sunday
}
}
interface RestaurantListClickListener {
fun onItemClick(restaurantModel: RestaurentModel)
}
}
