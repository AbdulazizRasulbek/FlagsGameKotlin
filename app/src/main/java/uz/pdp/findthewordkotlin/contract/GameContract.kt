package uz.pdp.findthewordkotlin.contract

import android.content.Context
import uz.pdp.findthewordkotlin.model.QuestionData

interface GameContract {
    
    interface Model{
        fun getQuestionData(): List<QuestionData>
    }
    interface Presenter{
        fun answerClick(pos :Int)
        fun variantClick(pos :Int)
        fun showLetterClick()
        fun deleteLetterClick()
        fun loadData()

    }
    interface View{
        fun getAnswerTag(pos: Int):Any?
        fun setAnswerTag(pos: Int,tag:Any?)
        fun getAnswerText(pos: Int):CharSequence
        fun getVariantText(pos: Int):CharSequence
        fun setAnswerText(pos: Int,text:CharSequence)
        fun showVariant(pos: Int)
        fun showAnswer(pos: Int)
        fun hideVariant(pos: Int)
        fun loadQuestionData(imageId:Int,answerLength:Int,variants:String)
        fun isVisibleVariant(pos: Int):Boolean
        fun getAnswerFullText():String
        fun showToast(text: String)
        fun getContext():Context
        fun toMain()
        fun setScore(coins:Int)
        fun setLevel(level:Int)
        fun setBackgroundButton(pos: Int,drawableId: Int,color: String)

    }
}