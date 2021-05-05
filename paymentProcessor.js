$(window).load(function () {
  $("#loader").fadeOut(1000);
});

// Example starter JavaScript for disabling form submissions if there are invalid fields
(() => {
  'use strict';

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  const forms = document.querySelectorAll('.needs-validation');

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms).forEach((form) => {

    form.addEventListener('submit', (event) => {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        console.log("check validity");
        form.classList.add('was-validated');
      } else {
        console.log("check validity done");
        showLoader(3000);
        submitForm(event);
      }

    }, false);

  });
})();

function showLoader(time) {
  $("#loader").show();
  $("#loader").fadeOut(time);
}

function hideLoader() {
  $("#loader").hide();
}

function submitForm(e) {
  console.log("submitting form");
  var $regform = $('form#registration-form'),
    //test url - don not delete
    //regurl = 'https://script.google.com/macros/s/AKfycbx6IiND5GRpqJsRDKU8TOU-vVO5g94jHHb-73JCD52Wlf_o6691/exec'

    //live
    regurl = 'https://script.google.com/macros/s/AKfycbzjeaDa9eICyEwiz4i6nElQEiyr75WnhpFgA7HtxSfOMiv3LO8t/exec'

  // code to save to google sheet
  e.preventDefault(); // prevent form calling deafult action method
  var jqxhr = $.ajax({
    url: regurl,
    method: "GET",
    dataType: "json",
    data: $regform.serialize(),
    success: function (data, text) {
      // asynchronous, after thr response from google returns
      console.log("form submittion successful");
      processPayment();
    },
    error: function (request, status, error) {
      $("#failureModal").modal('show');
      console.log("form submittion failed");
      console.log(request.responseText);
    }
  }).success(
    // synchronous handling
  );
}
function processPayment() {

  console.log("Processing payment");
  var progVal = $('select[name="program"]').val();
  var progArr = progVal.split("_");
  var amount = progArr[progArr.length - 1];
  console.log("amount = " + amount);
  console.log($('input[name="firstName"]').val() + " "
    + $('input[name="lastName"]').val() +
    " email " + $('input[name="email"]').val() +
    " contact " + $('input[name="phone"]').val()) +
    " address " + $('input[name="address"]').val()
  var options = {
    "key": "rzp_test_kLmsHnmQfdgIhF",//"rzp_live_l91cg0z4PNOkVp", 
    "amount": amount * 100, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    "currency": "INR",
    "name": "The Classical Yoga",
    "description": "Hatha Yoga In Its Purest Form",
    "handler": function (response) {
      //success handler
      console.log("Payment Success!!");
      console.log(response.razorpay_payment_id);
      console.log(response.razorpay_order_id);
      console.log(response.razorpay_signature);

      // show payment sucess msg pop up
      $("#staticBackdropSuccess").modal('show');

    },
    "prefill": {
      "name": $('input[name="firstName"]').val() + " " + $('input[name="lastName"]').val(),
      "email": $('input[name="email"]').val(),
      "contact": $('input[name="phone"]').val()
    },
    "notes": {
      "address": $('input[name="address"]').val()
    }
  };
  console.log("open rzpy")
  var rzp1 = new Razorpay(options);
  rzp1.on('payment.failed', function (response) {
    console.log("Error payment failed!!");
    console.log(response.error.code);
    console.log(response.error.description);
    console.log(response.error.source);
    console.log(response.error.step);
    console.log(response.error.reason);
    console.log(response.error.metadata.order_id);
    console.log(response.error.metadata.payment_id);
    // show payment failure msg pop up not needed, razor pay has its built in popup
  });
  rzp1.open();
  //e.preventDefault();
  return false;

}

function onSuccessOKClick() {

  //reset form
  // $(':input','#registration-form')
  // .not(':button, :submit, :reset, :hidden')
  // .val('')
  // .prop('checked', false)
  // .prop('selected', false);

  // reload the page 
  window.location.reload();
}


// var $form = $('form#test-form'),
//   url = 'https://script.google.com/macros/s/AKfycbyJ1tw_nDgwE92ZtQq59eW9KAyeK79qrWBE2RWWTQH25UHmS4s/exec'

// $('#submit-test-form').on('click', function (e) {
//   console.log("test form url - " +url);

//   e.preventDefault();
//   var jqxhr = $.ajax({
//     url: url,
//     method: "GET",
//     dataType: "json",
//     data: $form.serialize()
//   }).success(
//     // do something
//   );
// })