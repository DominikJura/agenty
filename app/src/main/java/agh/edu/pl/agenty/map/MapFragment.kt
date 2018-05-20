package agh.edu.pl.agenty.map

import agh.edu.pl.agenty.R
import agh.edu.pl.agenty.model.VoiceModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*

class MapFragment : android.support.v4.app.Fragment() {

    private lateinit var allVoicesRef: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance()
        allVoicesRef = database.getReference("Voices")
    }

    private fun listenToDB() {
        allVoicesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value as Map<String, Double>
                Log.d("TEST", "Value is: $value")
                val listOfData = value.map { VoiceModel(it.key, it.value) }
                //TODO here u have data to show on Map
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })
    }
}