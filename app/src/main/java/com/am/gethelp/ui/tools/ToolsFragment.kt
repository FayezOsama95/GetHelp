package com.am.gethelp.ui.tools

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.gethelp.AdapterListNews
import com.am.gethelp.ArticleSimple
import com.am.gethelp.News
import com.am.gethelp.R
import com.am.gethelp.R.array
import com.am.gethelp.R.layout
import java.util.ArrayList
import java.util.Collections
import java.util.Random

class ToolsFragment : Fragment() {

    private lateinit var toolsViewModel: ToolsViewModel
    private lateinit var mAdapter: AdapterListNews
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(ToolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tools, container, false)
        initComponent(root)
        return root
    }

    private fun initComponent(root: View) {
        val recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setHasFixedSize(true)
        val items: List<News> = getNewsData(10)
        //set data and list adapter
        mAdapter = AdapterListNews(items, layout.item_news_light)
        recyclerView.setAdapter(mAdapter)
        // on item list clicked
        mAdapter.setOnItemClickListener { view: View, news: News, i: Int ->
            context!!.startActivity(Intent(context, ArticleSimple::class.java))
        }
    }

    fun getNewsData(count: Int): List<News> {
        val items: MutableList<News> = ArrayList()
        val images: List<Int> = getAllImages()
        val titles: List<String> =
            getStringsMedium()
        val full_date: List<String> =
            getFullDate()
        val cat = context!!.resources.getStringArray(array.news_category)
        for (i in 0 until count) {
            val obj = News()
            obj.image = images[getRandomIndex(images.size)]
            obj.title = titles[getRandomIndex(titles.size)]
            obj.subtitle = cat[getRandomIndex(cat.size)]
            obj.date = full_date[getRandomIndex(full_date.size)]
            items.add(obj)
        }
        return items
    }

    fun getAllImages(): List<Int> {
        val items: MutableList<Int> = ArrayList()
        val drw_arr = context!!.resources.obtainTypedArray(array.people_images)
        for (i in 0 until drw_arr.length()) {
            items.add(drw_arr.getResourceId(i, -1))
        }
        Collections.shuffle(items)
        return items
    }

    fun getStringsMedium(): List<String> {
        val items: MutableList<String> = ArrayList()
        val name_arr = context!!.resources.getStringArray(array.strings_medium)
        for (s in name_arr) items.add(s)
        Collections.shuffle(items)
        return items
    }

    fun getFullDate(): List<String> {
        val items: MutableList<String> = ArrayList()
        val name_arr = context!!.resources.getStringArray(array.full_date)
        for (s in name_arr) items.add(s)
        Collections.shuffle(items)
        return items
    }

    private fun getRandomIndex(max: Int): Int {
        return Random().nextInt(max - 1)
    }
}