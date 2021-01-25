package uz.pdp.findthewordkotlin.Model

import uz.pdp.findthewordkotlin.Contract.GameContract
import uz.pdp.findthewordkotlin.R
import kotlin.collections.ArrayList

class Repository() : GameContract.Model {
    private val data: ArrayList<QuestionData> = ArrayList()

    init {

        data.add(
            QuestionData(
                R.drawable.austria,
                "Austria",
                generateVariants("Austria")
            )
        )
        data.add(
            QuestionData(
                R.drawable.argentina,
                "Argentina",
                generateVariants("Argentina")
            )
        )
        data.add(
            QuestionData(
                R.drawable.bahrain,
                "Bahrain",
                generateVariants("Bahrain")
            )
        )
        data.add(
            QuestionData(
                R.drawable.belgium,
                "Belgium",
                generateVariants("Belgium")
            )
        )
        data.add(
            QuestionData(
                R.drawable.canada,
                "Canada",
                generateVariants("Canada")
            )
        )
        data.add(
            QuestionData(
                R.drawable.hungary,
                "Hungary",
                generateVariants("Hungary")
            )
        )
        data.add(
            QuestionData(
                R.drawable.ic_france_flag,
                "France",
                generateVariants("France")
            )
        )
        data.add(
            QuestionData(
                R.drawable.ic_germany_flag,
                "Germany",
                generateVariants("Germany")
            )
        )
        data.add(
            QuestionData(
                R.drawable.indonesia,
                "Indonesia",
                generateVariants("Indonesia")
            )
        )
        data.add(
            QuestionData(
                R.drawable.italy,
                "Italy",
                generateVariants("Italy".toLowerCase())
            )
        )
        data.add(
            QuestionData(
                R.drawable.mexico,
                "Mexico",
                generateVariants("Mexico")
            )
        )
        data.add(
            QuestionData(
                R.drawable.monaco,
                "Monaco",
                generateVariants("Monaco")
            )
        )
        data.add(
            QuestionData(
                R.drawable.new_zealand,
                "NewZealand",
                generateVariants("NewZealand")
            )
        )
        data.add(
            QuestionData(
                R.drawable.peru,
                "Peru",
                generateVariants("Peru")
            )
        )
        data.add(
            QuestionData(
                R.drawable.poland,
                "Poland",
                generateVariants("Poland")
            )
        )
        data.add(
            QuestionData(
                R.drawable.qatar,
                "Qatar",
                generateVariants("Qatar")
            )
        )
        data.add(
            QuestionData(
                R.drawable.romania,
                "Romania",
                generateVariants("Romania")
            )
        )
        data.add(
            QuestionData(
                R.drawable.spain,
                "Spain",
                generateVariants("Spain")
            )
        )
        data.add(
            QuestionData(
                R.drawable.uzbekistan,
                "Uzbekistan",
                generateVariants("Uzbekistan")
            )
        )
        data.add(
            QuestionData(
                R.drawable.vietnam,
                "Vietnam",
                generateVariants("Vietnam")
            )
        )
        data.add(
            QuestionData(
                R.drawable.yemen,
                "Yemen",
                generateVariants("Yemen")
            )
        )
    }

    private fun generateVariants(answer: String): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val length = answer.length
        val l = 16 - length
        val list = ArrayList<String>()
        for (element in alphabet) {
            list.add(element.toString())
        }
        list.shuffle()
        val strings = ArrayList<String>()
        for (element in answer) {
            strings.add(element.toString())
        }
        for (i in 0 until l) {
            strings.add(list[i])
        }
        strings.shuffle()
        val s = StringBuilder()
        for (i in 0 until strings.size) {
            s.append(strings[i])
        }
        return s.toString()
    }

    override fun getQuestionData(): List<QuestionData> {
//        data.shuffle()
        return data
    }
}