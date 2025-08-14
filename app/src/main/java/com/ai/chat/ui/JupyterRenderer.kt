package com.ai.chat.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayInputStream

class JupyterRenderer(private val context: Context) {
    
    fun renderOutput(output: String): View {
        return when {
            output.startsWith("IMAGE:") -> renderImage(output.substring(6))
            output.startsWith("GRAPH:") -> renderGraph(output.substring(6))
            output.startsWith("HTML:") -> renderHtml(output.substring(5))
            else -> renderText(output)
        }
    }
    
    private fun renderText(text: String): TextView {
        return TextView(context).apply {
            this.text = text
            setTextIsSelectable(true)
            setPadding(16, 8, 16, 8)
        }
    }
    
    private fun renderImage(imageData: String): ImageView {
        return ImageView(context).apply {
            try {
                val decodedBytes = Base64.decode(imageData, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(decodedBytes))
                setImageBitmap(bitmap)
                adjustViewBounds = true
                setPadding(16, 8, 16, 8)
            } catch (e: Exception) {
                setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }
    }
    
    private fun renderGraph(graphData: String): LinearLayout {
        // Placeholder for graph rendering
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(TextView(context).apply {
                text = "Graph: $graphData"
                setPadding(16, 8, 16, 8)
            })
        }
    }
    
    private fun renderHtml(htmlData: String): TextView {
        return TextView(context).apply {
            text = "HTML Content: $htmlData"
            setPadding(16, 8, 16, 8)
        }
    }
} 