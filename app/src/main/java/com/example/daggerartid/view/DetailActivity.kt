package com.example.daggerartid.view

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.daggerartid.R
import com.example.daggerartid.databinding.ActivityDetailBinding
import com.example.daggerartid.utility.AppBarStateChangeListener
import com.example.daggerartid.utility.Utility
import com.example.daggerartid.viewModel.DetailViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailActivity : AppCompatActivity() {


    private var mAppBarLayout: AppBarLayout? = null
    private var mToolbarTextView: TextView? = null
    private var mTitleTextView: TextView? = null
    private var mToolBar: Toolbar? = null
    private var mAppBarStateChangeListener: AppBarStateChangeListener? = null
    private val mToolbarTextPoint = FloatArray(2)
    private val mTitleTextViewPoint = FloatArray(2)
    private var mTitleTextSize = 0f
    private var menuImageView: ImageView? = null
    private var isImage = true
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var toolbar: Toolbar? = null

    //    @Inject
    private val viewModel: DetailViewModel by viewModels()

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val views = intent.getStringExtra("view")
        val image = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")

        binding.back.setOnClickListener {
            onBackPressed()
        }

        viewModel.artDetails.observe(this) {
            binding.title.text = title
            binding.author.text = it.author
            binding.date.text = it.date
            binding.viewCount.text = views
            binding.tvCategory.text = it.category[0].name
            Picasso.with(binding.image.context).load(image).into(binding.image)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.content.text = Html.fromHtml(it.content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.content.text = Html.fromHtml(it.content)
            }
        }
        viewModel.errorMessage.observe(this) {

        }
        viewModel.fetchAllArtDetails(id)


//            ...............................................................................


        mAppBarLayout = binding.appBar
        mToolbarTextView = binding.toolbarTitle
        mTitleTextView = binding.title
        mToolBar = binding.menuToolbar
        menuImageView = binding.image
        collapsingToolbar = binding.collapsingToolbar
//        toolbar = findViewById(R.id.toolbar)
        toolbar = findViewById(R.id.toolbar)

        mTitleTextSize = mTitleTextView!!.textSize

//        setUpToolbar()
        setUpAmazingAvatar()
        setSimpleToolbar()

        if (isImage) {
            collapsingToolbar!!.visibility = View.VISIBLE
            toolbar!!.visibility = View.GONE
        } else {
            collapsingToolbar!!.visibility = View.GONE
            toolbar!!.visibility = View.VISIBLE
        }

    }

    private fun setSimpleToolbar() {
        toolbar!!.title = "Hungry Birds"
        toolbar!!.setNavigationIcon(R.drawable.back)
        toolbar!!.setNavigationOnClickListener { finish() }
    }

    private fun setUpAmazingAvatar() {
        mAppBarStateChangeListener = object : AppBarStateChangeListener() {
            override fun onStateChanged(
                appBarLayout: AppBarLayout?,
                state: State?
            ) {
            }

            override fun onOffsetChanged(state: State?, offset: Float) {
                translationView(offset)
            }
        }
        mAppBarLayout!!.addOnOffsetChangedListener(mAppBarStateChangeListener)
    }

    private fun translationView(offset: Float) {
        menuImageView!!.alpha = 1 - offset
        val newTextSize = mTitleTextSize - (mTitleTextSize - mToolbarTextView!!.textSize) * offset
        val paint = Paint(mTitleTextView!!.paint)
        paint.textSize = newTextSize
        val newTextWidth: Float = Utility.getTextWidth(paint, mTitleTextView!!.text.toString())
        paint.textSize = mTitleTextSize
        val originTextWidth: Float = Utility.getTextWidth(paint, mTitleTextView!!.text.toString())
        val xTitleOffset = originTextWidth - newTextWidth
        val yTitleOffset = (mToolbarTextPoint[1] - mTitleTextViewPoint[1]) * offset
        mTitleTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize)
        mTitleTextView!!.translationX = xTitleOffset
        mTitleTextView!!.translationY = yTitleOffset
    }

    //    private fun setUpToolbar() {
//        setSupportActionBar(mToolBar)
//        if (supportActionBar != null) {
//            supportActionBar!!.setDisplayShowTitleEnabled(false)
//            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        }
//    }
    private fun resetPoints(isTextChanged: Boolean) {
        val offset: Float = mAppBarStateChangeListener!!.getCurrentOffset()
        val toolbarTextPoint = IntArray(2)
        mToolbarTextView!!.getLocationOnScreen(toolbarTextPoint)
        mToolbarTextPoint[0] = toolbarTextPoint[0].toFloat()
        mToolbarTextPoint[1] = toolbarTextPoint[1].toFloat()
        val paint = Paint(mTitleTextView!!.paint)
        val newTextWidth = Utility.getTextWidth(paint, mTitleTextView!!.text.toString())
        paint.textSize = mTitleTextSize
        val originTextWidth = Utility.getTextWidth(paint, mTitleTextView!!.text.toString())
        val titleTextViewPoint = IntArray(2)
        mTitleTextView!!.getLocationOnScreen(titleTextViewPoint)
        mTitleTextViewPoint[0] = titleTextViewPoint[0] - mTitleTextView!!.translationX
        if (mToolbarTextView!!.width > newTextWidth) (originTextWidth - newTextWidth) / 2f else 0
        mTitleTextViewPoint[1] = titleTextViewPoint[1] - mTitleTextView!!.translationY
        if (isTextChanged) {
            Handler().post { translationView(offset) }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            return
        }
        resetPoints(false)
    }
}


//@AndroidEntryPoint
//class DetailActivity : AppCompatActivity() {
//
//    @Inject
//    latent var viewModel: DetailViewModel
//
//    private var _binding: ActivityDetailBinding? = null
//    private val binding get() = _binding!!
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //setContentView(R.layout.activity_detail)
//
//        _binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        val id = intent.getIntExtra("id",0)
//
//        binding.back.setOnClickListener {
//            onBackPressed()
//        }
//        viewModel.detailList.observe(this){
//
//            binding.title.text = it.title
//            binding.author.text = it.author
//            Picasso.with(binding.image.context).load(it.image)
//                .into(binding.image)
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                binding.content.text = Html.fromHtml(it.content, Html.FROM_HTML_MODE_COMPACT)
//            } else {
//                binding.content.text = Html.fromHtml(it.content)
//            }
//        }
//
//        viewModel.errorMessage.observe(this){
//
//        }
//        viewModel.getDetails(id)
//    }
//}