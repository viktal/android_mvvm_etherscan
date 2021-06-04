package main.src.etherscan.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.res.Resources.getSystem
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.viewmodels.WalletViewModel

class WaitFragment : Fragment() {

    private val ANIMATION_DURATION = 400L

    var upper_left_triangle: View? = null
    var upper_right_triangle: View? = null
    var middle_left_triangle: View? = null
    var middle_right_triangle: View? = null
    var bottom_left_triangle: View? = null
    var bottom_right_triangle: View? = null

    private fun pxFromDp(dp: Float): Float {
        return dp * getSystem().displayMetrics.density
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val address: String = requireArguments().getString(BundleConstants.ADDRESS)!!
        val bundle = Bundle()
        bundle.putString(BundleConstants.ADDRESS, address)

        val viewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)
        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                Run.after(2000) {
                    findNavController().navigate(R.id.action_waitFragment_to_walletFragment, bundle)
                }
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

        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)

        val windowWidth = getSystem().displayMetrics.widthPixels.toFloat()
        val windowHeight = getSystem().displayMetrics.heightPixels.toFloat()

        val centerX = windowWidth / 2
        val centerY = windowHeight / 2

        val offsetX = pxFromDp(73f)

        val moveX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, centerX - offsetX)
        val moveY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, centerY - pxFromDp(38f))
        val minusMoveX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -centerX + offsetX)
        val minusMoveY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -centerY + pxFromDp(42f))

        val UpperLeftAnim =
            ObjectAnimator.ofPropertyValuesHolder(upper_left_triangle, moveX, moveY, alpha)
        val UpperRightAnim =
            ObjectAnimator.ofPropertyValuesHolder(upper_right_triangle, minusMoveX, moveY, alpha)

        val MiddleLeftAnim = ObjectAnimator.ofPropertyValuesHolder(
            middle_left_triangle,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_X, centerX - pxFromDp(3f)),
            alpha
        )
        val MiddleRightAnim = ObjectAnimator.ofPropertyValuesHolder(
            middle_right_triangle,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -centerX + pxFromDp(3f)),
            alpha
        )

        val BottomLeftAnim =
            ObjectAnimator.ofPropertyValuesHolder(
                bottom_left_triangle, moveX, minusMoveY, alpha
            )
        val BottomRightAnim =
            ObjectAnimator.ofPropertyValuesHolder(
                bottom_right_triangle, minusMoveX, minusMoveY, alpha
            )

        val animatorSet = AnimatorSet().setDuration(ANIMATION_DURATION)
        animatorSet.playSequentially(
            UpperLeftAnim,
            UpperRightAnim,
            MiddleRightAnim,
            MiddleLeftAnim,
            BottomRightAnim,
            BottomLeftAnim
        )
        animatorSet.start()
    }
}

class Run {
    companion object {
        fun after(delay: Long, process: () -> Unit) {
            Handler().postDelayed({
                process()
            }, delay)
        }
    }
}
