package main.src.etherscan.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import main.src.etherscan.R
import main.src.etherscan.viewmodels.WalletViewModel

class WaitFragment : Fragment() {

    //View animation deprecated

    private val ANIMATION_DURATION = 500L

    var upper_left_triangle: View? = null
    var upper_right_triangle: View? = null
    var middle_left_triangle: View? = null
    var middle_right_triangle: View? = null
    var bottom_left_triangle: View? = null
    var bottom_right_triangle: View? = null

    var distanceX = 0
    var distanceY = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)
        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                findNavController().navigate(R.id.walletFragment, null)
            }
        })

        val view: View = inflater.inflate(R.layout.wait_fragment, container, false)

        animateLoader(view)

        return view
    }

    private fun animateLoader(view: View) {
        upper_left_triangle = view.findViewById(R.id.upper_left_triangle)
        upper_right_triangle = view.findViewById(R.id.upper_right_triangle)
        middle_left_triangle = view.findViewById(R.id.middle_left_triangle)
        middle_right_triangle = view.findViewById(R.id.middle_right_triangle)
        bottom_left_triangle = view.findViewById(R.id.bottom_left_triangle)
        bottom_right_triangle = view.findViewById(R.id.bottom_right_triangle)

        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);

        val moveX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 366f)
        val moveY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 825f)
        val minusMoveX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -366f)
        val minusMoveY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -725f)

        val UpperLeftAnim =
            ObjectAnimator.ofPropertyValuesHolder(upper_left_triangle, moveX, moveY, alpha)
        val UpperRightAnim =
            ObjectAnimator.ofPropertyValuesHolder(upper_right_triangle, minusMoveX, moveY, alpha)

        val MiddleLeftAnim = ObjectAnimator.ofPropertyValuesHolder(
            middle_left_triangle,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 625f),
            alpha
        )
        val MiddleRightAnim = ObjectAnimator.ofPropertyValuesHolder(
            middle_right_triangle,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -625f),
            alpha
        )

        val BottomLeftAnim =
            ObjectAnimator.ofPropertyValuesHolder(bottom_left_triangle, moveX, minusMoveY, alpha)
        val BottomRightAnim = ObjectAnimator.ofPropertyValuesHolder(
            bottom_right_triangle,
            minusMoveX,
            minusMoveY,
            alpha
        )

        val animatorSet = AnimatorSet().setDuration(ANIMATION_DURATION);
        animatorSet.playSequentially(
            UpperLeftAnim,
            UpperRightAnim,
            MiddleRightAnim,
            MiddleLeftAnim,
            BottomRightAnim,
            BottomLeftAnim
        );
        animatorSet.start()
    }
}

