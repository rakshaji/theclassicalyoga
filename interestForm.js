$(window).load(function () {
  $("#loader").fadeOut(500);
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
        console.log("check validity failed");
        form.classList.add('was-validated');
        $("#formErrorsModal").modal('show');
      } else {
        console.log("check validity done");
        $("#formErrorsModal").modal('hide');
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
  showLoader(2000);
  console.log("submitting form");
  var $regform = $('form#interest-form'),
    //test url - do not delete
    //regurl = 'https://script.google.com/macros/s/AKfycbx6IiND5GRpqJsRDKU8TOU-vVO5g94jHHb-73JCD52Wlf_o6691/exec'

    //live
    regurl = 'https://script.google.com/macros/s/AKfycbxLdA6m2peB8V2XDsRkZrgsUmehklnL11I6W9iJxG1kg7va4c_O/exec'

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
      $("#successModal").modal('show');
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
function refreshPage() {

  //reset form
  // $(':input','#interest-form')
  // .not(':button, :submit, :reset, :hidden')
  // .val('')
  // .prop('checked', false)
  // .prop('selected', false);

  // reload the page 
  //window.location.reload();

  $( '#interest-form' ).each(function(){
    this.reset();
});
}
