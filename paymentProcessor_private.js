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
      $("#staticBackdropSuccess").modal('show');
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