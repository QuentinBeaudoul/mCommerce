package com.beaudoul.mcommerce.controllers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.beaudoul.mcommerce.R
import com.beaudoul.mcommerce.adapter.ProductAdapter
import com.beaudoul.mcommerce.application.App
import com.beaudoul.mcommerce.databinding.FragmentShopingListBinding
import com.beaudoul.mcommerce.viewmodel.ProductViewModel
import com.beaudoul.mcommerce.viewmodel.ProductViewModelFactory


class ShoppingListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentShopingListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as App).repository)
    }
    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = _recyclerView!!
    private var _swipeRefreshLayout: SwipeRefreshLayout? = null
    private val swipeRefreshLayout get() = _swipeRefreshLayout!!
    private var _adapter: ProductAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        productViewModel.allProducts.observe(this, Observer { products ->
            products?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShopingListBinding.inflate(inflater, container, false)
        _adapter = ProductAdapter()
        _recyclerView = binding.productRecyclerView
        recyclerView.adapter = adapter
        _swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_refresh -> {
                swipeRefreshLayout.isRefreshing = true
                productViewModel.updateDatabase()
                swipeRefreshLayout.isRefreshing = false
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onRefresh() {
        productViewModel.updateDatabase()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}