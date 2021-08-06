const programArr = [
    {
        id: "SK1", 
        programName: "Surya Kriya", 
        tagLine: "Fire Up The Sun Within You", 
        language: "English", 
        date: "2 AUG to 3 AUG", 
        time: "6AM to 8AM", 
        age: "14+ years", 
        eflyerImg1: "images/flyer/SuryaKriya/SuryaKriya1.png",
        eflyerImg2: "images/flyer/SuryaKriya/SuryaKriya2.webp", 
        eflyerImg3: ""
    }
    // },
    // {
    //     id: "ANG1", 
    //     programName: "Angamardana", 
    //     tagLine: "The Ultimate Fitness Session", 
    //     language: "English", 
    //     date: "15 JUL to 30 JUL", 
    //     time: "9AM to 11AM",
    //     eflyerImg1: "images/flyer/SuryaKriya/SuryaKriya1.png", 
    //     eflyerImg2: "images/flyer/SuryaKriya/SuryaKriya2.webp", 
    //     eflyerImg3: ""
    // }
];

function dynamicUpdateProgramsInRegistrationPage() {

    for (let i = 0; i < programArr.length; i++) {
        let program = programArr[i];

        let content = '<div class="row" id="' + program.id + '">\n';
        content += '<div class="col-md-12">\n';
        content += '<div class="services-2 ftco-animate d-flex w-100">\n';
        content += '<div class="icon d-flex justify-content-center align-items-center">\n';
        content += '<span class="flaticon-meditation"></span>\n';
        content += '</div>\n';
        content += '<div class="text text-left pl-4">\n';
        content += '<h3> <b>' + program.programName + '</b> - ' + program.tagLine + ' <b> (in ' + program.language + ')</b> </h3>\n';
        content += '<p class="up-details">' + program.date + ' | ' + program.time + ' | Age - ' + program.age + ' </p>\n';
        content += '<p>\n';
        content += '<a href="registration_page.html" target="_blank" class="btn btn-white px-4 py-3"> Register Now <span class="ion-ios-arrow-round-forward"></span></a> \n';
        content += '<div class="container-fluid px-md-5">\n';
        content += '<div class="row">\n';
        content += '<div class="col-md-3 ftco-animate">\n';
        content += '<a href="' + program.eflyerImg1 + '" class="gallery image-popup img d-flex align-items-center" style="background-image: url(' + program.eflyerImg1 + ');"></a>\n';
        content += '</div>\n';
        content += '<div class="col-md-3 ftco-animate">\n';
        content += '<a href="' + program.eflyerImg2 + '" class="gallery image-popup img d-flex align-items-center" style="background-image: url(' + program.eflyerImg2 + ');">\n';
        content += '</a>\n';
        content += '</div>\n';
        content += '</div>\n';
        content += '</div>\n';
        content += '</div>\n';
        content += '</div>\n';
        content += '</div>\n';
        content += '</div>\n';
        if (i == 0) {
            $('#upcomingPrograms').after(content);
        } else {
            $('#' + programArr[i - 1].id).after(content);
        }

    }
}
