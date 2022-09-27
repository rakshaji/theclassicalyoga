$(window).load(function () {

    // upcoming programs carousal
    var owl = $('#home-carousel');
    owl.owlCarousel({
        items:4,
        loop:false,
        margin:10,
        rewind:true,
        autoplay:true,
        autoplayTimeout:2000,
        autoplayHoverPause:true,
        responsiveClass: true,
        autoHeight: true,
        smartSpeed: 800,
        nav: true,
        responsive: {
            0: {
            items: 1,
            margin:10
            },

            600: {
            items: 3,
            margin:10
            },

            1024: {
            items: 4,
            margin:10
            },

            1366: {
            items: 4,
            margin:10
            }
        }
    });
    
    //owl.trigger('play.owl.autoplay',[1000])
    // $('.play').on('click',function(){
        
    // })
    // $('.stop').on('click',function(){
    //     owl.trigger('stop.owl.autoplay')
    // })
    
    //$('#saveSoilModal').modal('show');
    // for classes page
    $(".up-prog-info2").each(function(){

        var infoText = $(this).text();
        //console.log(infoText);  
        // get all program details in an array
        const progDetailArr = infoText.split(" | ");
        let date = progDetailArr[0];
        //console.log(date);

        var today = new Date(); 
        var year = today.getFullYear();
        //console.log(today);

        const dateArr = date.split(" - ");
        //console.log(dateArr);

        var from = new Date(getEnglishDate(dateArr[0])); 
        //keep open on the first day of program for the evening programs
        from.setDate(from.getDate() + 1);
        //console.log(from + " " + to);

        if(today >= from) {
            //console.log("Yes! " + today + " >= " + from);
            // only hide the reg button for currently running programs & mark closed
            $(this).closest(".text").find("#reg_btn").hide();
            $(this).closest(".text").find(".up-venue").text("Registration closed.");
        }
        
        if(dateArr.length > 1){
            var to = new Date(getEnglishDate(dateArr[1]));
            console.log(from + " " + to);
            if(today > to) {
                // once last date passes, remove from view
                $(this).closest(".row").hide();
            }
        }

    });

    // for registration page
    // get all the timings in and id-timings map
    $("#program option").each(function()
    {
        var optionText = $(this).text();
        console.log(optionText);
        var key = $(this).val();
        if(key == "") {
        return;
        }
        // get all program details in an array
        const progDetailArr = optionText.split(" | ");
        let date1 = progDetailArr[1];
        var today = new Date(); 
        //var year = today.getFullYear();
        //console.log(date1);

        const dateArr = date1.split(" - ");
        //console.log(dateArr);

        var from = new Date(getEnglishDate(dateArr[0])); 
        //keep open on the first day of program for the evening programs or onspot regs
        from.setDate(from.getDate() + 1);
        //console.log(from + " " + to);

        if(today >= from) {
            // disable the option for currently running programs
            $(this).hide();//prop('disabled', true);
        }

        if(dateArr.length > 1){
            var to = new Date(getEnglishDate(dateArr[1]));
            //console.log(from + " " + to);
            if(today > to) {
                // once last date passes, remove from view
                $(this).remove();
            }
        }
    });

    // for home/banner page
    // $(".subheading").each(function(){

    //     var infoText = $(this).text();
    //     //console.log(infoText);  
    //     // get all program details in an array
    //     const progDetailArr = infoText.split(" | ");
    //     let date = progDetailArr[0];
    //     //console.log(date);

    //     var today = new Date(); 
    //     var year = today.getFullYear();
    //     //console.log(today);

    //     const dateArr = date.split(" - ");
    //     //console.log(dateArr);

    //     var from = new Date(getEnglishDate(dateArr[0])); 
    //     //keep open on the first day of program for the evening programs
    //     from.setDate(from.getDate() + 1);
        
    //     if(today >= from) {
    //         //console.log("Yes! " + today + " >= " + from);
    //         // only remove the button for currently running programs
    //         $(this).closest(".owl-item").find(".btn").remove();
    //     }

    //     if(dateArr.length > 1){
    //         var to = new Date(getEnglishDate(dateArr[1]));
    //         //console.log(from + " " + to);
    //         if(today > to) {
    //             //console.log("Yes! " + today + " >= " + from);
    //             // once last date passes, remove from view
    //             $(this).closest(".owl-item").remove();
    //         }
    //     }

    // });

});

function getEnglishDate(hindiDate){
    console.log(hindiDate);
    if(hindiDate == undefined){
        return hindiDate;
    }
    hindiDate = hindiDate.replace('जनवरी','January');
    hindiDate = hindiDate.replace('फरवरी','February');
    hindiDate = hindiDate.replace('मार्च','March');
    hindiDate = hindiDate.replace('अप्रैल','April');
    hindiDate = hindiDate.replace('मई','May');
    hindiDate = hindiDate.replace('जून','June');
    hindiDate = hindiDate.replace('जुलाई','July');
    hindiDate = hindiDate.replace('अगस्त','August');
    hindiDate = hindiDate.replace('सितम्बर','September');
    hindiDate = hindiDate.replace('अक्टूबर','October');
    hindiDate = hindiDate.replace('नवम्बर','November');
    hindiDate = hindiDate.replace('दिसम्बर','December');
    console.log(hindiDate);
    return hindiDate;
}
    