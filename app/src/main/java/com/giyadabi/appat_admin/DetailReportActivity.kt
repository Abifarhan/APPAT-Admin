package com.giyadabi.appat_admin

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_report.*


class DetailReportActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID_REPORT = "ID"
        const val EXTRA_LOCATION_REPORT = "LOCATION"
        const val EXTRA_IMAGE_REPORT = "IMAGE"
        const val EXTRA_DATE_REPORT = "DATE"
        const val EXTRA_DESCRIPTION_REPORT = "DESCRIPTION"
        const val EXTRA_RATING_REPORT = "RATING"
        const val EXTRA_VERIFICATION_REPORT = "VERIFICATION"
        const val EXTRA_UID = "UID"
    }
    private var id: String?=null
    private var location: String? = null
    private var image: String? = null
    private var date: String? = null
    private var uid: String? = null
    private var verification: Boolean = false

    private var description: String? = null
    private var rating: String? = null
    private lateinit var dataIdList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)
        progressBar_Detail.setVisibility(View.VISIBLE)

        supportActionBar?.title="Detail Laporan"

        id = intent.getStringExtra(EXTRA_ID_REPORT)
        location = intent.getStringExtra(EXTRA_LOCATION_REPORT)
        image = intent.getStringExtra(EXTRA_IMAGE_REPORT)
        date = intent.getStringExtra(EXTRA_DATE_REPORT)
        verification = intent.getBooleanExtra(EXTRA_VERIFICATION_REPORT, false)
        description = intent.getStringExtra(EXTRA_DESCRIPTION_REPORT)
        rating = intent.getStringExtra(EXTRA_RATING_REPORT)
        uid = intent.getStringExtra(EXTRA_UID)


        Log.d("Cek id dari detail", "Detail Report Id $id")
        Log.d("Cek lokasi detail", "Detail Report Location $location")
        Log.d("Cek lokasi detail", "Detail Report Date $date")
        Log.d("Cek lokasi detail", "Detail Report Verifivation $verification")


        if (verification == true) {
            textView_status_detail.text = "sudah diverifikasi"
        } else {
            textView_status_detail.text = "belum diverifikasi"
        }
        textView_location_detail.text = location
        textView_date_detail.text = date
//        textView_status_detail.text = verification
        textView_deskripsi_detail.text = description
        textview_rating_detail.text = rating
        Picasso.get().load(image).into(imageView_detail_report)

        Log.d("uji nilai", "nilai verifikasi adalah sebelum diklik ${textView_status_detail.text}")

        btn_verifikasi_detail.setOnClickListener{
            updateVerificationToAdmin()
        }

        progressBar_Detail.visibility = View.INVISIBLE
    }

    //update ke tabel laporan user
    private fun updateVerificationToAdmin(){
        val ref = FirebaseDatabase.getInstance().getReference("APPAT/laporan_user")
        ref.child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    ref.ref.child(id!!).child("verification").setValue(true)
                    textView_status_detail.text = "Sudah di verifikasi"
                    updateVerificationToUser()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

//    kendalanya di method ini kak, gmna caranya sewaktu admin verifikasi data di user berubah juga jadi sudah terverifikasi
    private fun updateVerificationToUser() {

        val ref = FirebaseDatabase.getInstance().getReference("APPAT/data")
        ref.child(uid!!).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Pesan $error", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    ref.ref.child(id!!).child("verification").setValue(true)
                }

            }

        })
//        ref.child(id!!).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val report = dataSnapshot.getValue(Data::class.java)
//                val uid = report?.uid
//                val userRef = FirebaseDatabase.getInstance().getReference("APPAT/data").child(uid!!)
//                if(dataSnapshot.exists()){
//                    userRef.ref.ref.child(id!!).child("verification").setValue(true)
//                    textView_status_detail.text = "Sudah di verifikasi"
//                }
////                uupdateVerificationToUser()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
    }


    //    disini aksi ketika tombol verifikasi diklik kak
    private fun performDeleteReport(){
//        val key =
        val ref = FirebaseDatabase.getInstance().getReference("APPAT/laporan_user")
        verification = intent.getBooleanExtra(EXTRA_VERIFICATION_REPORT, true)

        if (verification == true) {
            textView_status_detail.text = "sudah diverifikasi"
        } else {
            textView_status_detail.text = "belum diverifikasi"
        }

        Log.d("Cek id dari detail", "setelah di klik Detail Report Id $id")
        Log.d("Cek lokasi detail", "setelah di klik Detail Report Location $location")
        Log.d("Cek lokasi detail", "setelah di klik Detail Report Date $date")
        Log.d("Cek lokasi detail", "setelah di klik Detail Report Verifivation $verification")


        Log.d("uji nilai", "nilai verifikasi adalah setelah di klik ${textView_status_detail.text}") //
    //sewaktu saya tes run Log.d tempo hari nilainya sudah bernilai sudah bernilai true kak, tetapi saya bingun cara ubanya
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_report_verififcation -> {
                val intent = Intent(Intent(this,ReportDoneActivity::class.java))
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
