package com.rudyrachman16.belajarmvvmhilt.ui.detail_meal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rudyrachman16.belajarmvvmhilt.R
import com.rudyrachman16.belajarmvvmhilt.core.model.domain.Meal
import com.rudyrachman16.belajarmvvmhilt.core.repository.Status
import com.rudyrachman16.belajarmvvmhilt.core.utils.CategorySetter
import com.rudyrachman16.belajarmvvmhilt.databinding.FragmentDetailMealBinding
import com.rudyrachman16.belajarmvvmhilt.ui.MainActivity
import com.rudyrachman16.belajarmvvmhilt.utils.ActivityViewUtils.showToast
import com.rudyrachman16.belajarmvvmhilt.utils.ImageViewUtils.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMealFragment : Fragment() {

    companion object {
        private const val ID_MEAL_KEY = "mealId"
    }

    private var binding: FragmentDetailMealBinding? = null
    private val bind get() = binding!!

    private var id: String = ""

    private val viewModel: DetailMealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString(ID_MEAL_KEY, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMealBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadingVisibility(isLoading: Boolean) {
        bind.detLoad.isVisible = isLoading
        bind.detContent.isVisible = !isLoading
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (id == "") {
            findNavController().popBackStack()
            bind.root.showToast("ID empty")
            return
        }

        viewModel.getData(id).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Error -> {
                    loadingVisibility(false)
                    bind.root.showToast("Terjadi kesalahan silahkan coba lagi")
                    findNavController().popBackStack()
                }

                is Status.Loading -> loadingVisibility(true)
                is Status.Success -> {
                    loadingVisibility(false)
                    initView(it.data)
                }
            }
        }
    }

    private fun initView(meal: Meal) {
        bind.ivMealThumb.load(meal.strMealThumb, false)
        bind.tvMealStr.text = meal.strMeal
        bind.tvMealArea.text = meal.strArea
        bind.tvMealCategory.text = meal.strCategory

        bind.rvMealIngredients.adapter = DetailMealIngredientAdapter(
            meal.measurements ?: listOf(),
            meal.ingredients ?: listOf()
        )

        val instruction = HtmlCompat.fromHtml(
            "<ol><li>${meal.strInstructions?.replace("\n", "<li>")}</ol>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        bind.tvMealInstruction.text = instruction

        (requireActivity() as MainActivity).setToolbarTitle(meal.strMeal)

        if (meal.strDrinkAlternate == null || meal.strDrinkAlternate == "") {
            bind.detDrink.isVisible = false
        } else {
            bind.detDrink.isVisible = true
            bind.detDrink.text = getString(R.string.drink_recommendation, meal.strDrinkAlternate)
        }

        if (meal.strSource == null || meal.strSource == "") {
            bind.detDrink.isVisible = false
        } else {
            bind.detSource.isVisible = true
            bind.detSource.text = meal.strSource
            bind.detSource.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(meal.strSource)
                startActivity(intent)
            }
        }

        if (meal.strYoutube == null || meal.strYoutube == "") {
            bind.youtubePlayerView.isVisible = false
            return
        }

        val ytbId =
            meal.strYoutube?.replace("https://www.youtube.com/watch?v=", "")?.substring(0..10) ?: ""

        lifecycle.addObserver(bind.youtubePlayerView)
        bind.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(ytbId, 0f)
                youTubePlayer.pause()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).showOrHideToolbar()
    }
}