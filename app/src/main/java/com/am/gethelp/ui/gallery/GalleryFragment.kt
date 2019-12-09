package com.am.gethelp.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.gethelp.AdapterListSectioned
import com.am.gethelp.ChatFacebook
import com.am.gethelp.People
import com.am.gethelp.R
import com.am.gethelp.R.array
import java.util.ArrayList
import java.util.Collections

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        initComponent(root)

        return root
    }

    private fun initComponent(root: View) {
        val recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.setHasFixedSize(true)
        val items: ArrayList<People> = getPeopleData()
        items.addAll(getPeopleData())
        items.addAll(getPeopleData())
        var sect_count = 0
        var sect_idx = 0
        val months: List<String> = getStringsMonth()
        for (i in 0 until items.size / 6) {
            items.add(sect_count, People(months[sect_idx], true))
            sect_count = sect_count + 5
            sect_idx++
        }
        //set data and list adapter
        val mAdapter = AdapterListSectioned(context, items)
        recyclerView.setAdapter(mAdapter)
        // on item list clicked
        mAdapter.setOnItemClickListener(object : AdapterListSectioned.OnItemClickListener {
            override fun onItemClick(view: View?, obj: People?, position: Int) {
                startActivity(Intent(context!!, ChatFacebook::class.java))
            }
        })
    }

    fun getStringsMonth(): List<String> {
        val items: MutableList<String> = ArrayList()
        val arr = context!!.resources.getStringArray(array.month)
        for (s in arr) items.add(s)
        Collections.shuffle(items)
        return items
    }

    fun getPeopleData(): ArrayList<People> {
        val items: ArrayList<People> = ArrayList()
        val drw_arr = context!!.resources.obtainTypedArray(array.people_images)
        val name_arr = context!!.resources.getStringArray(array.people_names)
        val desc_arr = context!!.resources.getStringArray(array.people_desc)
        for (i in 0 until drw_arr.length()) {
            val obj = People()
            obj.image = drw_arr.getResourceId(i, -1)
            obj.name = name_arr[i]
            obj.description = desc_arr[i]
            obj.email = getEmailFromName(obj.name)
            obj.imageDrw = context!!.resources.getDrawable(obj.image)
            items.add(obj)
        }
        Collections.shuffle(items)
        return items
    }

    fun getEmailFromName(name: String): String {
        return if (name != null && name != "") {
            name.replace(" ".toRegex(), ".").toLowerCase() + "@mail.com"
        } else name
    }
}