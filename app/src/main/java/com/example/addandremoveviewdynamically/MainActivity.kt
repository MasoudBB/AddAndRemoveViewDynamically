/* Source:

Tutorials point

This example demonstrates how to Add and Remove Views in Android Dynamically in Kotlin.

https://www.tutorialspoint.com/add-and-remove-views-in-android-dynamically-in-kotlin
*/
package com.example.addandremoveviewdynamically

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.akki.circlemenu.OnCircleMenuItemClicked
import com.example.addandremoveviewdynamically.AnswerState.FINAL_ANSWER
import com.example.addandremoveviewdynamically.AnswerState.NUMBER_OF_ADDED_CUBES
import com.example.addandremoveviewdynamically.AnswerState.NUMBER_OF_COMPLETED_BUNCHES
import com.example.addandremoveviewdynamically.AnswerState.START
import com.example.addandremoveviewdynamically.MyApplication.Companion.context
import com.example.addandremoveviewdynamically.databinding.ActivityMainBinding
import com.mcdev.quantitizerlibrary.AnimationStyle


class MainActivity : AppCompatActivity(),
    View.OnTouchListener,
    View.OnDragListener,
    OnCircleMenuItemClicked {

    private lateinit var binding: ActivityMainBinding

    //a lateinit variable used to manage sound effects in the code.
    private lateinit var soundEffect: SoundManager

    // a lateinit variable  used to handle animations in the code.
    private lateinit var myAnimations: Animations

    private lateinit var frontColumns: ArrayList<LinearLayout>
    private lateinit var shadowColumns: ArrayList<LinearLayout>

    private lateinit var counter: Counter

    //lateinit var prentLinearLayout: LinearLayout
    //lateinit var staticAbacus: LinearLayout

    private var factor1: Int = 10
    private var factor2: Int = 10

    // an integer array named "deltaHorizontalCounters"  of 11 elements.
    // Each element represents a counter or delta value associated with a specific index,
    // and is initially set to 0.
    private val deltaHorizontalCounters = IntArray(11)

    // Initialize a Boolean array named " with  11 elements.
    // Each element represents the animation state for a specific item or index,
    // and is initially set to false.
    private val animated = BooleanArray(11){true}

    // answer state
    private var answerState = START


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //providing a fullscreen UI
        window.addFlags(FLAG_LAYOUT_NO_LIMITS)

        //text animation style of the "hQ" view.
        //This animation style determines how the text will animate when displayed.
        binding.hQ.textAnimationStyle = AnimationStyle.FALL_IN

        //////////////////////////////////////////////////////////////////
        title = "primary arithmetic"
        myAnimations = Animations()
        soundEffect = SoundManager(this)

        frontColumns = ArrayList()
        shadowColumns = ArrayList()
        for (i in 0..10) {
            frontColumns.add(binding.parentLinearLayout.getChildAt(i) as LinearLayout)
            shadowColumns.add(binding.underParentLinearlayout.getChildAt(i) as LinearLayout)
        }
        /////////////////////////////////////////////////////////////

        //selection of multiplicative factors
        factor1 = (2..9).random()
        factor2 = (2..9).random()

        //set up first state:
        setUpFirstState(factor1, factor2)

        //hide cardViewQ visibility
        binding.cardViewHQ.visibility = View.INVISIBLE

        // a click listener to generate random factors between 2 and 9,
        // set up the initial state using the generated factors,
        // and make the "cardViewHQ" invisible.
        binding.buttonReset.setOnClickListener {
            factor1 = (2..9).random()
            factor2 = (2..9).random()
            setUpFirstState(factor1, factor2)
            binding.cardViewHQ.visibility = View.INVISIBLE
        }

        // Set click listener to update the question text,
        // perform animations,
        // and modify the answer
        binding.buttonNextStep.setOnClickListener {
            binding.hQ.textAnimationStyle = AnimationStyle.FALL_IN
            binding.hQ.value = 0

            if (answerState == START) {
                binding.textViewQuestion.text =
                    "How many blocks did you add to each completed bunch?"
                myAnimations.bounce(binding.textViewQuestion)
                binding.cardViewHQ.visibility = View.VISIBLE
                answerState = NUMBER_OF_COMPLETED_BUNCHES

            } else if (answerState == NUMBER_OF_COMPLETED_BUNCHES) {
                binding.textViewQuestion.text = "How many bunches of tens have you made?"
                myAnimations.bounce(binding.textViewQuestion)
                answerState = NUMBER_OF_ADDED_CUBES

            } else if (answerState == NUMBER_OF_ADDED_CUBES) {
                binding.textViewQuestion.text = "How many single blocks are left?"
                myAnimations.bounce(binding.textViewQuestion)
                answerState = FINAL_ANSWER

            } else if (answerState == FINAL_ANSWER) {
                answerState = AnswerState.NUMBER_OF_REMINDED_SINGLE_BLOCKS
                binding.textViewQuestion.text = "What is the product of  $factor1 \u00D7 $factor2?"
                myAnimations.bounce(binding.textViewQuestion)
               // binding.cardViewHQ.visibility = View.INVISIBLE
            }
        }

        // Set click listener to invoke the "rotate()"
        binding.buttonRotate.setOnClickListener {
            rotate()
        }

    }

    ////////////////////////////////////////////////////////////
    // Functions Segment
    ////////////////////////////////////////////////////////////

    //set up first state
    fun setUpFirstState(m: Int, n: Int) {
        soundEffect.playSoundSetup()
        for (i in 0..10) {
            if (animated[i]) {

                    myAnimations.zoomReverse(frontColumns[i])
            }

        //set up delta horizontal counters
        for (i in 1..10) deltaHorizontalCounters[i] = 0
        // delete cubes from above and under columns (column0 filled with vertical counter)
        for (i in 1..10) {
            frontColumns[i].removeAllViews()
            shadowColumns[i].removeAllViews()
        }
        // fill above and under columns with cubes
        for (i in 1..m) {
            for (j in 1..n) {
                val column = binding.parentLinearLayout.getChildAt(i) as LinearLayout
                addUpperCube(column)
                // addOverCube(frontColumns[i])
                addLowerCube(shadowColumns[i])
            }

        }
        //set up coordinates

        //setCoordinates()
        //setColumnsCounter()

        //set drag and drop
        setDropAbles()
        setDragAbles()


        }

        //set up question text
        answerState = START

        binding.textViewQuestion.text = getString(R.string.start)

    }

    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    private fun addUpperCube(column: LinearLayout) {
        val inflater = LayoutInflater.from(context)
        // above and under codes are equivalent
        //  val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cube: View = inflater.inflate(R.layout.field, null)
        column.addView(cube, -2)
    }

    private fun addLowerCube(column: LinearLayout) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cube: View = inflater.inflate(R.layout.field, null)
        cube.alpha = 0.3F
        column.addView(cube, 0)
    }

    private fun setCoordinates() {
        for (i in 1..10) {
            binding.horizontalCoordinatLinearlayout.getChildAt(i).visibility = View.INVISIBLE
            binding.overColumn0.getChildAt(i)?.alpha = 0.7F
        }
        //
        for (i in 0..factor2 - 1) {
            binding.overColumn0.getChildAt(9 - i)?.alpha = 1F
        }
        //
        for (j in 1..factor1) {
            binding.horizontalCoordinatLinearlayout.getChildAt(j).visibility = View.VISIBLE
            binding.horizontalCoordinatLinearlayout.getChildAt(j).alpha = 1F
        }

    }


    private fun setDropAbles() {
        for (i in 1..10) {
            for (j in 0..9) {
                frontColumns[i].getChildAt(j)?.setOnTouchListener(this)
            }
        }
    }

    private fun setDragAbles() {
        for (i in 1..10) {
            frontColumns[i].setOnDragListener(this)
        }
        // needs to complete
        binding.root.setOnDragListener(this)
    }

    ////////////////////////////////////////////////////////////
    fun onDelete(view: View) {
        val parent = view.parent
   //dynamicAbacus!!.removeView(view.parent as View)
        binding.overColumn1.removeView(view.parent as View)
    }
    //////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////
//    fun setPrimaryVerticalCounter(m: Int, n: Int) {
//        for (i in 1..n - 1) {
//            val verticalCounterTextView =
//                binding.primaryVerticalCounterLinearlayout?.getChildAt(i) as TextView
//            verticalCounterTextView.text = "$factor1"
//        }
//    }
    //////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////
//    fun setSecondaryHorizontalCounter(i: Int, j: Int) {
//        var fromCounterTextView =
//            binding.secondaryHorizontalCounterLinearlayout.getChildAt(i) as TextView
//        var fromCounter = fromCounterTextView.text.toString().toInt()
//        fromCounter--
//        fromCounterTextView.text = "$fromCounter"
//        if (fromCounter < 0) fromCounterTextView.setTextColor(Color.MAGENTA)
//        fromCounterTextView.visibility = View.VISIBLE
//
//        val toCounterTextView =
//            binding.secondaryHorizontalCounterLinearlayout.getChildAt(j) as TextView
//        var toCounter = toCounterTextView.text.toString().toInt()
//        toCounter++
//        toCounterTextView.text = "$toCounter"
//        if (toCounter > 0) toCounterTextView.setTextColor(Color.CYAN)
//        toCounterTextView.visibility = View.VISIBLE
//    }

    //////////////////////////////////////////////////////////////////
    private fun onAddPaleCub(column: LinearLayout) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val paleCubView: View = inflater.inflate(R.layout.field2, null)
//        paleCubView.id = dynamicAbacus!!.childCount - 1
        column.addView(paleCubView, 0)
    }

    //////////////////////////////////////////////////////////////////
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return if (event!!.action == MotionEvent.ACTION_DOWN) {
            val shadow = View.DragShadowBuilder(v)
            v!!.startDragAndDrop(null, shadow, v, 0)
            v.visibility = View.GONE
            true
        } else {
            false
        }

    }

    //////////////////////////////////////////////////////////
    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        if (event?.action == DragEvent.ACTION_DROP && v is LinearLayout && v.size < 10) {
            val view = event.localState as View
            val from = view.parent as LinearLayout
            val to = v
            if (v == from) {
                view.visibility = View.VISIBLE
            } else {
                to.startLayoutAnimation()
                from.removeView(view)
                // این دو خط برای تغییر رنگ است
                //to.addView(view, 0)
                onAddPaleCub(to)
                view.visibility = View.VISIBLE

                if (to.size < 10) {
                    soundEffect.playSoundCorrectMovement()
                    showToast("The number of blocks in this column increased to ${to.size}")
                    view.alpha = 1F
                    myAnimations.blink(to)

                    val i = (from.parent as ViewGroup).indexOfChild(from)
                    val j = (to.parent as ViewGroup).indexOfChild(to)
                    //addToHorizontalCounter(j)
                    //substractFromHorizontalCounter(i)
//                    setSecondaryHorizontalCounter(i, j)

                    //runFinalHorizontalCounter(i)
                    //runFinalHorizontalCounter(j)
                    //addOverCube(from)
//                    to.setBackgroundColor(Color.YELLOW)
                    //make moved blocks dragable
                    setDragAbles()
                    setDropAbles()

                }

                if (to.size == 10) {
                    soundEffect.playSoundTenMade()
                    showToast("You have completed this column")
                    //changing column to stack
                    // myAnimations.zoom(to)

                    //blinking the column
                    myAnimations.blink(to)
                    val j = (to.parent as ViewGroup).indexOfChild(to)
                    animated[j] = true
                    for (i in 0..9) {
                        to.getChildAt(i).animate()
                            .apply {
                                //rotation for seeing stack
                                // rotation(-19F)
                                duration = (3000).toLong()

                            }
                    }

                }
            }
            return true
        } else if (event?.action == DragEvent.ACTION_DROP && v is LinearLayout && v.size >= 10) {
            val view = event.localState as View
            val from = view.parent as LinearLayout
            val to = v
            view.visibility = View.VISIBLE
            soundEffect.playSoundIncorrectMovement()
            showToast("You have already completed this column")
            view.animate().apply {
                rotationBy(90F)
                duration = 500
            }.start()
            view.alpha = 1f
            return true
        }
        //wen they try move a block out of abacus
        else if (event?.action == DragEvent.ACTION_DROP && v !is LinearLayout) {
            val view = event.localState as View
            // val from = view.parent as LinearLayout
            //from.addView(view)
            view.visibility = View.VISIBLE
            soundEffect.playSoundIncorrectMovement()
            Toast.makeText(this, "Stack the blocks", Toast.LENGTH_SHORT).show()
            view.animate().apply {
                rotationBy(90F)
                duration = 1000
            }.start()
            return true
        } else {
            return true
        }
    }

    //////////////////////////////////////////////////////////
    override fun onMenuItemClicked(id: Int) {
        var k = 2
        if (k == 1) {
            for (i in 1..counter.touchedCounter(id)) {
                val counter = binding.horizontalCoordinatLinearlayout.getChildAt(i) as TextView
                counter.setTextColor(Color.RED)
            }
        } else if (k == 2) {
            for (i in 1..counter.touchedCounter(id)) {
                val counter = binding.underColumn0.getChildAt(i) as TextView
                counter.setTextColor(Color.RED)
            }
        }
    }

    // Display a short duration toast message with the specified "message" parameter.
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //rotate the matrix of cubes
    private fun rotate() {
        val rotatedFactor1 = factor2
        val rotatedFactor2 = factor1
        factor1 = rotatedFactor1
        factor2 = rotatedFactor2
        setUpFirstState(factor1, factor2)
    }

}

