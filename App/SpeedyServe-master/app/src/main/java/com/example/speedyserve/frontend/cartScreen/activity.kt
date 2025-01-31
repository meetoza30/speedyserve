import android.app.Activity
import android.content.Context
import com.razorpay.Checkout
import org.json.JSONObject
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.PaymentResultListener


fun startPayment(context: Context, amount: Int) {
    val activity = context as Activity
    val checkout = Checkout()

    // Use Test Key (Get from Razorpay Dashboard)
    checkout.setKeyID("rzp_test_un59kO5L4It2cv")

    try {
        val options = JSONObject()
        options.put("name", "SpeedyServe")
        options.put("description", "Hackathon Payment")
        options.put("currency", "INR")
        options.put("amount", amount * 100) // Convert to paise

        options.put("prefill", JSONObject().apply {
            put("email", "test@example.com")
            put("contact", "9999999999")
        })

        checkout.open(activity, options)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        Toast.makeText(this, "Payment Successful: $razorpayPaymentId", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(code: Int, response: String?) {
        Toast.makeText(this, "Payment Failed: $response", Toast.LENGTH_LONG).show()
    }
}