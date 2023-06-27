package com.rudyrachman16.belajarmvvmhilt.ui.recommended

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rudyrachman16.belajarmvvmhilt.R
import com.rudyrachman16.belajarmvvmhilt.databinding.FragmentRecommendedBinding
import com.rudyrachman16.belajarmvvmhilt.utils.ActivityViewUtils.showToast
import com.rudyrachman16.belajarmvvmhilt.core.repository.Status
import com.rudyrachman16.belajarmvvmhilt.core.utils.CategorySetter
import com.rudyrachman16.belajarmvvmhilt.ui.MainActivity
import com.rudyrachman16.belajarmvvmhilt.ui.recommended.RecommendedFragmentDirections.ActionRecommendedFragmentToDetailMealFragment
import com.rudyrachman16.belajarmvvmhilt.utils.GridBoxCountUtils.countBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendedFragment : Fragment() {
    private var binding: FragmentRecommendedBinding? = null
    private val bind get() = binding!!

    private val viewModel: RecommendedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        bind.loadingRecom.isVisible = isLoading
        bind.rvMealsRecommended.isVisible = !isLoading
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.rvMealsRecommended.apply {
            layoutManager = GridLayoutManager(context, context.countBox())
            setHasFixedSize(true)
        }

        viewModel.recommended.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Error -> {
                    showLoading(false)
                    bind.root.showToast("Terjadi kesalahan silahkan coba lagi nanti")
                }

                Status.Loading -> {
                    showLoading(true)
                }

                is Status.Success -> {
                    showLoading(false)
                    bind.recomEmpty.isVisible = it.data.isEmpty()
                    bind.rvMealsRecommended.isVisible = it.data.isNotEmpty()

                    if (it.data.isEmpty()) return@observe

                    val adapter = RecommendedAdapter { idMeal ->
                        println(idMeal)
                        val action =
                            RecommendedFragmentDirections.actionRecommendedFragmentToDetailMealFragment(
                                idMeal
                            )
                        findNavController().navigate(action)
                    }
                    bind.rvMealsRecommended.adapter = adapter
                    adapter.submitList(it.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setToolbarTitle(CategorySetter.getCategory())
        (requireActivity() as MainActivity).showOrHideToolbar()
    }
}