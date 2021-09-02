var progTimingsMap = new Object(); // or var map = {};

$(window).load(function () {
  $("#loader").fadeOut(1000);
  // get all the timings in and id-timings map
  
  $("#program option").each(function()
  {
    console.log($(this).text());

    var optionText = $(this).text();
    var key = $(this).val();
    if(key == "") {
      return;
    }

    // get all program details in an array
    const progDetailArr = optionText.split(" | ");
    let id = progDetailArr[0];
    let date = progDetailArr[1];
    let timeWithExceptions = progDetailArr[2];
    let amount = progDetailArr[3];

    // get timings removing the exceptions part
    var timeArr = clearExceptions(timeWithExceptions);
    
    // replace program details by removing timings
    $(this).text(id + " | " + date + " | " + amount);
    
    // check if we have store time already
    if(get(key) == null) {
      progTimingsMap[key] = timeArr;
    } 

    console.log(get(key));
    console.log($(this).text());
  });
  
  $('#program').on('change', function() {
    // remove all options except first one
    $("#time option").each(function() {
      if($(this).val() != "") {
        $(this).remove();
      }
    });

    // return if first option selected
    let key = this.value;
    console.log( key );
    if(key == "") {
      return;
    }

    // add timings to the drop down
    let matchedTimeArr = get(key);
    console.log( matchedTimeArr );
    if(matchedTimeArr != null && matchedTimeArr.length > 0) { 
      for (let i = 0; i < matchedTimeArr.length; i++) {
        let time = matchedTimeArr[i];
        console.log(time);
        $('#time').append(new Option(time, time));
      }
    }
  });
});

function clearExceptions(timeWithExceptions){
    var timeWithoutExceptions;
    var timeArr = [];
    if(timeWithExceptions.includes("[")) {
      // remove exceptions part
      timeWithoutExceptions = timeWithExceptions.split("[")[0];
    } else {
      timeWithoutExceptions = timeWithExceptions;
    }

    console.log(timeWithoutExceptions);
    // get all the timings in an array
    if(timeWithoutExceptions.includes(" या ")) {
      timeArr = timeWithoutExceptions.split(" या ");
    } else if(timeWithoutExceptions.includes(" or ")) {
      timeArr = timeWithoutExceptions.split(" or ");
    } else {
      timeArr[0] = timeWithoutExceptions;
    }
    console.log(timeArr);
    return timeArr;
}

function get(k) {
  return progTimingsMap[k];
}

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
        console.log("check validity failed");
        form.classList.add('was-validated');
        $("#formErrorsModal").modal('show');
      } else {
        console.log("check validity done");
        $("#formErrorsModal").modal('hide');
        submitForm(event);
      }

    }, false);

    $('#payByCashBtn').bind( 'click', function(event) {
      var $regform = $('form#registration-form')[0];
      if (!$regform.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        console.log("check validity failed");
        $regform.classList.add('was-validated');
        $("#formErrorsModal").modal('show');
      } else {
        console.log("check validity done");
        $("#formErrorsModal").modal('hide');
        $("#payByCashPopup").modal('show');
      }

    });

  });
})();

function showLoader(time) {
  $("#loader").show();
  $("#loader").fadeOut(time);
}

function showLoader() {
  $("#loader").show();
}

function hideLoader() {
  $("#loader").hide();
}

function submitFormWithoutPayment(e) {
  showLoader();
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
      hideLoader();
      // show payment sucess msg pop up
      $("#staticBackdropSuccess").modal('show');
    },
    error: function (request, status, error) {
      hideLoader();
      $("#failureModal").modal('show');
      console.log("form submittion failed");
      console.log(request.responseText);
    }
  }).success(
    // synchronous handling
  );
}

function submitForm(e) {
  showLoader();
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
      hideLoader();
      processPayment();
    },
    error: function (request, status, error) {
      hideLoader();
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
  var progDetailArr = progVal.split("_");
  var amount = progDetailArr[progDetailArr.length - 1];
  console.log("amount = " + amount);
  console.log($('input[name="firstName"]').val() + " "
    + $('input[name="lastName"]').val() +
    " email " + $('input[name="email"]').val() +
    " contact " + $('input[name="phone"]').val()) +
    " address " + $('input[name="address"]').val()
  var options = {
    // "key": "rzp_test_kLmsHnmQfdgIhF",//"rzp_live_l91cg0z4PNOkVp", 
    "key": "rzp_live_l91cg0z4PNOkVp", //"rzp_test_kLmsHnmQfdgIhF",
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

      hideLoader();
      // show payment sucess msg pop up
      $("#staticBackdropSuccess").modal('show');

    },
    "modal": {
      "ondismiss": function(){
        hideLoader();
        // reload the page 
        // window.location.reload();
       }
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
    hideLoader();
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

function onYesIUnderstandClick(event) {
  submitFormWithoutPayment(event);
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