package com.demo.foodorderanddeliveryappkotlin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.foodorderanddeliveryappkotlin.adapter.PlaceYourOrderAdapter
import com.demo.foodorderanddeliveryappkotlin.models.RestaurentModel
import kotlinx.android.synthetic.main.activity_place_your_order.*
class PlaceYourOrderActivity : AppCompatActivity() {
var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
var isDeliveryOn: Boolean = false
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_place_your_order)

val restaurantModel: RestaurentModel? =
intent.getParcelableExtra(&quot;RestaurantModel&quot;)
val actionbar: ActionBar? = supportActionBar
actionbar?.setTitle(restaurantModel?.name)
actionbar?.setSubtitle(restaurantModel?.address)
actionbar?.setDisplayHomeAsUpEnabled(true)
buttonPlaceYourOrder.setOnClickListener {
onPlaceOrderButtonCLick(restaurantModel)
}
switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked
-&gt;
if(isChecked) {
inputAddress.visibility = View.VISIBLE
inputCity.visibility = View.VISIBLE
inputState.visibility = View.VISIBLE
inputZip.visibility = View.VISIBLE
tvDeliveryCharge.visibility = View.VISIBLE
tvDeliveryChargeAmount.visibility = View.VISIBLE
isDeliveryOn = true
calculateTotalAmount(restaurantModel)
} else {
inputAddress.visibility = View.GONE
inputCity.visibility = View.GONE
inputState.visibility = View.GONE
inputZip.visibility = View.GONE
tvDeliveryCharge.visibility = View.GONE
tvDeliveryChargeAmount.visibility = View.GONE
isDeliveryOn = false
calculateTotalAmount(restaurantModel)
}
}
initRecyclerView(restaurantModel)
calculateTotalAmount(restaurantModel)
}
private fun initRecyclerView(restaurantModel: RestaurentModel?) {
cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
placeYourOrderAdapter =
PlaceYourOrderAdapter(restaurantModel?.menus)
cartItemsRecyclerView.adapter =placeYourOrderAdapter
}
private fun calculateTotalAmount(restaurantModel: RestaurentModel?) {
var subTotalAmount = 0f
for(menu in restaurantModel?.menus!!) {
subTotalAmount += menu?.price!! * menu?.totalInCart!!
}
tvSubtotalAmount.text = &quot;Rs:&quot;+ String.format(&quot;%.2f&quot;,
subTotalAmount)
if(isDeliveryOn) {
tvDeliveryChargeAmount.text = &quot;Rs:&quot;+String.format(&quot;%.2f&quot;,
restaurantModel.delivery_charge?.toFloat())
subTotalAmount += restaurantModel?.delivery_charge?.toFloat()!!
}

tvTotalAmount.text = &quot;Rs:&quot;+ String.format(&quot;%.2f&quot;, subTotalAmount)
}
private fun onPlaceOrderButtonCLick(restaurantModel: RestaurentModel?)
{
if(TextUtils.isEmpty(inputName.text.toString())) {
inputName.error = &quot;Enter your name&quot;
return
} else if(isDeliveryOn &amp;&amp;
TextUtils.isEmpty(inputAddress.text.toString())) {
inputAddress.error = &quot;Enter your address&quot;
return
} else if(isDeliveryOn &amp;&amp;
TextUtils.isEmpty(inputCity.text.toString())) {
inputCity.error = &quot;Enter your City Name&quot;
return
} else if(isDeliveryOn &amp;&amp;
TextUtils.isEmpty(inputZip.text.toString())) {
inputZip.error = &quot;Enter your Zip code&quot;
return
} else if( TextUtils.isEmpty(inputCardNumber.text.toString())) {
inputCardNumber.error = &quot;Enter your credit card number&quot;
return
} else if( TextUtils.isEmpty(inputCardExpiry.text.toString())) {
inputCardExpiry.error = &quot;Enter your credit card expiry&quot;
return
} else if( TextUtils.isEmpty(inputCardPin.text.toString())) {
inputCardPin.error = &quot;Enter your credit card pin/cvv&quot;
return
}
val intent = Intent(this@PlaceYourOrderActivity,
SuccessOrderActivity::class.java)
intent.putExtra(&quot;RestaurantModel&quot;, restaurantModel)
startActivityForResult(intent, 1000)
}
override fun onActivityResult(requestCode: Int, resultCode: Int, data:
Intent?) {
if(requestCode == 1000) {
setResult(RESULT_OK)
finish()
}
super.onActivityResult(requestCode, resultCode, data)
}
override fun onOptionsItemSelected(item: MenuItem): Boolean {
when(item.itemId) {
android.R.id.home -&gt; finish()
else -&gt; {}
}
return super.onOptionsItemSelected(item)
}
override fun onBackPressed() {
super.onBackPressed()
setResult(RESULT_CANCELED)
finish()
}
}
