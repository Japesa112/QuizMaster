package com.nyandori.onlinequiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyandori.onlinequiz.databinding.ActivityMainBinding
import com.example.onlinequiz.ui.theme.OnlineQuizTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf
import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var QuizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        QuizModelList = mutableListOf()

        getDataFromFirebase()




    }
    private fun setUpRecyclerView(){
        binding.progressBar.visibility = View.GONE
       adapter = QuizListAdapter(QuizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    private fun  getDataFromFirebase(){

        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get().addOnSuccessListener {dataSnapshot->
            if (dataSnapshot.exists()){

                for (snapshot in dataSnapshot.children){

                    val quizModel = snapshot.getValue(QuizModel::class.java)

                    if (quizModel != null) {
                        QuizModelList.add(quizModel)
                    }


                }


            }
            setUpRecyclerView()

        }.addOnFailureListener{
            Toast.makeText(this, "It has failed", Toast.LENGTH_LONG).show()

        }




        /*
                var listQuestionModel = mutableListOf<QuestionModel>()
                listQuestionModel.add(QuestionModel("What is android OS?", mutableListOf("Language", "OS","Product", "None"), "OS"))
                listQuestionModel.add(QuestionModel("Who owns android?", mutableListOf("Apple", "Google","Samsung", "Microsoft"), "Google"))

                listQuestionModel.add(QuestionModel("Which assistant android uses?", mutableListOf("Siri", "Cortana","Google Assistant", "Alexa"), "Google Assistant"))
                QuizModelList.add(QuizModel(id="1", title="Programming",subtitle="All the basic programming", time="10",listQuestionModel))
        //QuizModelList.add(QuizModel(id="2", title="Computer",subtitle="All the computer questions", time="20"))
        //QuizModelList.add(QuizModel(id="3", title="Geography",subtitle="Boost your Geography knowledge", time="15"))
               */
                setUpRecyclerView()

    }
}

