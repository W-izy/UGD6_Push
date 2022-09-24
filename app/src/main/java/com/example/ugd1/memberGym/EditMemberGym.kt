package com.example.ugd1.memberGym

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.ugd1.R
import com.example.ugd1.databinding.ActivityEditMemberGymBinding
import com.example.ugd1.databinding.ActivityEditProfileBinding
import com.example.ugd1.room.Constant
import com.example.ugd1.room.MemberGym
import com.example.ugd1.room.MemberGymDB
import kotlinx.android.synthetic.main.activity_edit_member_gym.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditMemberGym : AppCompatActivity() {
    val db by lazy { MemberGymDB(this) }
    private var memberGymId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_member_gym)
        setupView()
        setupListener()
    }

    fun setupView() {
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE-> {
                btnUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ-> {
                btnSave.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                getMemberGym()
            }
            Constant.TYPE_UPDATE-> {
                btnSave.visibility = View.GONE
                getMemberGym()
            }
        }
    }

    private fun setupListener() {
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.MemberGymDao().addMemberGym(
                    MemberGym(
                        0, etPersonalTrainer.editText?.text.toString(),
                        etMembership.editText?.text.toString(),
                        etTanggal.editText?.text.toString(),
                        etDurasi.editText?.text.toString()
                    )
                )
                finish()
            }
        }
        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.MemberGymDao().updateMemberGym(
                    MemberGym(memberGymId,
                        etPersonalTrainer.editText?.text.toString(),
                        etMembership.editText?.text.toString(),
                        etTanggal.editText?.text.toString(),
                        etDurasi.editText?.text.toString())
                )
                finish()
            }
        }
    }
    fun getMemberGym(){
        memberGymId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val memberGym = db.MemberGymDao().getMemberGym(memberGymId)[0]
            etPersonalTrainer.editText?.setText(memberGym.personalTrainer)
            etMembership.editText?.setText(memberGym.membership)
            etTanggal.editText?.setText(memberGym.tanggal)
            etDurasi.editText?.setText(memberGym.durasi)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}