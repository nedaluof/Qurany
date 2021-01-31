package com.nedaluof.qurany

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ActivityTestBinding
import com.nedaluof.qurany.ui.baseadapter.TestAdapter
import com.nedaluof.qurany.util.toastySuccess
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val testAdapter = TestAdapter().apply {
            setListOfData(getFakeList())
            setBaseOnClickListener {
                toastySuccess(R.string.alrt_download_completed_msg)
            }
        }

        binding.testRecyclerView.adapter = testAdapter



        Handler(Looper.getMainLooper()).postDelayed({
            binding.testRecyclerView.scrollToPosition(17)
        }, 6000)

    }

    private fun getFakeList(): ArrayList<Reciter> {
        val list = ArrayList<Reciter>()
        repeat(25) {
            list.add(
                    Reciter(
                            count = "$it ${UUID.randomUUID()}",
                            name = "nkohjjbkbkb $it"
                    )
            )
        }
        return list
    }
}