package com.example.eufirestoredap.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eufirestoredap.FiloAdapters
import com.example.eufirestoredap.R
import com.example.eufirestoredap.viewModels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var v: View
    private lateinit var recFilo: RecyclerView
    private lateinit var filoListAdapter: FiloAdapters
    private lateinit var uploadButton: FloatingActionButton
 //    var filoList: MutableList<Filosofia> = ArrayList()


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main, container, false)
        recFilo = v.findViewById(R.id.rec_filo)
        uploadButton = v.findViewById(R.id.btn_add)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        recFilo.setHasFixedSize(true)
        recFilo.layoutManager = LinearLayoutManager(context)


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

        filoListAdapter = FiloAdapters(viewModel.filoList, requireContext()) { pos ->
            onItemClick(pos)
        }

        viewModel.filosofos.observe(this, Observer {
                filosofos -> filoListAdapter.notifyDataSetChanged()
        })

        uploadButton.setOnClickListener {
            onButtonClick()
        }

        recFilo.adapter = filoListAdapter
    }



    private fun onItemClick(position: Int) {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        val filo = viewModel.filoList[position]
        val filoArray = arrayOf<String>(filo.info, filo.escuela, filo.nombre, filo.siglo, filo.foto)

        val action = MainFragmentDirections.actionMainFragmentToDataFragment(filoArray)
        v.findNavController().navigate(action)
    }

    private fun onButtonClick() {
        val action = MainFragmentDirections.actionMainFragmentToUploadFragment()
        v.findNavController().navigate(action)
    }
}