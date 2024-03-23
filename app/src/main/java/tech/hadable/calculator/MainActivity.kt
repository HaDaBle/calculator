package tech.hadable.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import tech.hadable.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firstNumberText=StringBuilder("")
    private val secondNumberText=StringBuilder("")
    private val operatorText=StringBuilder("")
    private val decimalFormat=DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
    fun numberClicked(view:View){
        val numberString=(view as? Button)?.text.toString() ?:""
        val numberText=if(operatorText.isEmpty()) firstNumberText else secondNumberText

        numberText.append(numberString)
        updateEquationTextView()
    }

    fun clearClicked(view:View){
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()

        updateEquationTextView()
        binding.resultTextView.text=""
    }

    fun equalClicked(view:View){
        if(firstNumberText.isEmpty()||secondNumberText.isEmpty()||operatorText.isEmpty()){
            Toast.makeText(this,"수식이 올바르지 않습니다",Toast.LENGTH_LONG).show()
            return
        }
        val firstNumber=firstNumberText.toString().toBigDecimal()
        val secondNumber=secondNumberText.toString().toBigDecimal()
        val result=when(operatorText.toString()){
            "+"->decimalFormat.format(firstNumber + secondNumber)
            "-"->decimalFormat.format(firstNumber - secondNumber)
            else -> "잘못된 수식 입니다."
        }
        binding.resultTextView.text=result
    }

    fun operatorClicked(view: View){
        val operatorString=(view as? Button)?.text.toString() ?:""

        if(firstNumberText.isEmpty()){
            Toast.makeText(this,"먼저 숙자를 입력하세요.",Toast.LENGTH_LONG).show()
            return
        }
        if(secondNumberText.isNotEmpty()){
            Toast.makeText(this,"1개의 연산자에 대해서만 연산이 가능합니다.",Toast.LENGTH_LONG).show()
            return
        }
        operatorText.append(operatorString)
        updateEquationTextView()

    }
    private fun updateEquationTextView(){
        val firstFormattedNumber=if(firstNumberText.isNotEmpty())decimalFormat.format(firstNumberText.toString().toBigDecimal())else ""
        val secondFormattedNumber=if(secondNumberText.isNotEmpty())decimalFormat.format(secondNumberText.toString().toBigDecimal())else ""

        binding.equationTextView.text="$firstFormattedNumber $operatorText $secondFormattedNumber"
    }
}