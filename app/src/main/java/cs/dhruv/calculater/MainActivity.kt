package cs.dhruv.calculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null
    private var LastDigit : Boolean = false
    private var LastDot : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view : View){
        tvInput?.append((view as Button).text)
        LastDigit = true
        LastDot = false
    }
    fun onClr(view : View) {
        tvInput?.text = ""
    }
    fun onDecimalPoint(view: View){
        if(LastDigit && !LastDot) {
            tvInput?.append(".")
            LastDigit = false
            LastDot = true
        }
    }
    fun onOperator(view: View){
        tvInput?.text?.let {
            if (LastDigit && !onOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                LastDigit = false
                LastDot = false
            }
        }

    }

    private fun onOperatorAdded (value : String ) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+")
                    || value.contains("*")
                    || value.contains("/")
                    || value.contains("-")
        }
    }

    fun onEqual (view : View){
        var tvValue = tvInput?.text.toString()
        var prifix = ""
        if (LastDigit){
            try {
                if (tvValue.startsWith("-")){
                    prifix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prifix.isNotEmpty()){
                        one = prifix + one
                    }

                    tvInput?.text = removeDotZero((one.toDouble() - two.toDouble()).toString())
                }else  if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prifix.isNotEmpty()){
                        one = prifix + one
                    }

                    tvInput?.text = removeDotZero((one.toDouble() + two.toDouble()).toString())
                } else  if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prifix.isNotEmpty()){
                        one = prifix + one
                    }

                    tvInput?.text = removeDotZero((one.toDouble() * two.toDouble()).toString())
                }else  if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prifix.isNotEmpty()){
                        one = prifix + one
                    }

                    tvInput?.text = removeDotZero((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }

        }

    }
    fun removeDotZero(result:String ) : String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }

}