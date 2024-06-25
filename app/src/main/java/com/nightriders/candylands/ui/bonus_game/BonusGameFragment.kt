package com.nightriders.candylands.ui.bonus_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.nightriders.candylands.R
import com.nightriders.candylands.databinding.FragmentBonusGameBinding
import com.nightriders.candylands.domain.Coordinate
import com.nightriders.candylands.domain.MoveDirections
import com.nightriders.candylands.domain.PositionInField
import com.nightriders.candylands.domain.isGameOnPause
import com.nightriders.candylands.domain.launchFragment
import com.nightriders.candylands.ui.help.HelpFragment
import com.nightriders.candylands.ui.lvl_complete.LvlCompleteFragment
import com.nightriders.candylands.ui.lvl_failed.LvlFailedFragment
import com.nightriders.candylands.ui.pause.PauseFragment
import com.nightriders.candylands.ui.start.StartFragment

private const val BASE_SCORE = "base_score"
const val ANIM_DURATION:Long = 300


class BonusGameFragment : Fragment(), OnTouchListener {
    private val binding by lazy { FragmentBonusGameBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<BonusGameViewModel>()
    private val listOfGameItemsView by lazy {
        listOf(
            binding.bonus11,
            binding.bonus12,
            binding.bonus13,
            binding.bonus14,
            binding.bonus15,
            binding.bonus21,
            binding.bonus22,
            binding.bonus23,
            binding.bonus24,
            binding.bonus25,
            binding.bonus31,
            binding.bonus32,
            binding.bonus33,
            binding.bonus34,
            binding.bonus35,
            binding.bonus41,
            binding.bonus42,
            binding.bonus43,
            binding.bonus44,
            binding.bonus45,
            binding.bonus51,
            binding.bonus52,
            binding.bonus53,
            binding.bonus54,
            binding.bonus55,
            binding.bonus61,
            binding.bonus62,
            binding.bonus63,
            binding.bonus64,
            binding.bonus65,
        )
    }
    private var xStart = 0F
    private var xEnd = 0F
    private var yStart = 0F
    private var yEnd = 0F
    private var viewSwiped: ImageView? = null
    private val topLeft by lazy {
        Coordinate(
            binding.bonus11.x,
            binding.bonus11.y,
        )
    }
    private val bottomRight by lazy {
        Coordinate(
            binding.bonus65.x,
            binding.bonus65.y,
        )
    }
    val listOfFieldPositions by lazy {
        listOf(
            PositionInField(
                listOfGameItemsView[0].id,
                0,
                0,
                Coordinate(listOfGameItemsView[0].x, listOfGameItemsView[0].y)
            ),
            PositionInField(
                listOfGameItemsView[1].id,
                1,
                0,
                Coordinate(listOfGameItemsView[1].x, listOfGameItemsView[1].y)
            ),
            PositionInField(
                listOfGameItemsView[2].id,
                2,
                0,
                Coordinate(listOfGameItemsView[2].x, listOfGameItemsView[2].y)
            ),
            PositionInField(
                listOfGameItemsView[3].id,
                3,
                0,
                Coordinate(listOfGameItemsView[3].x, listOfGameItemsView[3].y)
            ),
            PositionInField(
                listOfGameItemsView[4].id,
                4,
                0,
                Coordinate(listOfGameItemsView[4].x, listOfGameItemsView[4].y)
            ),
            PositionInField(
                listOfGameItemsView[5].id,
                0,
                1,
                Coordinate(listOfGameItemsView[5].x, listOfGameItemsView[5].y)
            ),
            PositionInField(
                listOfGameItemsView[6].id,
                1,
                1,
                Coordinate(listOfGameItemsView[6].x, listOfGameItemsView[6].y)
            ),
            PositionInField(
                listOfGameItemsView[7].id,
                2,
                1,
                Coordinate(listOfGameItemsView[7].x, listOfGameItemsView[7].y)
            ),
            PositionInField(
                listOfGameItemsView[8].id,
                3,
                1,
                Coordinate(listOfGameItemsView[8].x, listOfGameItemsView[8].y)
            ),
            PositionInField(
                listOfGameItemsView[9].id,
                4,
                1,
                Coordinate(listOfGameItemsView[9].x, listOfGameItemsView[9].y)
            ),
            PositionInField(
                listOfGameItemsView[10].id,
                0,
                2,
                Coordinate(listOfGameItemsView[10].x, listOfGameItemsView[10].y)
            ),
            PositionInField(
                listOfGameItemsView[11].id,
                1,
                2,
                Coordinate(listOfGameItemsView[11].x, listOfGameItemsView[11].y)
            ),
            PositionInField(
                listOfGameItemsView[12].id,
                2,
                2,
                Coordinate(listOfGameItemsView[12].x, listOfGameItemsView[12].y)
            ),
            PositionInField(
                listOfGameItemsView[13].id,
                3,
                2,
                Coordinate(listOfGameItemsView[13].x, listOfGameItemsView[13].y)
            ),
            PositionInField(
                listOfGameItemsView[14].id,
                4,
                2,
                Coordinate(listOfGameItemsView[14].x, listOfGameItemsView[14].y)
            ),
            PositionInField(
                listOfGameItemsView[15].id,
                0,
                3,
                Coordinate(listOfGameItemsView[15].x, listOfGameItemsView[15].y)
            ),
            PositionInField(
                listOfGameItemsView[16].id,
                1,
                3,
                Coordinate(listOfGameItemsView[16].x, listOfGameItemsView[16].y)
            ),
            PositionInField(
                listOfGameItemsView[17].id,
                2,
                3,
                Coordinate(listOfGameItemsView[17].x, listOfGameItemsView[17].y)
            ),
            PositionInField(
                listOfGameItemsView[18].id,
                3,
                3,
                Coordinate(listOfGameItemsView[18].x, listOfGameItemsView[18].y)
            ),
            PositionInField(
                listOfGameItemsView[19].id,
                4,
                3,
                Coordinate(listOfGameItemsView[19].x, listOfGameItemsView[19].y)
            ),
            PositionInField(
                listOfGameItemsView[20].id,
                0,
                4,
                Coordinate(listOfGameItemsView[20].x, listOfGameItemsView[20].y)
            ),
            PositionInField(
                listOfGameItemsView[21].id,
                1,
                4,
                Coordinate(listOfGameItemsView[21].x, listOfGameItemsView[21].y)
            ),
            PositionInField(
                listOfGameItemsView[22].id,
                2,
                4,
                Coordinate(listOfGameItemsView[22].x, listOfGameItemsView[22].y)
            ),
            PositionInField(
                listOfGameItemsView[23].id,
                3,
                4,
                Coordinate(listOfGameItemsView[23].x, listOfGameItemsView[23].y)
            ),
            PositionInField(
                listOfGameItemsView[24].id,
                4,
                4,
                Coordinate(listOfGameItemsView[24].x, listOfGameItemsView[24].y)
            ),
            PositionInField(
                listOfGameItemsView[25].id,
                0,
                5,
                Coordinate(listOfGameItemsView[25].x, listOfGameItemsView[25].y)
            ),
            PositionInField(
                listOfGameItemsView[26].id,
                1,
                5,
                Coordinate(listOfGameItemsView[26].x, listOfGameItemsView[26].y)
            ),
            PositionInField(
                listOfGameItemsView[27].id,
                2,
                5,
                Coordinate(listOfGameItemsView[27].x, listOfGameItemsView[27].y)
            ),
            PositionInField(
                listOfGameItemsView[28].id,
                3,
                5,
                Coordinate(listOfGameItemsView[28].x, listOfGameItemsView[28].y)
            ),
            PositionInField(
                listOfGameItemsView[29].id,
                4,
                5,
                Coordinate(listOfGameItemsView[29].x, listOfGameItemsView[29].y)
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.baseScore = it.getInt(BASE_SCORE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.performClick()
        if (v in listOfGameItemsView) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    xStart = event.x
                    yStart = event.y
                    viewSwiped = v as ImageView
                }

                MotionEvent.ACTION_MOVE -> {
                    xEnd = event.x
                    yEnd = event.y
                }

                MotionEvent.ACTION_UP -> {
                    val xDiff = xEnd - xStart
                    val yDiff = yEnd - yStart
                    val xMod = if (xDiff < 0) xDiff * (-1) else xDiff
                    val yMod = if (yDiff < 0) yDiff * (-1) else yDiff
                    val moveDirection = if (xMod > yMod) {
                        if (xDiff < 0) MoveDirections.LEFT
                        else MoveDirections.RIGHT
                    } else {
                        if (yDiff < 0) MoveDirections.TOP
                        else MoveDirections.DAWN
                    }
                    xStart = 0F
                    xEnd = 0F
                    yStart = 0F
                    yEnd = 0F
                    viewSwiped?.let { moveCard(moveDirection, viewSwiped as ImageView, 500) }
                    viewSwiped = null
                }
            }
        }
        return true
    }

    private fun moveCard(moveDirection: MoveDirections, cardView: ImageView, baseDuration: Long) {
        val cardInField =
            listOfFieldPositions.filter { it.coordinate == Coordinate(cardView.x, cardView.y) }[0]
        val cardCoordinate = Coordinate(cardView.x, cardView.y)
        val secondCardInField = when (moveDirection) {
            MoveDirections.LEFT -> {
                if (cardCoordinate.x > topLeft.x) {
                    listOfFieldPositions.filter { it.column == (cardInField.column - 1) && it.row == cardInField.row }[0]
                } else null
            }

            MoveDirections.TOP -> {
                if (cardCoordinate.y > topLeft.y) {
                    listOfFieldPositions.filter { it.row == (cardInField.row - 1) && it.column == cardInField.column }[0]
                } else null
            }

            MoveDirections.RIGHT -> {
                if (cardCoordinate.x < bottomRight.x) {
                    listOfFieldPositions.filter { it.column == (cardInField.column + 1) && it.row == cardInField.row }[0]
                } else null
            }

            MoveDirections.DAWN -> {
                if (cardCoordinate.y < bottomRight.y) {
                    listOfFieldPositions.filter { it.row == (cardInField.row + 1) && it.column == cardInField.column }[0]
                } else null
            }
        }

        val firstCard = viewModel.field?.get(cardInField.row)?.get(cardInField.column)?.view
        val secondCard = secondCardInField?.let { viewModel.field?.get(it.row)?.get(it.column)?.view }
        val firstField = viewModel.field?.get(cardInField.row)?.get(cardInField.column)
        val secondField = secondCardInField?.let { viewModel.field?.get(it.row)?.get(it.column)}
        if (secondCard != null && firstCard != null) {

            firstCard.animate()?.apply {
                duration = baseDuration
                x(secondCard.x)
                y(secondCard.y)
            }
            secondCard.animate().apply {
                duration = baseDuration
                x(firstCard.x)
                y(firstCard.y)
            }
            viewModel.moveCardInField(cardInField, secondCardInField, firstField!!, secondField!! )
            viewModel.fullCheckRes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBonusGame()
//        setupCandysClickListener()
        setupOnTouchListener()
        observeKoeficient()
        observeTimer()
        shouldFinishGame()
        observeGameState()
        setupBtnClickListeners()
        viewModel.startCountDawnTimer()
        observeField()
    }

    private fun animHide(img:ImageView){
        img.animate().apply {
            duration = ANIM_DURATION
            scaleX(0.1f)
            scaleY(0.1f)
            withEndAction {
                img.visibility = View.INVISIBLE
                img.setImageDrawable(null)
            }
        }
    }

    private fun observeField(){
        viewModel.fieldLD.observe(viewLifecycleOwner){ it ->
            it.forEach {
                it.forEach {
                    if (it.resId ==null){
                        animHide(it.view)
                    }
                }
            }
        }
    }

    private fun setupBtnClickListeners() {
        setupBtnHome()
        setupBtnPause()
        setupBtnHelp()
    }

    private fun setupBtnHome() {
        binding.btnHome.setOnClickListener {
            parentFragmentManager.launchFragment(StartFragment())
        }
    }

    private fun setupBtnPause() {
        binding.btnPause.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.postValue(true)
            parentFragmentManager.beginTransaction().apply {
                add(R.id.mainContainer, PauseFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeGameState() {
        isGameOnPause.isGameOnPauseLD.observe(viewLifecycleOwner) { isGameOnPause ->
            if (isGameOnPause == true) {
                viewModel.pauseCountDawnTimer()
            } else if (isGameOnPause == false) {
                viewModel.startCountDawnTimer()
            }
        }
    }

    private fun setupBtnHelp() {
        binding.btnHelp.setOnClickListener {
            isGameOnPause.isGameOnPauseLD.value = true
            parentFragmentManager.beginTransaction().apply {
                add(R.id.mainContainer, HelpFragment.newInstance(false))
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun shouldFinishGame() {
        viewModel.shouldFinishGame.observe(viewLifecycleOwner) { score ->
            if (score > 0) {
                parentFragmentManager.launchFragment(LvlCompleteFragment.newInstance(score))
            } else {
                parentFragmentManager.launchFragment(LvlFailedFragment())
            }
        }
    }

    private fun observeTimer() {
        viewModel.timerLD.observe(viewLifecycleOwner) {
            binding.pbTimer.setProgress(it, true)
        }
    }

    private fun observeKoeficient() {
        viewModel.multiplyLD.observe(viewLifecycleOwner) {
            val first = it/10
            val second = it - first*10
            binding.tvKoeficient.text = "${first + 1}.$second"
        }
    }

    private fun initBonusGame() {
        viewModel.setupSrcs(listOfGameItemsView)
    }

    private fun setupOnTouchListener() {
        with(binding) {
            bonus11.setOnTouchListener(this@BonusGameFragment)
            bonus12.setOnTouchListener(this@BonusGameFragment)
            bonus13.setOnTouchListener(this@BonusGameFragment)
            bonus14.setOnTouchListener(this@BonusGameFragment)
            bonus15.setOnTouchListener(this@BonusGameFragment)
            bonus21.setOnTouchListener(this@BonusGameFragment)
            bonus22.setOnTouchListener(this@BonusGameFragment)
            bonus23.setOnTouchListener(this@BonusGameFragment)
            bonus24.setOnTouchListener(this@BonusGameFragment)
            bonus25.setOnTouchListener(this@BonusGameFragment)
            bonus31.setOnTouchListener(this@BonusGameFragment)
            bonus32.setOnTouchListener(this@BonusGameFragment)
            bonus33.setOnTouchListener(this@BonusGameFragment)
            bonus34.setOnTouchListener(this@BonusGameFragment)
            bonus35.setOnTouchListener(this@BonusGameFragment)
            bonus41.setOnTouchListener(this@BonusGameFragment)
            bonus42.setOnTouchListener(this@BonusGameFragment)
            bonus43.setOnTouchListener(this@BonusGameFragment)
            bonus44.setOnTouchListener(this@BonusGameFragment)
            bonus45.setOnTouchListener(this@BonusGameFragment)
            bonus51.setOnTouchListener(this@BonusGameFragment)
            bonus52.setOnTouchListener(this@BonusGameFragment)
            bonus53.setOnTouchListener(this@BonusGameFragment)
            bonus54.setOnTouchListener(this@BonusGameFragment)
            bonus55.setOnTouchListener(this@BonusGameFragment)
            bonus61.setOnTouchListener(this@BonusGameFragment)
            bonus62.setOnTouchListener(this@BonusGameFragment)
            bonus63.setOnTouchListener(this@BonusGameFragment)
            bonus64.setOnTouchListener(this@BonusGameFragment)
            bonus65.setOnTouchListener(this@BonusGameFragment)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(baseScore: Int) =
            BonusGameFragment().apply {
                arguments = Bundle().apply {
                    putInt(BASE_SCORE, baseScore)
                }
            }
    }
}