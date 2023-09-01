/* Source:

Tutorials point

This example demonstrates how to Add and Remove Views in Android Dynamically in Kotlin.

https://www.tutorialspoint.com/add-and-remove-views-in-android-dynamically-in-kotlin
*/
package com.example.addandremoveviewdynamically

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import com.akki.circlemenu.CircleMenu
import com.akki.circlemenu.OnCircleMenuItemClicked
import com.example.addandremoveviewdynamically.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity(),
    View.OnTouchListener,
    View.OnDragListener,
    OnCircleMenuItemClicked {
    lateinit var binding: ActivityMainBinding
    lateinit var soundEffect: SoundEffect
    lateinit var frontColumns: ArrayList<LinearLayout>
    lateinit var shadowColumns: ArrayList<LinearLayout>
    lateinit var counter: Counter

    //    lateinit var prentLinearLayout: LinearLayout
    //    lateinit var staticAbacus: LinearLayout
    lateinit var myAnimations: Animations
    var factor1: Int = 10
    var factor2: Int = 10

    //array for saving columns size
    val deltaHorizontalCounters = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val animated =  mutableListOf(false, false, false, false, false, false, false, false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
        //////////////////////////////////////////////////////////////////
        counter = Counter()
        val CircleMenu = findViewById<CircleMenu>(R.id.circle_menu)
        CircleMenu.setOnMenuItemClickListener(this)

        //////////////////////////////////////////////////////////////////
        title = "primary arithmetic"
        myAnimations = Animations()
        soundEffect = SoundEffect(this)
        frontColumns = ArrayList()
        shadowColumns = ArrayList()
        for (i in 0..10) {
            frontColumns.add(binding.parentLinearLayout.getChildAt(i) as LinearLayout)
            shadowColumns.add(binding.underParentLinearlayout.getChildAt(i) as LinearLayout)
        }
        /////////////////////////////////////////////////////////////
        factor1 = (2..9).random()
        factor2 = (2..9).random()
        //set up first state:
        setUpFirstState(factor1, factor2)
        /////////////////////////////////////////////////////////////
        binding.buttonReset.setOnClickListener {
            factor1 = (2..9).random()
            factor2 = (2..9).random()
            setUpFirstState(factor1, factor2)
        }
        /////////////////////////////////////////////////////////////
//        binding.buttonmove.setOnClickListener {
//            //binding.parentLinearLayout.animate().translationY(300F).duration=0
//            myAnimations.move(binding.parentLinearLayout.getChildAt(1))
//        }
//        binding.buttonStop.setOnClickListener {
//            myAnimations.stop(binding.parentLinearLayout)
//        }
        /////////////////////////////////////////////////////////////
        //???????????????????????????????????????
//        binding.buttonEnd.setOnClickListener {
//            for (i in 1..8) {
//                val column = binding.parentLinearLayout.getChildAt(9) as LinearLayout
//                addOverCube(column)
//            }
//            for (i in 0..7) {
//                val column = binding.parentLinearLayout.getChildAt(9) as LinearLayout
//                column.getChildAt(i).alpha = 0F
//            }
//
//            for (i in 1..8) {
//                val column = binding.parentLinearLayout.getChildAt(i) as LinearLayout
//                column.getChildAt(0).animate().apply {
//                    alpha(0F)
//                    duration = (i * 1000).toLong()
//                }.start()
//                // fade(column.getChildAt(0))
//            }
//
//            for (i in 7 downTo 0) {
//                frontColumns[9].getChildAt(i).animate().apply {
//                    alpha(1F)
//                    duration = (7000 - i * 1000).toLong()
//                }.start()
//                //reversFade(binding.column9.getChildAt(i))
//            }
//
//        }


    }
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    //set up  first state
    fun setUpFirstState(m: Int, n: Int) {
        soundEffect.playSoundSetup()
        for (i in 0..10) {
            if (animated[i]) {
                myAnimations.zoomReverse(frontColumns[i])
            }
        }
        //set up  first state
        for (i in 1..10) deltaHorizontalCounters[i] = 0
        // deleting cubes from above and under columns (column0 filled with vertical counter)
        for (i in 1..10) {
            frontColumns[i].removeAllViews()
            shadowColumns[i].removeAllViews()
        }
        // filling above and under columns with cubes

        for (i in 1..m) {
            for (j in 1..n) {

                val column = binding.parentLinearLayout.getChildAt(i) as LinearLayout
                addOverCube(column)

                // addOverCube(frontColumns[i])
                addUnderCube(shadowColumns[i])

            }

        }


        //set up coordinates
        //setCoordinates()
        //setColumnsCounter()
        //set drag and drop
        setDropAbles()
        setDragAbles()

    }

    ////////////////////////////////////////////////////////////
    private fun addOverCube(column: LinearLayout) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cubView: View = inflater.inflate(R.layout.field, null)
        column.addView(cubView, -2)
    }

    private fun addUnderCube(column: LinearLayout) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cubView: View = inflater.inflate(R.layout.field, null)
        cubView.alpha = 0.3F
        column.addView(cubView, 0)
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

    private fun setColumnsCounter() {
        for (i in 0..10) {
            val columnCounterText =
                binding.columnsCounterLinearlayout.getChildAt(i) as TextView
            columnCounterText.text = "0"
            columnCounterText.visibility = View.INVISIBLE
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
        binding.rootConstraintlayout.setOnDragListener(this)
    }

    ////////////////////////////////////////////////////////////
    fun onDelete(view: View) {
        val parent = view.parent
//        dynamicAbacus!!.removeView(view.parent as View)
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
    private fun setPrimaryHorizontalCounter(m: Int, n: Int) {
        for (i in 1..m) {
            val primaryHorizontalCounterTextView =
                binding.primaryHorizontalCounterLinearlayout.getChildAt(i) as TextView
            primaryHorizontalCounterTextView.text = " ${n}"
            primaryHorizontalCounterTextView.visibility = View.VISIBLE
        }

        for (i in m + 1..10) {
            val primaryHorizontalCounterTextView =
                binding.primaryHorizontalCounterLinearlayout.getChildAt(i) as TextView
            primaryHorizontalCounterTextView.text = "0"
            primaryHorizontalCounterTextView.visibility = View.INVISIBLE
        }

//        for (i in 1..10) {
//            val horizontalCounterTextView =
//                binding.secondaryHorizontalCounterLinearlayout.getChildAt(i) as TextView
//            horizontalCounterTextView.text = "0"
//            horizontalCounterTextView.visibility = View.INVISIBLE
//        }
    }

    //////////////////////////////////////////////////////////////////
    private fun addToHorizontalCounter(i: Int) {
        deltaHorizontalCounters[i]++
        val primaryHorizontalCounterTextView =
            binding.primaryHorizontalCounterLinearlayout.getChildAt(i) as TextView
        if (i <= factor1) {
            primaryHorizontalCounterTextView.text = "${factor2}+${deltaHorizontalCounters[i]}"
        } else {
            primaryHorizontalCounterTextView.visibility = View.VISIBLE
            primaryHorizontalCounterTextView.text = "${deltaHorizontalCounters[i]}"
        }
    }

    //////////////////////////////////////////////////////////////////
    private fun substractFromHorizontalCounter(k: Int) {
        deltaHorizontalCounters[k]--
        val primaryHorizontalCounterTextView =
            binding.primaryHorizontalCounterLinearlayout.getChildAt(k) as TextView
        if (k <= factor1) {
            primaryHorizontalCounterTextView.text = "${factor2}${deltaHorizontalCounters[k]}"
        } else {
            primaryHorizontalCounterTextView.visibility = View.VISIBLE
            primaryHorizontalCounterTextView.text = "${deltaHorizontalCounters[k]}"
        }
    }

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
    fun runFinalHorizontalCounter(m: Int) {
        val finalCounterTextView =
            binding.columnsCounterLinearlayout.getChildAt(m) as TextView
        val finalCounter = finalCounterTextView.text.toString().toInt()
//        var column = binding.dynamicAbacus.getChildAt(m) as LinearLayout
//        finalCounterTextView.text = "${column.size}"
        if (finalCounter == 9) finalCounterTextView.setTextColor(Color.MAGENTA)
        finalCounterTextView.visibility = View.VISIBLE
    }

    //////////////////////////////////////////////////////////////////
    fun onAddPaleCub(column: LinearLayout) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val paleCubView: View = inflater.inflate(R.layout.field2, null)
//        paleCubView.id = dynamicAbacus!!.childCount - 1
        column.addView(paleCubView, 0)
    }

    //////////////////////////////////////////////////////////////////
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            val shadow = View.DragShadowBuilder(v)
            v!!.startDragAndDrop(null, shadow, v, 0)
            v.visibility = View.GONE
            return true
        } else {
            return false
        }
    }

    //////////////////////////////////////////////////////////
    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        if (event?.action == DragEvent.ACTION_DROP&& v is LinearLayout && v.size < 10) {
            val view = event.localState as View
            val from = view.parent as LinearLayout
            val to = v as LinearLayout
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
                    soundEffect.playSoundCorrectBlockMove()
                    view.alpha = 1F
                    myAnimations.blink(to)

                    val i = (from.getParent() as ViewGroup).indexOfChild(from)
                    val j = (to.getParent() as ViewGroup).indexOfChild(to)
                    //addToHorizontalCounter(j)
                    //substractFromHorizontalCounter(i)
//                    setSecondaryHorizontalCounter(i, j)

                    //runFinalHorizontalCounter(i)
                    //runFinalHorizontalCounter(j)
                    //addOverCube(from)
//                    to.setBackgroundColor(Color.YELLOW)
                }

                if (to.size == 10) {
                    soundEffect.playSoundETenGroupMade()
                    //changing column to stack
                    // myAnimations.zoom(to)
                    val j = (to.getParent() as ViewGroup).indexOfChild(to)
                    animated[j] = true
                    for (i in 0..9) {
                        to.getChildAt(i).animate()
                            .apply {
                               //rotation for seeing stack
                               // rotation(-19F)
                                duration = (1000).toLong()

                            }
                    }

                }
            }
            return true
        }
        else if (event?.action == DragEvent.ACTION_DROP && v is LinearLayout && v.size >= 10) {
            val view = event.localState as View
            val from = view.parent as LinearLayout
            val to = v as LinearLayout
            view.visibility = View.VISIBLE
            soundEffect.playSoundIncorrectBlockMove()
            view.animate().apply {
                rotationBy(90F)
                setDuration(500)
            }.start()
            view.alpha = 1f
            return true
        }
        //wen they try move a block out of abacus
        else if (event?.action == DragEvent.ACTION_DROP && v !is LinearLayout) {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
            val view = event.localState as View
            // val from = view.parent as LinearLayout
            //from.addView(view)
            view.visibility = View.VISIBLE
            soundEffect.playSoundIncorrectBlockMove()
            view.animate().apply {
                rotationBy(90F)
                setDuration(500)
            }.start()
            return true
        }

        else {
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
}

