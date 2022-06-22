package id.ac.atmaluhur.dpmuas2011500031

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adpDosen: AdapterDosen
    private lateinit var dataDosen: ArrayList<DataDosen>
    private lateinit var lvDosen: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDosen = findViewById(R.id.lvDosen)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataDosen  = ArrayList()
        adpDosen = AdapterDosen(this@MainActivity, dataDosen)

        lvDosen.adapter = adpDosen

        refresh()
        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntriDataDosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean){
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh() {
        val db = Campuss(this@MainActivity)
        val data = db.tampil()
        repeat(dataDosen.size) { dataDosen.removeFirst() }
        if (data.count > 0) {
            while (data.moveToNext()) {
                val campuss = DataDosen(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDosen.add(campuss)
                adpDosen.notifyDataSetChanged()
            }
            lvDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}