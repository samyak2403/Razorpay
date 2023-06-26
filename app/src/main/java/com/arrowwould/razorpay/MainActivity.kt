package com.arrowwould.razorpay

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var editAmount: EditText

    lateinit var btnPay: Button

    lateinit var paymentStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editAmount = findViewById(R.id.edit_amount)

        btnPay = findViewById(R.id.btn_pay)

        paymentStatus = findViewById(R.id.payment_status)

        btnPay.setOnClickListener {

            paymentProcess(editAmount.text.toString().trim().toInt())

        }

        Checkout.preload(this@MainActivity)


    }

    private fun paymentProcess(amount: Int) {

        val checkout = Checkout()

        checkout.setKeyID("Your RazorPay Key")

        try {

            val options = JSONObject()

            options.put("name", "Arrow Would")

            options.put("description", "Make a payment")

            options.put(
                "image",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz_3pUUaYQz8iQtUA6VswvCxmBcOEdJqRjSXeMr43j9GnhU7L0HV7fFLLZ9kWTwwZ2dG4&usqp=CAU"
            )

            options.put("theme.color","#528FF0")

            options.put("currency","INR")

            options.put("amount",amount*100)

            val retryObj=JSONObject()

            retryObj.put("enabled",true)

            retryObj.put("max_count",4)

            options.put("retry",retryObj)


            checkout.open(this@MainActivity,options)



        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPaymentSuccess(p0: String?) {

        paymentStatus.text=p0

        paymentStatus.setTextColor(Color.GREEN)

    }

    override fun onPaymentError(p0: Int, p1: String?) {

        paymentStatus.text=p1

        paymentStatus.setTextColor(Color.RED)


    }
}