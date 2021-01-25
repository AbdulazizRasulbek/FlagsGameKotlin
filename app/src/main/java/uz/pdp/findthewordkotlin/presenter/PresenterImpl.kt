package uz.pdp.findthewordkotlin.presenter

import android.os.CountDownTimer
import cn.pedant.SweetAlert.SweetAlertDialog
import uz.pdp.findthewordkotlin.contract.GameContract
import uz.pdp.findthewordkotlin.model.LocalStorage
import uz.pdp.findthewordkotlin.model.QuestionData
import uz.pdp.findthewordkotlin.model.Repository
import uz.pdp.findthewordkotlin.R
import kotlin.collections.ArrayList

class PresenterImpl(
    val localStorage: LocalStorage,
    val view: GameContract.View
) : GameContract.Presenter {

    private val model = Repository()
    private var position = localStorage.level
    private val list: List<QuestionData> = model.getQuestionData()
    private var answersSize = 10
    private var variantSize = 16
    private var coins = localStorage.coins
    private var step = 0
    private val randomAnswers = ArrayList<Int>()
    private val randomVariants = ArrayList<Int>()

    override fun answerClick(pos: Int) {
        val (_, _, variants) = getCurrentQuestion()
        val answerText = view.getAnswerText(pos)
        if (answerText.isNotEmpty() && view.getAnswerTag(pos) == null) {

            for (i in variants.indices) {
                if (!view.isVisibleVariant(i) && answerText == view.getVariantText(i)) {
                    view.showVariant(i)
                    view.setAnswerText(pos, "")
                    step--
                    break
                }
            }

        }
    }

    private fun isAnswerTrue(): Boolean {
        return view.getAnswerFullText().equals(getCurrentQuestion().answer, ignoreCase = true)
    }

    override fun variantClick(pos: Int) {
        val currentQuestion = getCurrentQuestion()

        for (i in currentQuestion.answer.indices)
            if (view.getAnswerText(i).isEmpty()) {
                view.setAnswerText(i, view.getVariantText(pos))
                view.hideVariant(pos)
                step++
                if (step == getCurrentQuestion().answer.length)
                    checkResult()
                return
            }


    }

    val shownLetters=ArrayList<Int>()
    override fun showLetterClick() {

        val answer = getCurrentQuestion().answer
        val dialog = SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Show one true letter for 50 coins?")
            .setConfirmText("Yes")
            .setCancelButton("Cancel", SweetAlertDialog::dismissWithAnimation)
            .setConfirmClickListener {

                it.dismissWithAnimation()

                if (coins >= 50) {
                    randomAnswers.shuffle()
                    for (i in randomAnswers) {

                        if (view.getAnswerText(i).isEmpty()) {
                            view.setAnswerText(i, answer[i] + "")
                            view.setAnswerTag(i, "tag")
                            shownLetters.add(i)
                            view.setBackgroundButton(i, R.drawable.bg_true, "#ffffff")

                            coins -= 50
                            view.setScore(coins)
                            step++

                            if (step == getCurrentQuestion().answer.length)
                                checkResult()
                            for (j in randomVariants) {
                                if (view.getVariantText(j).toString() == view.getAnswerText(i).toString()) {
                                    view.hideVariant(j)
                                    localStorage.shownLetters = shownLetters
                                    return@setConfirmClickListener
                                }
                            }

                        }
                    }

                } else view.showToast("No enough money")
            }
        dialog.setCancelable(false)
        dialog.show()

    }

    var limit = 0
    override fun deleteLetterClick() {
        val question = getCurrentQuestion()

        limit++
        val dialog = SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Delete one wrong letter for 25 coins?")
            .setConfirmText("Yes")
            .setCancelButton("Cancel", SweetAlertDialog::dismissWithAnimation)
            .setConfirmClickListener {
                it.dismissWithAnimation()
                if (coins >= 25) {
                    for (i in randomVariants.indices) {
                        val pos = randomVariants[i]
                        val text = view.getVariantText(pos)
                        if (limit <= 5) {
                            if (view.isVisibleVariant(pos) && !question.answer.contains(
                                    text,
                                    true
                                )
                            ) {
                                view.hideVariant(pos)
                                coins -= 25
                                view.setScore(coins)
                                return@setConfirmClickListener
                            }
                        } else {
                            view.showToast("You have reached the limit")
                        }

                    }
                }

            }
        dialog.show()
    }


    private fun checkResult() {
        if (isAnswerTrue()) {
            coins += 20
            object : CountDownTimer(200, 200) {
                override fun onFinish() {
                    position++
                    val dialog =
                        SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    if (list.size - 1 < position) {
                        dialog.titleText = "\uD83D\uDC4F You win the game!! \uD83D\uDC4F"
                        dialog.setConfirmText("Main Menu")
                            .setConfirmClickListener {
                                view.toMain()
                                localStorage.coins = 300
                                localStorage.level = 0
                            }
                            .setCancelable(false)
                        dialog.show()
                    } else {
                        if (position <= list.size - 1) {
                            dialog.titleText = "\uD83D\uDC4F Correct! \uD83D\uDC4F"
                            dialog.contentText = list[position - 1].answer
                            dialog.confirmText = "Continue"
                            dialog.setConfirmClickListener {
                                it.dismiss()
                                localStorage.level = position
                                localStorage.coins = coins
                                loadData()

                            }.show()
                            dialog.setCancelable(false)

                        }
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                }
            }.start()
            return
        } else {
            if (coins >= 5) {
                coins -= 5
                view.setScore(coins)
                view.showToast("❌ Incorrect! You lost 5 coins")
            } else {
                view.showToast("❌ Incorrect! Try again \uD83D\uDD04")
            }

        }

    }

    override fun loadData() {
        val currentQuestion = getCurrentQuestion()
        getCurrentQuestion().answer.indices.forEach { i ->
            randomAnswers.add(i)
        }
        getCurrentQuestion().variants.indices.forEach { i ->
            randomVariants.add(i)
        }

        step = 0
        view.setLevel(position + 1)
        view.setScore(coins)
        clearAnswersButton()
        showVariants()

        view.loadQuestionData(
            currentQuestion.image,
            currentQuestion.answer.length,
            currentQuestion.variants
        )
        val letters = localStorage.shownLetters
        if (letters.size > 0) {
            for (i in letters) {
                if (i != -1) {
                    view.setAnswerText(i, currentQuestion.answer[i].toString())
                    view.setAnswerTag(i,"tag")
                }
            }
        }
    }

    private fun clearAnswersButton() {
        for (i in 0 until answersSize) {
            view.showAnswer(i)
            view.setAnswerText(i, "")
            view.setAnswerTag(i, null)
            view.setBackgroundButton(i, R.drawable.bg_answer, "#000000")
        }
    }

    private fun showVariants() {
        for (i in 0 until variantSize) {
            view.showVariant(i)
        }
    }

    private fun getCurrentQuestion(): QuestionData {
        return list[position]
    }
}