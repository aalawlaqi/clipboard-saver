package com.tadween.clipboardsaver

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class ClipboardImeService : InputMethodService() {
    private lateinit var clipboard: ClipboardManager
    private val listener = ClipboardManager.OnPrimaryClipChangedListener { capture() }
    override fun onCreate() { super.onCreate(); clipboard=getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager; clipboard.addPrimaryClipChangedListener(listener) }
    override fun onDestroy() { clipboard.removePrimaryClipChangedListener(listener); super.onDestroy() }
    private fun capture() { try { val clip=clipboard.primaryClip ?: return; if(clip.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)||clip.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) ClipboardStore.append(applicationContext,clip.getItemAt(0).coerceToText(this).toString()) } catch(_:Exception){} }
    override fun onCreateInputView(): View = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(24,12,24,12); addView(TextView(this@ClipboardImeService).apply { text="Clipboard Saver\nCopies are saved automatically"; textSize=16f; setPadding(8,8,8,8) }); addView(TextView(this@ClipboardImeService).apply { text="Saved entries: ${ClipboardStore.count(applicationContext)}"; textSize=13f }) }
}
