package com.demo.foodorderanddeliveryappkotlin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.foodorderanddeliveryappkotlin.adapter.MenuListAdapter
import com.demo.foodorderanddeliveryappkotlin.models.Menus
import com.demo.foodorderanddeliveryappkotlin.models.RestaurentModel
import kotlinx.android.synthetic.main.activity_restaurant_menu.*
class RestaurantMenuActivity : AppCompatActivity(),
MenuListAdapter.MenuListClickListener {
private var itemsInTheCartList: MutableList&lt;Menus?&gt;? = null
private var totalItemInCartCount = 0
private var menuList: List&lt;Menus?&gt;? = null
private var menuListAdapter: MenuListAdapter? = null
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_restaurant_menu)
val restaurantModel =
intent?.getParcelableExtra&lt;RestaurentModel&gt;(&quot;RestaurantModel&quot;)
val actionBar: ActionBar? = supportActionBar
actionBar?.setTitle(restaurantModel?.name)
actionBar?.setSubtitle(restaurantModel?.address)
actionBar?.setDisplayHomeAsUpEnabled(true)
menuList = restaurantModel?.menus
initRecyclerView(menuList)
checkoutButton.setOnClickListener {
if(itemsInTheCartList != null &amp;&amp; itemsInTheCartList!!.size &lt;=
0) {
Toast.makeText(this@RestaurantMenuActivity, &quot;Please add
some items in cart&quot;, Toast.LENGTH_LONG).show()
}
else {
restaurantModel?.menus = itemsInTheCartList
val intent = Intent(this@RestaurantMenuActivity,
PlaceYourOrderActivity::class.java)
intent.putExtra(&quot;RestaurantModel&quot;, restaurantModel)
startActivityForResult(intent, 1000)
}
}
}
private fun initRecyclerView(menus: List&lt;Menus?&gt;?) {
menuRecyclerVuew.layoutManager = GridLayoutManager(this, 2)
menuListAdapter = MenuListAdapter(menus, this)
menuRecyclerVuew.adapter =menuListAdapter
}

override fun addToCartClickListener(menu: Menus) {
if(itemsInTheCartList == null) {
itemsInTheCartList = ArrayList()
}
itemsInTheCartList?.add(menu)
totalItemInCartCount = 0
for(menu in itemsInTheCartList!!) {
totalItemInCartCount = totalItemInCartCount +
menu?.totalInCart!!
}
checkoutButton.text = &quot;Checkout (&quot; + totalItemInCartCount +&quot;)
Items&quot;
}
override fun updateCartClickListener(menu: Menus) {
val index = itemsInTheCartList!!.indexOf(menu)
itemsInTheCartList?.removeAt(index)
itemsInTheCartList?.add(menu)
totalItemInCartCount = 0
for(menu in itemsInTheCartList!!) {
totalItemInCartCount = totalItemInCartCount +
menu?.totalInCart!!
}
checkoutButton.text = &quot;Checkout (&quot; + totalItemInCartCount +&quot;)
Items&quot;
}
override fun removeFromCartClickListener(menu: Menus) {
if(itemsInTheCartList!!.contains(menu)) {
itemsInTheCartList?.remove(menu)
totalItemInCartCount = 0
for(menu in itemsInTheCartList!!) {
totalItemInCartCount = totalItemInCartCount +
menu?.totalInCart!!
}
checkoutButton.text = &quot;Checkout (&quot; + totalItemInCartCount +&quot;)
Items&quot;
}
}
override fun onOptionsItemSelected(item: MenuItem): Boolean {
when(item.itemId) {
android.R.id.home -&gt; finish()
else -&gt; {}
}
return super.onOptionsItemSelected(item)
}
override fun onActivityResult(requestCode: Int, resultCode: Int, data:
Intent?) {
super.onActivityResult(requestCode, resultCode, data)
if(requestCode == 1000 &amp;&amp; resultCode == RESULT_OK) {
finish()
}
}
}
