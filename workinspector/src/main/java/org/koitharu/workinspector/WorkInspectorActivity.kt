package org.koitharu.workinspector

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import org.koitharu.workinspector.databinding.WiActivityWorkInspectorBinding

public class WorkInspectorActivity : AppCompatActivity() {
    private lateinit var binding: WiActivityWorkInspectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = WiActivityWorkInspectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.appbar) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
}
