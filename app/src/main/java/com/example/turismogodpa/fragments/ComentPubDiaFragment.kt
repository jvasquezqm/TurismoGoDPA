package com.example.turismogodpa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.ComentPubAdapter
import com.example.turismogodpa.data.ComentPubData
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class ComentPubDiaFragment : DialogFragment() {
    private lateinit var adapter: ComentPubAdapter
    private val commentList = mutableListOf<ComentPubData>()

    companion object {
        private const val ARG_ACTIVITY_ID = "activity_id"

        fun newInstance(activityId: String): ComentPubDiaFragment {
            val fragment = ComentPubDiaFragment()
            val args = Bundle()
            args.putString(ARG_ACTIVITY_ID, activityId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comentpub_dialog, container, false)
        val rvComentPubDialog = view.findViewById<RecyclerView>(R.id.rvComentPubDialog)
        rvComentPubDialog.layoutManager = LinearLayoutManager(requireContext())
        adapter = ComentPubAdapter(commentList)
        rvComentPubDialog.adapter = adapter
        fetchComments()
        return view
    }

    private fun fetchComments() {
        val activityId = arguments?.getString(ARG_ACTIVITY_ID)
        if (activityId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("reviews2")
                .orderBy("time")
                .whereEqualTo("idactivity", activityId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val userId = document.getString("iduser")
                        val commentText = document.getString("comment")
                        val timestamp = document.getTimestamp("time")

                        if (userId != null && commentText != null && timestamp != null) {
                            fetchUserName(userId) { userName ->
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                                val formattedDate = dateFormat.format(timestamp.toDate())
                                val commentData = ComentPubData(userName, commentText, formattedDate)
                                commentList.add(commentData)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Manejar errores de consulta
                }
        }
    }

    private fun fetchUserName(userId: String, callback: (String) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userName = document.getString("name")
                    if (userName != null) {
                        callback.invoke(userName)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Manejar errores de consulta
            }
    }
}