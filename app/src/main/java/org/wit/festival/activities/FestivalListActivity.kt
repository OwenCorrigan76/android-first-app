package org.wit.festival.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.festival.R
import org.wit.festival.adapters.FestivalAdapter
import org.wit.festival.adapters.FestivalListener
import org.wit.festival.databinding.ActivityFestivalListBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel


class FestivalListActivity : AppCompatActivity(), FestivalListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFestivalListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFestivalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FestivalAdapter(app.festivals.findAll(),this)
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FestivalActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFestivalClick(festival: FestivalModel) {
        val launcherIntent = Intent(this, FestivalActivity::class.java)
        launcherIntent.putExtra("festival_edit", festival)
        refreshIntentLauncher.launch(launcherIntent)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }*/
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}