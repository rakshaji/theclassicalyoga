

function processPayment() {
alert("test - " );

    var options = {
        "key": "rzp_test_xmtYVGhPrKMFT6", // Enter the Key ID generated from the Dashboard
        "amount": "100", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
        "currency": "INR",
        "name": "The Classical Yoga",
        "description": "Hatha Yoga In Its Purest Form",
        "image": "https://d33wubrfki0l68.cloudfront.net/9507a6ff391171e22b0881fbb08b3606b7179446/38167/images/logo.png",
        //"order_id": "order_9A33XWu170gUtm", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
        "handler": function (response){
            alert("Success!!");
            //this.order_id = response.razorpay_order_id;
            alert(response.razorpay_payment_id);
            alert(response.razorpay_order_id);
            alert(response.razorpay_signature);
        },
        "prefill": {
            "name": "Gaurav Kumar",
            "email": "gaurav.kumar@example.com",
            "contact": "9999999999"
        },
        "notes": {
            "address": "Razorpay Corporate Office"
        },
        "theme": {
            "color": "#3399cc"
        }
    };
    alert("open razorpay")
    var rzp1 = new Razorpay(options);
    rzp1.on('payment.failed', function (response){
            alert("Error!!");
            alert(response.error.code);
            alert(response.error.description);
            alert(response.error.source);
            alert(response.error.step);
            alert(response.error.reason);
            alert(response.error.metadata.order_id);
            alert(response.error.metadata.payment_id);    
    });
    rzp1.open();
    e.preventDefault();
    return false;
    // document.getElementById('rzp-button1').onclick = function(e){
    //     rzp1.open();
    //     e.preventDefault();
    // }
}