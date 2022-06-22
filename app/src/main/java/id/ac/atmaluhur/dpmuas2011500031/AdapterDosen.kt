package id.ac.atmaluhur.dpmuas2011500031

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class AdapterDosen(
    private val getContext: Context,
    private val customListItem:ArrayList<DataDosen>
): ArrayAdapter <DataDosen>(getContext,0,customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflatelist = (getContext as Activity).layoutInflater
            listLayout = inflatelist.inflate(R.layout.activity_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNidn = listLayout.findViewById(R.id.tvNidn)
                tvNamaDosen = listLayout.findViewById(R.id.tvNamaDosen)
                tvProgramStudi = listLayout.findViewById(R.id.tvProgramStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNidn!!.setText(listItem.nidn)
        holder.tvNamaDosen!!.setText(listItem.namadosen)
        holder.tvProgramStudi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("kode", listItem.nidn)
            i.putExtra("nama", listItem.namadosen)
            i.putExtra("Jabatan", listItem.jabatan)
            i.putExtra("golongan_pangkat", listItem.golonganpangkat)
            i.putExtra("Pendidikan", listItem.pendidikan)
            i.putExtra("Keahlian", listItem.keahlian)
            i.putExtra("Program_Studi", listItem.programstudi)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener {
            val db = Campuss(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvNidn!!.text
            val nama = holder.tvNamaDosen!!.text
            val Program_Studi = holder.tvProgramStudi!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage(""" Apakah Anda yakin akan menghapus data ini?
                    $nama
                    [$kode-$Program_Studi]
                """.trimIndent())
                setPositiveButton("Ya") {_, _->
                    if (db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data Dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }
        return listLayout!!
    }
    class ViewHolder {
        internal var tvNidn: TextView? = null
        internal var tvNamaDosen: TextView? = null
        internal var tvProgramStudi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}
