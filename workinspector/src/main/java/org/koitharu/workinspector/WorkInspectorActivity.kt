package org.koitharu.workinspector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koitharu.workinspector.databinding.ActivityWorkInspectorBinding

public class WorkInspectorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkInspectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkInspectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
