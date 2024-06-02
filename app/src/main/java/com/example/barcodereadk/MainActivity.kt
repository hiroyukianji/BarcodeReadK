package com.example.barcodereadk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.read_barcode).setOnClickListener {
            startReader()
        }

    }
    /**
     * バーコード読取処理
     */
    private fun startReader() {
        val textView = findViewById<TextView>(R.id.textBarcode)
        textView.text = ""

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(            // 読み取るバーコードの種別を設定
                Barcode.FORMAT_QR_CODE     // 今回は QR コードを読み取るよう設定
            )
            .enableAutoZoom()              // 自動ズーム有効
            .build()
        val scanner = GmsBarcodeScanning.getClient(this, options)

        // ここを実行するとバーコードスキャンの画面が起動する
        scanner.startScan()
            // 読み取りが成功した時のリスナー
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                Log.d("BarcodeTest", "成功しました : $rawValue")
                rawValue?.let {
                    Toast.makeText(this, rawValue, Toast.LENGTH_SHORT).show()
                    textView.text = rawValue
                }
            }
            // 読み取りキャンセルした時のリスナー
            .addOnCanceledListener {
                Log.d("BarcodeTest", "キャンセルしました")
            }
            // 読み取り失敗した時のリスナー
            .addOnFailureListener { exception ->
                Log.d("BarcodeTest", "失敗しました : ${exception.message}")
            }
    }
}