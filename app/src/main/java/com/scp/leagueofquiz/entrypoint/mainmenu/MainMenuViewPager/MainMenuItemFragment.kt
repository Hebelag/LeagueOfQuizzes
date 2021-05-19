package com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuFragmentDirections

class MainMenuItemFragment : Fragment() {
    // Widgets
    private lateinit var mPedestalImageView: ImageView
    private lateinit var mLightShineView: ImageView
    private lateinit var mTitle: TextView

    // Variables
    private var mMainMenuItem: MainMenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainMenuItem = arguments?.getParcelable("mainMenuItem")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu_viewpager_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPedestalImageView = view.findViewById(R.id.pedestal_image)
        mLightShineView = view.findViewById(R.id.light_shine_image)
        mTitle = view.findViewById(R.id.title)
        init()
    }

    private fun init() {
        if (mMainMenuItem != null) {
            val options = RequestOptions().placeholder(R.drawable.ic_launcher_background)
            Glide.with(requireActivity())
                    .setDefaultRequestOptions(options)
                    .load(mMainMenuItem?.pedestal_image)
                    .into(mPedestalImageView)
            Glide.with(requireActivity())
                    .setDefaultRequestOptions(options)
                    .load(mMainMenuItem?.light_image)
                    .into(mLightShineView)
            mTitle.text = mMainMenuItem?.title
            mPedestalImageView.setOnClickListener {
                val primaryNavFragment = parentFragmentManager.primaryNavigationFragment
                val quizType = mMainMenuItem?.quizType
                if (primaryNavFragment != null && quizType != null) {
                    NavHostFragment.findNavController(primaryNavFragment)
                            .navigate(MainMenuFragmentDirections.actionQuizMode(quizType))
                }
            }
        }
    }

    companion object {
        fun getInstance(mainMenuItem: MainMenuItem?): MainMenuItemFragment {
            val fragment = MainMenuItemFragment()
            if (mainMenuItem != null) {
                val bundle = Bundle()
                bundle.putParcelable("mainMenuItem", mainMenuItem)
                fragment.arguments = bundle
            }
            return fragment
        }
    }
}