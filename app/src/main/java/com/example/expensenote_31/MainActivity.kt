package com.example.expensenote_31

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.nio.file.DirectoryNotEmptyException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    //สร้างตัวแปรสำหรับหน้า UI Element
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    private var total: Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อมโยงตัวแปรจากหน้า XML ()
        etAmount = findViewById<EditText>(R.id.etAmount)
        etNote = findViewById<EditText>(R.id.etNote)
        btnAdd = findViewById<Button>(R.id.btnAdd)
        btnClear = findViewById<Button>(R.id.btnClear)
        tvLastItem = findViewById<TextView>(R.id.tvRecent)
        tvTotal = findViewById<TextView>(R.id.tvTotal)

        btnAdd.setOnClickListener {
            //ดึงข้อความจากช่องกรอกจำนวณเงิน และช่องหมายเหตุ พร้อมตัดช่องว่างออก
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()
            //ตรวจสอบเงื่อนไขว่า ผู้ใช้งานได้ทำการกรอกจำนวนเงินมาแล้วรึยัง
            if (amountText.isEmpty()){
                Toast.makeText(this,"กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //ตรวจสอบว่าผู้ใช้กรอกขอมูลในช่องจำนวนเงิน เป็นตัวเลขรึป่าว
            val amount = amountText.toDoubleOrNull()
            if (amount == null){
                Toast.makeText(this,"กรุณากรอกจำนวนเงินเป็นตัวเลข", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //ทำการบวกจำนวนเงินเข้าไปในยอดสะสม
            total += amount

            //ตรวจสอบว่าช่องคำอธิบายมีการกรอกข้อมูลมารึป่าว ถ้าไม่มีให้ใช้เป็นคำว่า "ไม่ระบุ"
            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"

            tvLastItem.text = "รายการล่าสุด: $showNote - %,2f บาท".format(amount)
            tvTotal.text = "ยอดรวมวันนี้: %,2f บาท".format(total)

            etAmount.text.clear()
            etNote.text.clear()
        }
        btnClear.setOnClickListener {
            total = 0.0

            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text = "รายการล่าสุด: -"
            tvTotal.text = "ยอดรวมวันนี้: 0.00 บาท"
        }
    }
}