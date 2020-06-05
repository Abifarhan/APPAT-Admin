package com.giyadabi.appat_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_total_report.*
import kotlinx.android.synthetic.main.item_row_report.view.*

class TotalReportActivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_report)

        supportActionBar?.title="Total Report"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fetchReports()
    }

    private fun fetchReports() {
        val ref = FirebaseDatabase.getInstance().getReference()
        ref.child("APPAT").child("laporan_user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach{
                    Log.d("ResultReport", "ini nilai $ref")
                    val report = it.getValue(Data::class.java)
                    if (report != null) {
                        adapter.add(UserItem(report))
                        progressbar_result.setVisibility(View.INVISIBLE)
                    }
                }

//                adapter.setOnItemClickListener { item, view ->
//                    Toast.makeText(applicationContext,"anda mengklik data $item",Toast.LENGTH_SHORT).show()
//                    val intent = Intent(Intent(applicationContext,DetailReportActivity::class.java))
//                    startActivity(intent)
//                    finish()
//                }

                
                rv_newreport.adapter = adapter
                Log.d("ResultReport", "ini nilai $ref")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    class UserItem(val report: Data) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.item_row_report
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.textview_location_result.text = report.location
            viewHolder.itemView.textview_description_result.text = report.description
            viewHolder.itemView.textview_rating_result.text = report.rating
            viewHolder.itemView.text_view_date_result.text = report.date

            if (report.verification == true) {
                viewHolder.itemView.textview_status.text = "sudah diverifikasi"
            } else {
                viewHolder.itemView.textview_status.text = "belum diverifikasi"
            }

            Picasso.get().load(report.reportImageUrl)
                .into(viewHolder.itemView.imageview_result_report)

            viewHolder.itemView.setOnClickListener { view ->
                //coba dijalankan

                val intent = Intent(view.context, DetailReportActivity::class.java)
                intent.putExtra(DetailReportActivity.EXTRA_ID_REPORT, report.id)
                intent.putExtra(DetailReportActivity.EXTRA_LOCATION_REPORT, report.location)
                //Iits buat const val nya dulu. harus beda
                intent.putExtra(DetailReportActivity.EXTRA_IMAGE_REPORT,report.reportImageUrl)
                intent.putExtra(DetailReportActivity.EXTRA_DESCRIPTION_REPORT, report.description)
                intent.putExtra(DetailReportActivity.EXTRA_RATING_REPORT, report.rating)
                intent.putExtra(DetailReportActivity.EXTRA_DATE_REPORT, report.date)
                intent.putExtra(DetailReportActivity.EXTRA_VERIFICATION_REPORT, report.verification)
                intent.putExtra(DetailReportActivity.EXTRA_UID, report.uid)
                view.context.startActivity(intent)
                //ini bisa
            }
        }
     }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_report_verififcation ->{
                startActivity(Intent(this,ReportDoneActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
