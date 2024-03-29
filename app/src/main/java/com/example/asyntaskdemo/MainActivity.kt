package com.example.asyntaskdemo

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.asyntaskdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.button.setOnClickListener {

            var uploadtask = UploadTask()
            uploadtask.execute("ajju hii")
//            main thread block here anr WHEN we execute on ui intration
//            for (i in 1..10){
//                Thread.sleep(10000)
//                Log.d("TAG", "doInBackground: running ")
//            }
        }

        binding.textView.text=""
        binding.progressCir.visibility=View.INVISIBLE

    }

    inner class UploadTask : AsyncTask<String, Int, String>() {

//        work on main thread
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("TAG", "doInBackground: running " + Thread.currentThread().name)
            binding.textView.text="uploading data wait....."
            binding.progressCir.visibility=View.VISIBLE
            binding.button.isEnabled=false
        }

//        background thread work
        override fun doInBackground(vararg params: String?) :String{
            for (i in 0..10) {
                Thread.sleep(2000)
                Log.d("TAG", "doInBackground: running " + params[0] + Thread.currentThread().name)
                publishProgress(i*10)

            }
            return "data uploaded successfully.."
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            binding.progressBar.setProgress(values[0]?.plus(1) ?: 0)

        }

//        main thread work
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("TAG", "doInBackground: running " + Thread.currentThread().name)
            binding.textView.text=result
            binding.progressCir.visibility=View.INVISIBLE
            binding.button.isEnabled=true
        }

    }
}

