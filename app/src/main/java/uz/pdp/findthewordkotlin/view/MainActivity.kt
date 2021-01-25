package uz.pdp.findthewordkotlin.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import uz.pdp.findthewordkotlin.contract.GameContract
import uz.pdp.findthewordkotlin.model.LocalStorage
import uz.pdp.findthewordkotlin.presenter.PresenterImpl
import uz.pdp.findthewordkotlin.R

class MainActivity : AppCompatActivity(), GameContract.View {
    private var variants: ArrayList<Button> = ArrayList()
    private var answers: ArrayList<Button> = ArrayList()
    lateinit var presenter: GameContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()

    }

    private fun loadData() {
        val localStorage=LocalStorage(this)
        presenter = PresenterImpl(localStorage,this)
        showLetter.setOnClickListener {
            presenter.showLetterClick()
        }
        deleteLetter.setOnClickListener {
            presenter.deleteLetterClick()
        }

        addButtonsFromViewGroup(variants, layoutVariant1)
        addButtonsFromViewGroup(variants, layoutVariant2)
        addButtonsFromViewGroup(answers, layoutAnswers)
        setOnclickToButtons(answers, "ans")
        setOnclickToButtons(variants, "var")
        presenter.loadData()
    }

    private fun addButtonsFromViewGroup(list: ArrayList<Button>, viewGroup: ViewGroup) {
        for (i in 0..viewGroup.childCount) {
            val button: Button? = viewGroup.getChildAt(i) as? Button
            button?.let { list.add(it) }
            button?.visibility = View.VISIBLE

        }
    }

    private fun setOnclickToButtons(list: ArrayList<Button>, name: String) {
        if (name == "var") {
            list.forEachIndexed { index, button ->
                button.setOnClickListener {
                    presenter.variantClick(index)
                }
            }
        } else if (name == "ans") {
            list.forEachIndexed { index, button ->
                button.setOnClickListener {
                    presenter.answerClick(index)
                }
            }
        }


    }

    override fun getAnswerTag(pos: Int): Any?{
        return answers[pos].tag
    }

    override fun setAnswerTag(pos: Int,tag: Any?) {
        answers[pos].tag=tag
    }

    override
    fun getAnswerText(pos: Int): CharSequence {
        return answers[pos].text
    }

    override fun getVariantText(pos: Int): CharSequence {
        return variants[pos].text
    }


    override fun setAnswerText(pos: Int, text: CharSequence) {
        answers[pos].text = text
    }

    override fun showVariant(pos: Int) {
        variants[pos].visibility = View.VISIBLE
    }

    override fun showAnswer(pos: Int) {
        answers[pos].visibility = View.VISIBLE
    }

    override fun hideVariant(pos: Int) {
        variants[pos].visibility = View.INVISIBLE
    }

    override fun loadQuestionData(imageId: Int, answerLength: Int, variants: String) {
        imageView.setImageResource(imageId)
        for (i in answers.indices) {
            if (i >= answerLength)
                answers[i].visibility = View.GONE
        }
        for (i in variants.indices) {
            this.variants[i].text = variants[i].toString()
        }

    }

    override fun isVisibleVariant(pos: Int): Boolean {
        return variants[pos].visibility == View.VISIBLE
    }

    override fun getAnswerFullText(): String {
        val s = StringBuilder()
        answers.forEach {
            it.tag
            s.append(it.text)
        }
        return s.toString()
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun getContext(): Context {
        return this
    }

    override fun toMain() {
        startActivity(Intent(this, MenuActivity::class.java))
    }

    override fun setScore(coins: Int) {
        scoreTv.text = coins.toString()
    }

    override fun setLevel(level: Int) {
        levelTV.text = level.toString()
    }

    override fun setBackgroundButton(pos: Int, drawableId: Int, color: String) {
        answers[pos].setBackgroundResource(drawableId)
        answers[pos].setTextColor(Color.parseColor(color))

    }
}
