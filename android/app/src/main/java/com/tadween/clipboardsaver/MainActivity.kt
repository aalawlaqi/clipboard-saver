package com.tadween.clipboardsaver

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val create = registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri -> if(uri!=null) contentResolver.openOutputStream(uri)?.use { it.write(ClipboardStore.read(this).toByteArray()) } }
    override fun onCreate(b: Bundle?) { super.onCreate(b)
        val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(32,48,32,32) }
        root.addView(TextView(this).apply { text="Clipboard Saver"; textSize=28f; setPadding(0,0,0,20) })
        root.addView(TextView(this).apply { text="Enable Clipboard Saver as your keyboard/input method. Copied text is saved locally; no server or account is used."; textSize=16f; setPadding(0,0,0,24) })
        root.addView(Button(this).apply { text="Open Keyboard Settings"; setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) } })
        root.addView(Button(this).apply { text="Export clipboard.txt"; setOnClickListener { create.launch("clipboard.txt") } })
        root.addView(Button(this).apply { text="Clear History"; setOnClickListener { ClipboardStore.clear(this@MainActivity); update() } })
        setContentView(root); update()
    }
    private fun update(){ title="Clipboard Saver — ${ClipboardStore.count(this)} entries" }
}
