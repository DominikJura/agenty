package agh.edu.pl.agenty.list

import agh.edu.pl.agenty.R
import agh.edu.pl.agenty.model.VoiceModel
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragmnet_list.*


class ListFragment : Fragment() {

    private lateinit var allVoicesRef: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var listVoiceAdapter: ListVoiceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmnet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listVoiceAdapter = ListVoiceAdapter()
        initRecyclerView()

        database = FirebaseDatabase.getInstance()
        allVoicesRef = database.getReference("Voices")


        listenToDB()
    }

    private fun initRecyclerView() = with(recyler) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = listVoiceAdapter
    }

    private fun setDataToList(list: List<VoiceModel>) {
        listVoiceAdapter.submitList(list)
    }

    private fun listenToDB() {
        allVoicesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value as Map<String, Double>
                Log.d("TEST", "Value is: $value")
                setDataToList(value.map { VoiceModel(it.key, it.value) })
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })
    }
}