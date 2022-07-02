package com.icdominguez.starwarsencyclopedia.presentation.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.icdominguez.starwarsencyclopedia.R
import com.icdominguez.starwarsencyclopedia.data.common.checkIfIsEmpty
import com.icdominguez.starwarsencyclopedia.databinding.FragmentFilmInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmInfoFragment : DialogFragment() {

    private val args: FilmInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentFilmInfoBinding
    private val filmInfoViewModel: FilmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {

        args.film?.let {
            filmInfoViewModel.getFilmByName(it)

            filmInfoViewModel.getFilmByNameStatus.observe(
                viewLifecycleOwner,
                { film ->
                    with(binding) {
                        Glide.with(requireActivity()).load(film.photo).into(imageViewFilm)
                        textViewFilmName.text = film.name.checkIfIsEmpty()
                        textViewDateCreatedValue.text = film.dateCreated.checkIfIsEmpty()
                        textViewDirectorValue.text = film.director.checkIfIsEmpty()
                        textViewProducerValue.text = film.producers.toString().checkIfIsEmpty()
                        textViewOpeningCawl.text = film.openingCawl.checkIfIsEmpty()

                        imageViewFilmInfoBack.setOnClickListener {
                            findNavController().popBackStack()
                        }

                        showView()
                    }
                }
            )
        }
    }

    private fun showView() {
        with(binding) {
            progressBarLoadingFilmInfo.visibility = View.GONE

            imageViewFilm.visibility = View.VISIBLE
            textViewFilmName.visibility = View.VISIBLE
            textViewDateCreatedLabel.visibility = View.VISIBLE
            textViewDirectorLabel.visibility = View.VISIBLE
            textViewProducerLabel.visibility = View.VISIBLE
            view5.visibility = View.VISIBLE
            textViewOpeningCawl.visibility = View.VISIBLE

            imageViewFilmInfoBack.visibility = View.VISIBLE
        }
    }
}
