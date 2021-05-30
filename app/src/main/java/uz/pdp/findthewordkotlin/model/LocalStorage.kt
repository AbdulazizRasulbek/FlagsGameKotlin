package uz.pdp.findthewordkotlin.model

import android.content.Context
import com.securepreferences.SecurePreferences

class LocalStorage(context: Context) {
private val pref= SecurePreferences(context,"abdulaziz","Prefs")

    var level:Int
    get() {
        return pref.getInt("LEVEL",0)
    }
    set(value) = pref.edit().putInt("LEVEL",value).apply()

    var coins:Int
    get() = pref.getInt("COINS",300)
    set(value) = pref.edit().putInt("COINS",value).apply()

    var shownLetters:ArrayList<Int>
    get(){
        val list=ArrayList<Int>(listOf())
        val size = pref.getInt("SHOWNLETTERSIZE", 0)
        for (index in 0 until size){
            val i = pref.getInt("SHOWNLETTER_$index", -1)
            list.add(i)
        }
        return list
    }
    set(value) {
        val e=pref.edit()
        e.putInt("SHOWNLETTERSIZE",value.size)
        value.forEachIndexed { index, i ->
            e.putInt("SHOWNLETTER_$index",i)
        }

    }
    fun clearData(){
        pref.edit().clear().apply()
    }
}
