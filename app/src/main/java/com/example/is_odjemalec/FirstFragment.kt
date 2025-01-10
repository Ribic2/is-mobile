package com.example.is_odjemalec

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Service.ApiClient
import com.example.Service.Data.Ticket
import com.example.is_odjemalec.databinding.FragmentFirstBinding
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = view.findViewById(R.id.tickets_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            try {
                val tickets = ApiClient.apiService.getTickets("SecretKey").enqueue(object : retrofit2.Callback<List<Ticket>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<Ticket>>,
                        response: retrofit2.Response<List<Ticket>>
                    ) {
                        if (response.isSuccessful) {
                            val tickets = response.body()
                            recyclerView.adapter = tickets?.let { TicketsAdapter(it) }

                            Log.d("tickets", tickets.toString())
                        } else {
                            Log.e("tickets", "Failed to fetch tickets: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<List<Ticket>>, t: Throwable) {
                        Log.e("tickets", "Error fetching tickets: ${t.message}")
                    }
                })



            } catch (e: Exception) {
                Log.e("tickets", "Error fetching tickets: ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}