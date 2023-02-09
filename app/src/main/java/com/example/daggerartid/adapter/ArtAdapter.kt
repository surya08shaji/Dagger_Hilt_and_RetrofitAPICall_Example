package com.example.daggerartid.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.Html
import androidx.core.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerartid.data.model.DataModelItem
import com.example.daggerartid.databinding.ArticleAdapterBinding
import com.example.daggerartid.view.DetailActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject


class ArtAdapter @Inject constructor() : RecyclerView.Adapter<ArtAdapter.ViewHolder>() {

    private var artList = mutableListOf<DataModelItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setArt(arts: ArrayList<DataModelItem>) {
        this.artList = arts.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ArticleAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ArticleAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = artList[position]
        holder.binding.title.text = list.title
        holder.binding.author.text = list.author
        Picasso.with(holder.binding.image.context).load(list.image).into(holder.binding.image)
        holder.binding.date.text = list.date
        holder.binding.viewCount.text = list.views
//        holder.binding.content.text = list.content
        holder.binding.tvCategory.text = list.category[0].name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.binding.content.text = Html.fromHtml(list.content, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.binding.content.text = Html.fromHtml(list.content)
        }

        holder.binding.cardView.setOnClickListener {
//            val intent = Intent(holder.binding.image.context, DetailActivity::class.java)
//            intent.putExtra("id", list.id)
//            holder.binding.image.context.startActivity(intent)


            val intent = Intent(holder.binding.image.context, DetailActivity::class.java)
            intent.putExtra("image", list.image)
            intent.putExtra("id", list.id)
            intent.putExtra("view", list.views)
            intent.putExtra("title", list.title)

            val p1: Pair<View, String> = Pair.create(holder.binding.title as View, "passTitle")
            val p2: Pair<View, String> = Pair.create(holder.binding.cardView as View, "passImage")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.binding.image.context as Activity,
                p1,
                p2
            )

            startActivity(holder.binding.image.context, intent, options.toBundle())

//            val options = ActivityOptions.makeSceneTransitionAnimation(
//                holder.binding.image.context as Activity?,
//                UtilPair.create(it,"id"),
//                UtilPair.create(it,"view")
//            )

//            val bundle = Bundle()
//            bundle.putString("view",list.views)
//            bundle.putInt("id",list.id)
//
//            val intent = Intent(holder.binding.image.context,DetailActivity::class.java)
//            intent.putExtras(bundle)
//            holder.binding.image.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}


//imgContainerView.setOnClickListener( {
//    val intent = Intent(this, Activity2::class.java)
//    // create the transition animation - the images in the layouts
//    // of both activities are defined with android:transitionName="robot"
//    val options = ActivityOptions
//            .makeSceneTransitionAnimation(this, androidRobotView, "robot")
//    // start the new activity
//    startActivity(intent, options.toBundle())
//})

//import android.util.Pair as UtilPair
//...
//val options = ActivityOptions.makeSceneTransitionAnimation(this,
//        UtilPair.create(view1, "agreedName1"),
//        UtilPair.create(view2, "agreedName2"))

//            val intent = Intent(this@HomeActivity, ObjectDetailActivity::class.java)
//            intent.putExtra(ObjectDetailActivity.EXTRA_OBJECT, `object`)
//            val p1: Pair<View, String> = Pair.create(mObjectIV as View?, "object_image")
//            val p2: Pair<View, String> = Pair.create(mObjectNameTV as View?, "object_name")
//            val options: ActivityOptionsCompat =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2)
//            startActivity(intent, options.toBundle())

//            val intent = Intent(this@HomeActivity, ObjectDetailActivity::class.java)
//            intent.putExtra(ObjectDetailActivity.EXTRA_OBJECT, `object`)
//            val options: ActivityOptionsCompat =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, mObjectIV, "object_image")
//            startActivity(intent, options.toBundle())