package com.tadween.clipboardsaver

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ClipboardStore {
    private const val NAME = "clipboard.txt"
    private fun file(c: Context) = File(c.filesDir, NAME)
    fun append(c: Context, raw: String): Boolean {
        val text = raw.trim(); if (text.isEmpty()) return false
        synchronized(this) {
            val f = file(c); val old = if (f.exists()) f.readText() else ""
            val last = old.trimEnd().substringAfterLast("\n\n").substringAfter("\n").trim()
            if (last == text) return false
            val stamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            f.appendText((if (f.length() > 0) "\n\n" else "") + stamp + "\n" + text)
            return true
        }
    }
    fun read(c: Context) = file(c).takeIf { it.exists() }?.readText().orEmpty()
    fun clear(c: Context) { file(c).delete() }
    fun count(c: Context): Int { val s=read(c).trim(); return if(s.isEmpty()) 0 else Regex("\\n\\n(?=\\d{4}-\\d{2}-\\d{2} )").findAll(s).count()+1 }
}
