
//set path=C:\Program Files\Java\jdk-16.0.2\bin
//javac -cp .;./jar/jsoup-1.14.1.jar ProgramUpdater.java
//java -cp .;./jar/jsoup-1.14.1.jar ProgramUpdater
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

class ProgramUpdater {
    // hindi constants
    public static String REGISTER_NOW_HINDI;
    public static String UPCOMING_PROGS_HINDI;
    public static String REGISTRATION_CLOSED_HINDI;
    public static String VIEW_ALL_UPCOMING_PROGS_HINDI;
    public static String SHOW_YOUR_INTEREST_HINDI;
    public static String PARTICIPANT_HINDI;
    public static String SELECT_PROGRAM_HINDI;
    public static String LEARN_MORE_HINDI;
    // english and hindi progs
    public static ArrayList<Program> progArrEng = new ArrayList<Program>();
    public static ArrayList<Program> progArrHindi = new ArrayList<Program>();
    // file constants - english pages
    public static String UP_PROG_SPLIT_DELIMITER = ",,,,";
    public static String CLASSES_PAGE_ENGLISH = "./classes.html";
    public static String REGISTRATION_PAGE_ENGLISH = "./registration_page.html";
    public static String HOME_PAGE_ENGLISH = "./index.html";
    public static String GALLERY_PAGE_ENGLISH = "./gallery.html";
    // file constants - hindi pages
    public static String CLASSES_PAGE_HINDI = "./classes_hi.html";
    public static String REGISTRATION_PAGE_HINDI = "./registration_page_hi.html";
    public static String HOME_PAGE_HINDI = "./index_hi.html";
    public static String GALLERY_PAGE_HINDI = "./gallery_hi.html";
    // config files
    public static String HINDI_ENGLISH_PROGS_CONFIG_FILE = "./configurables/HindiEnglishProgramConfig.txt";
    public static String HINDI_PROGS_CONFIG_FILE = "./configurables/Hindi Classes.txt";
    public static String ENGLISH_PROGS_CONFIG_FILE = "./configurables/English Classes.txt";
    public static String TESTIMONIES_CONFIG_FILE = "./configurables/Testimonies.txt";
    public static String ALL_PROG_NAMES_TXT = "./configurables/AllProgNames.txt";
    public static Map<String, String> programPageMapEng = new HashMap<String, String>();;
    public static Map<String, String> programPageMapHindi = new HashMap<String, String>();;
    
    public static void main(String[] args) throws IOException {
        
        initProgramPageMap();
        // initHindiProgs();  
        // initEnglishProgs();  
        initPrograms();

        // english pages
        updateClassesPage(progArrEng, CLASSES_PAGE_ENGLISH);
        updateRegistrationPage(progArrEng, REGISTRATION_PAGE_ENGLISH);
        updateHomePage(progArrEng, HOME_PAGE_ENGLISH);
        updateGalleryWithLatestPics(GALLERY_PAGE_ENGLISH);

        // hindi pages
        updateClassesPage(progArrHindi, CLASSES_PAGE_HINDI);
        updateRegistrationPage(progArrHindi, REGISTRATION_PAGE_HINDI);
        updateHomePage(progArrHindi, HOME_PAGE_HINDI);
        updateGalleryWithLatestPics(GALLERY_PAGE_HINDI);
    }

    private static void initProgramPageMap() throws IOException { 
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inStream = new FileInputStream(ALL_PROG_NAMES_TXT);
        final InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
        
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitArr = line.split(",");
            programPageMapHindi.put(splitArr[1], splitArr[2]);  
            programPageMapEng.put(splitArr[0], splitArr[2].replace("_hi", ""));   

            // System.out.println(line);
            // System.out.println(splitArr[0]);
            // System.out.println(splitArr[1]);
            // System.out.println(splitArr[2]);
        }    

        System.out.println(programPageMapEng);
        System.out.println(programPageMapHindi);
    }

    private static void initPrograms() throws IOException {
        // load hindi programs
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inStream = new FileInputStream(HINDI_ENGLISH_PROGS_CONFIG_FILE);
        final InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
        
        // initialize hindi constants
        REGISTER_NOW_HINDI = bufferedReader.readLine();
        REGISTRATION_CLOSED_HINDI = bufferedReader.readLine();
        UPCOMING_PROGS_HINDI = bufferedReader.readLine();
        VIEW_ALL_UPCOMING_PROGS_HINDI = bufferedReader.readLine();
        SHOW_YOUR_INTEREST_HINDI = bufferedReader.readLine();
        PARTICIPANT_HINDI = bufferedReader.readLine();
        SELECT_PROGRAM_HINDI = bufferedReader.readLine();
        LEARN_MORE_HINDI = bufferedReader.readLine();

        String line = null;
        // read program info hindi
        //System.out.println("line - " + line);
        while ((line = bufferedReader.readLine()) != null) {
            String programCode = bufferedReader.readLine().trim();//1

            String programName = bufferedReader.readLine().trim(); // 2 program name
            String[] progNameArr = programName.split(UP_PROG_SPLIT_DELIMITER);

            String tagLine1 = bufferedReader.readLine().trim(); // 3 tag line 1
            String[] tagLine1Arr = tagLine1.split(UP_PROG_SPLIT_DELIMITER);

            String tagLine2 = bufferedReader.readLine().trim(); // 4 tag line 2 
            String[] tagLine2Arr = tagLine2.split(UP_PROG_SPLIT_DELIMITER);

            String language = bufferedReader.readLine().trim(); // 5 language 
            String[] languageArr = language.split(UP_PROG_SPLIT_DELIMITER);

            String date = bufferedReader.readLine().trim(); // 6 date
            String[] dateArr = date.split(UP_PROG_SPLIT_DELIMITER);

            String time = bufferedReader.readLine().trim(); // 7 time
            String[] timeArr = time.split(UP_PROG_SPLIT_DELIMITER);

            String ageLimit = bufferedReader.readLine().trim(); // 8 age limit
            String[] ageLimitArr = ageLimit.split(UP_PROG_SPLIT_DELIMITER);

            String eflyer1 = bufferedReader.readLine().trim(); // 9 1st image
            String eflyer2 = bufferedReader.readLine().trim(); // 10 2nd image
            String eflyer3 = bufferedReader.readLine().trim(); // 11 3rd image if any
            String fee = bufferedReader.readLine().trim(); // 12 fee
            String bannerImagePath = bufferedReader.readLine().trim(); // 13 bannerImagePath
            boolean showInterestBtn = Boolean.parseBoolean(bufferedReader.readLine().trim()); // 14 showInterestBtn
            boolean showUpcomingProgsBtn = Boolean.parseBoolean(bufferedReader.readLine().trim()); // 15 showUpcomingProgsBtn
            boolean showRegisterNowBtn = Boolean.parseBoolean(bufferedReader.readLine().trim()); // 16 showRegisterNowBtn
            boolean showLearnMoreBtn = Boolean.parseBoolean(bufferedReader.readLine().trim()); // 17 showLearnMoreBtn

            // hindi prog
            Program prog = new ProgramUpdater().new Program(
                programCode
                , progNameArr.length > 0? progNameArr[0]:"NA"
                , tagLine1Arr.length > 0? tagLine1Arr[0]:"NA"
                , tagLine2Arr.length > 0? tagLine2Arr[0]:"NA"
                , languageArr.length > 0? languageArr[0]:"NA"
                , dateArr.length > 0? dateArr[0]:"NA"
                , timeArr.length > 0? timeArr[0]:"NA"
                , ageLimitArr.length > 0? ageLimitArr[0]:"NA"
                , eflyer1, eflyer2, eflyer3, fee, bannerImagePath
                , showInterestBtn, showUpcomingProgsBtn, showRegisterNowBtn, showLearnMoreBtn); 
            progArrHindi.add(prog);               

            // english prog
            prog = new ProgramUpdater().new Program(
                programCode
                , progNameArr.length > 1? progNameArr[1]:"NA"
                , tagLine1Arr.length > 1? tagLine1Arr[1]:"NA"
                , tagLine2Arr.length > 1? tagLine2Arr[1]:"NA"
                , languageArr.length > 1? languageArr[1]:"NA"
                , dateArr.length > 1? dateArr[1]:"NA"
                , timeArr.length > 1? timeArr[1]:"NA"
                , ageLimitArr.length > 1? ageLimitArr[1]:"NA"
                , eflyer1, eflyer2, eflyer3, fee, bannerImagePath
                , showInterestBtn, showUpcomingProgsBtn, showRegisterNowBtn, showLearnMoreBtn); 
            progArrEng.add(prog);   
        }    
        System.out.println("Hindi size - "+progArrHindi.size());
        System.out.println("Hindi size - "+progArrEng.size());
    }

    private static void initEnglishProgs() throws IOException {
        // load english programs
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inStream = new FileInputStream(ENGLISH_PROGS_CONFIG_FILE);
        final InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
    
        // read program info english
        String line = null;
        // read program info english
        //System.out.println("line - " + line);
        while ((line = bufferedReader.readLine()) != null) {
            Program prog = new ProgramUpdater().new Program(
                bufferedReader.readLine().trim(), // program code
                bufferedReader.readLine().trim(), // program name
                bufferedReader.readLine().trim(), // tag line 1
                bufferedReader.readLine().trim(), // tag line 2
                bufferedReader.readLine().trim(), // language 
                bufferedReader.readLine().trim(), // date
                bufferedReader.readLine().trim(), // time
                bufferedReader.readLine().trim(), // age limit
                bufferedReader.readLine().trim(), // 1st image
                bufferedReader.readLine().trim(), // 2nd image
                bufferedReader.readLine().trim(), // 3rd image if any
                bufferedReader.readLine().trim(), // fee
                bufferedReader.readLine().trim(), // bannerImagePath
                Boolean.parseBoolean(bufferedReader.readLine().trim()), // showInterestBtn
                Boolean.parseBoolean(bufferedReader.readLine().trim()), // showUpcomingProgsBtn
                Boolean.parseBoolean(bufferedReader.readLine().trim()), // showRegisterNowBtn
                Boolean.parseBoolean(bufferedReader.readLine().trim()) // showLearnMoreBtn
                ); 
            progArrEng.add(prog);   
        }    
        System.out.println("English size - " + progArrEng.size());
    }

    private static void updateRegistrationPage(ArrayList<Program> progArr, String fileName) throws IOException {
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateRegistrationPage - " + language);

        // parse html
        Document doc = Jsoup.parse(new File(fileName), "UTF-8", "");
        
        // start clean
        Element programSelectTag = doc.getElementById("program");
        if(programSelectTag == null) return;
        programSelectTag.html("");

        // add new options
        String optionsHtml;
        if(language.equals("Hindi")) {
            optionsHtml = "<option value=''> " + SELECT_PROGRAM_HINDI + "</option>";
        } else {
            optionsHtml = "<option value=''>- Select Program -</option>";
        }
        for(int i = progArr.size() -1; i >= 0; i--){
            Program program = progArr.get(i);
            if (program.showRegisterNowBtn) {
                optionsHtml += "<option value='" + program.id + "_" + program.amount + "'>" 
                    + program.programName + " | " + program.date + " | " + program.time 
                    + " | " + program.fee + "</option>";
            }
        }
        programSelectTag.html(optionsHtml);

        writeToFile(doc, fileName);
        System.out.println("Updated Registration Page");
    }

    private static void writeToFile(Document doc, String fileName) throws IOException{
        // write to file
        String docHtml = doc.html();
        Writer out = new BufferedWriter(new OutputStreamWriter
                           (new FileOutputStream(fileName), StandardCharsets.UTF_8));
        docHtml = docHtml.replaceAll("\u2019", "&#39;");
        out.append(docHtml);// fix issue which converts single quote to question mark.
        out.flush();
        out.close();
    }

    private static String getAmount(String fee) {
        fee = (fee.substring(1, fee.length())).trim();
        // System.out.println("fee = "+ fee);
        return fee;
    }

    private static void updateClassesPage(ArrayList<Program> progArr
                , String fileName) throws IOException {
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateClassesPage - " + language);
        // parse html
        Document doc = Jsoup.parse(new File(fileName), "UTF-8", "");
        
        // start clean container
        Element upDivParent = doc.getElementById("up");
        if(upDivParent == null) return;
        clearUpcomingPrograms(upDivParent, language);
        
        Element upDiv = doc.getElementById("upcomingPrograms");
        if(upDiv == null) return;
       
        // add programs
        for (int i = 0; i < progArr.size(); i++){
            if(progArr.get(i) != null){
                upDiv.after(getContentForUpcomingPrograms(progArr.get(i), language));
            }
        }
        
        writeToFile(doc, fileName);
        System.out.println("Updated Classes Page");
    }

    private static void updateHomePage(ArrayList<Program> progArr, String fileName) throws IOException {
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateHomePage - " + language);
        // parse html
        Document doc = Jsoup.parse(new File(fileName), "UTF-8", "");
        
        Element sliderDiv = doc.getElementById("slider");
        if(sliderDiv == null) return;
        // start clean
        sliderDiv.html("");

        Element testimonyDiv = doc.getElementById("testimony");
        if(testimonyDiv == null) return;
        // start clean
        testimonyDiv.html("");
        
        // add program banners and testimonies
        for (int i = progArr.size()-1; i >= 0; i--) {
            sliderDiv.append(getContentForBanners(progArr.get(i), language));
        }

        testimonyDiv.append(getContentForTestimony(language));
        
        writeToFile(doc, fileName);
        System.out.println("Updated Home Page - Banners, testimonies & gallery");
    }
    
    private static String getContentForTestimony(String language) throws IOException{

        // load hindi programs
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inStream = new FileInputStream(TESTIMONIES_CONFIG_FILE);
        final InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
        ArrayList<Testimony> testimonyList = new ArrayList<Testimony>();

        // read program info hindi
        String line = null;
        // read program info hindi
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println("line - " + line);
            Testimony testimony = new ProgramUpdater().new Testimony(
                bufferedReader.readLine(),// testimony
                bufferedReader.readLine(),// name
                bufferedReader.readLine(),// photo path
                bufferedReader.readLine() // language
                );
            if(language.equalsIgnoreCase(testimony.language)){
                testimonyList.add(testimony);
            }
        }
        System.out.println("testimonyList size - " + testimonyList.size());
        String content = "";
        for (Testimony t : testimonyList){
            content += "<div class='item'>";
            content += "<div class='testimony-wrap p-4 pb-5'>";
            content += "<div class='text'>";
            content += "<div class='line'>";
            content += "<p class='mb-4 pb-1'>"+ t.testimony +"</p>";
            content += "<span class='quote d-flex align-items-center justify-content-center'>";
            content += "<i class='icon-quote-left'></i>";
            content += "</span>";
            content += "</div>";
            content += "<div class='d-flex align-items-center'>";
            if(!(t.photo).equalsIgnoreCase("NA")){
                content += "<div class='user-img' style='background-image: url(" + t.photo + ")'></div>";
            } 
            content += "<div class='ml-4'>";
            content += "<p class='name'>" + t.name + "</p>";
            if(!language.equalsIgnoreCase("Hindi")){
                content += "<span class='position'>Participant</span>";
            } else {
                content += "<span class='position'>"+ PARTICIPANT_HINDI + "</span>";
            }
            content += "</div>";
            content += "</div>";
            content += "</div>";
            content += "</div>";
            content += "</div>";
        }

        return content;
    }

    private static String getMatchingProgramFile(String programNameOriginal, String language) {
        
        if(language.equalsIgnoreCase("Hindi")){
            for (Map.Entry<String, String> set : programPageMapHindi.entrySet()) {
                String programName = set.getKey();
                String programPage = set.getValue();
                System.out.println(programName + " - " + programPage + " - " + programNameOriginal);
                if(programNameOriginal.startsWith(programName)){
                    return programPage;
                }
            }
        } else {
            for (Map.Entry<String, String> set : programPageMapEng.entrySet()) {
                String programName = set.getKey();
                String programPage = set.getValue();
                System.out.println(programName + " - " + programPage + " - " + programNameOriginal);
                if(programNameOriginal.startsWith(programName)){
                    return programPage;
                }
            }
        }
        
        return "";
    }

    private static String getContentForUpcomingPrograms(Program program, String language){ 
        String content = "<div class='row' id='" + program.id + "'>";
        content += "<div class='col-md-12'>";
        content += "<div class='services-2 ftco-animate d-flex w-100'>";
        content += "<div class='icon d-flex justify-content-center align-items-center'>";
        content += "<span class='flaticon-meditation'></span>";
        content += "</div>";
        content += "<div class='text text-left pl-4'>";
        content += "<a href='" + getMatchingProgramFile(program.programName, language) + "'>" ;
        content += "<h3> <b>" + program.programName + "</b>" ;
        if(!(program.tagLine1).equalsIgnoreCase("NA")) {
            content += " - " + program.tagLine1;
        } 
        if(!(program.tagLine2).equalsIgnoreCase("NA")) {
            content += program.tagLine2;
        } 
        content +=  "</h3></a>";// <b> (" + program.language + ")</b>
        content += "<p class='up-details'>" + program.date + " | " + program.time + " | " + program.ageLimit + " </p>";
        String htmlFilePath = getMatchingProgramFile(program.programName, language);
        if (program.showRegisterNowBtn) {
            if(language.equals("Hindi")){
                 if(!htmlFilePath.equalsIgnoreCase("NA")){
                    content += "<a href='" + htmlFilePath + "' target='_blank' class='btn btn-white px-4 py-3'> " + LEARN_MORE_HINDI + " <span class='ion-ios-arrow-round-forward'></span></a>";
                }content += "  <a href='registration_page_hi.html' target='_blank' class='btn btn-white px-4 py-3'> " + REGISTER_NOW_HINDI + " <span class='ion-ios-arrow-round-forward'></span></a><p></p>";
            } else {
                if(!htmlFilePath.equalsIgnoreCase("NA")){
                    content += "<a href='" + htmlFilePath + "' target='_blank' class='btn btn-white px-4 py-3'> Learn More <span class='ion-ios-arrow-round-forward'></span></a>";
                }
                content += "  <a href='registration_page.html' target='_blank' class='btn btn-white px-4 py-3'> Register Now <span class='ion-ios-arrow-round-forward'></span></a><p></p>";
            }
        } else {
            
            if(language.equals("Hindi")){
                if(!htmlFilePath.equalsIgnoreCase("NA")){
                    content += "<a href='" + htmlFilePath + "' target='_blank' class='btn btn-white px-4 py-3'> " + LEARN_MORE_HINDI + " <span class='ion-ios-arrow-round-forward'></span></a>";
                }
                content += "<p>" + REGISTRATION_CLOSED_HINDI + "</p> ";
            } else {
                if(!htmlFilePath.equalsIgnoreCase("NA")){
                    content += "<a href='" + htmlFilePath + "' target='_blank' class='btn btn-white px-4 py-3'> Learn More <span class='ion-ios-arrow-round-forward'></span></a>";
                }
                content += "<p><b>Registrations Closed</b></p> ";
            }
        } 
        content += "<div class='container-fluid px-md-5'>";
        content += "<div class='row'>";
         
        if(program.eflyerImg1 != null && !"NA".equals(program.eflyerImg1)) {
            content += "<div class='col-md-3 ftco-animate'>";
            content += "<a href='" + program.eflyerImg1 ;
            content += "' class='gallery image-popup img d-flex align-items-center' style='background-image: url(" + program.eflyerImg1 + ");'></a>";
            content += "</div>";
        }
        if(program.eflyerImg2 != null && !"NA".equals(program.eflyerImg2)) {
            content += "<div class='col-md-3 ftco-animate'>";
            content += "<a href='" + program.eflyerImg2 ;
            content += "' class='gallery image-popup img d-flex align-items-center' style='background-image: url(" + program.eflyerImg2 + ");'></a>";
            content += "</div>";
        } 
        if(program.eflyerImg3 != null && !"NA".equals(program.eflyerImg3)) {
            content += "<div class='col-md-3 ftco-animate'>";
            content += "<a href='" + program.eflyerImg3 ;
            content += "' class='gallery image-popup img d-flex align-items-center' style='background-image: url(" + program.eflyerImg3 + ");'></a>";
            content += "</div>";
        }
        content += "</div>";
        content += "</div>";
        content += "</div>";
        content += "</div>";
        content += "</div>";
        content += "</div>";
        content += "</div>";
        return content;
    }

    private static void clearUpcomingPrograms(Element elementToEmpty, String language){
        System.out.println(language);
        if(elementToEmpty == null) return;
        elementToEmpty.html("");
        String upcomingProgramsDiv = "<div class='container'>";
        upcomingProgramsDiv += "<div id='upcomingPrograms' class='row justify-content-center mb-5 pb-3'>";
        upcomingProgramsDiv += "<div class='col-md-12 heading-section ftco-animate text-center fadeInUp ftco-animated'>";
        if(language.equals("Hindi")){
            upcomingProgramsDiv += "  <h2 class='mb-1'>" + UPCOMING_PROGS_HINDI + "</h2>";
        } else {
            upcomingProgramsDiv += "  <h2 class='mb-1'>Upcoming Programs</h2>";
        }  
        
        upcomingProgramsDiv += "</div>";
        upcomingProgramsDiv += "</div>";        
        upcomingProgramsDiv += "</div>";
        elementToEmpty.html(upcomingProgramsDiv);
    }

    private static String getContentForBanners(Program program, String language){
        System.out.println("***\n" + program.toString());
        String content = 
        "<div class='slider-item js-fullheight' style='background-image:url(" + program.bannerImagePath + ");'>";
      	content += "<div class='overlay'></div>";
        content += "<div class='container'>";
        content += "<div class='row no-gutters slider-text js-fullheight align-items-center justify-content-center' data-scrollax-parent='true'>";
        content += "<div class='col-md-10 text ftco-animate'>";
        content += "<h1 class='mb-4' style='color: rgb(252, 251, 249);'>" + program.programName ;
        if(!(program.tagLine1).equalsIgnoreCase("NA")) {
            content += " - " + program.tagLine1 + "</h1>";
        } 
        if(!"NA".equals(program.tagLine2)) {
            content += "<h1 class='mb-4' style='color: rgb(252, 251, 249);'>" + program.tagLine2 + "</h1>";
        }
        if(program.showRegisterNowBtn) {
            content += "<h3 class='subheading'>" + program.date + " | " + program.time + " | " + program.ageLimit + " </h3> <p></p>";
            
            if(language.equals("Hindi")){
                content += "<a href='registration_page_hi.html' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>" + REGISTER_NOW_HINDI + "</a> ";
            } else {
                content += "<a href='registration_page.html' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>Register Now</a> ";
            }           
        }    

        
        if(program.showLearnMoreBtn) {
             if(language.equals("Hindi")){
                content += " <a href='" + getMatchingProgramFile(program.programName, language) + "' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>" + LEARN_MORE_HINDI + "</a> ";
            } else {
                content += " <a href='" + getMatchingProgramFile(program.programName, language) + "' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>Learn More</a> ";
            }           
        }      
            
        if(program.showInterestBtn) {
            if(language.equals("Hindi")){
                content += "<a href='interest_form_hi.html' class='btn btn-white btn-outline-white px-4 py-3 mt-3'" ;
                content += "style='background-color: #ffb5b5; opacity: 90%;'>" + SHOW_YOUR_INTEREST_HINDI + "</a>";
            } else {
                content += "<a href='interest_form.html' class='btn btn-white btn-outline-white px-4 py-3 mt-3'" ;
                content += "style='background-color: #ffb5b5; opacity: 90%;'>Show Your Interest</a>";
            }
        }

        if(program.showUpcomingProgsBtn) {
            if(language.equals("Hindi")){
                content += " <a href='classes_hi.html#up' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>" + VIEW_ALL_UPCOMING_PROGS_HINDI + "</a> ";
            } else {
                content += " <a href='classes.html#up' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>View All Upcoming Classes</a> ";
            }
        }

        content += "</div>";
        content += "</div>";
        content += "</div>";
        content += "</div>";
        return content;
    }

    private static void updateGalleryWithLatestPics(String fileName) throws IOException{
        // parse html
        Document doc = Jsoup.parse(new File(fileName), "UTF-8", "");
        
        final File folder = new File("./images/gallery");
        ArrayList<String> picList = listFilesForFolder(folder);

        Element photosDiv = doc.getElementById("photos");
        if(photosDiv == null) return;

        // start clean
        photosDiv.html("");
        
        // add program banners
        for (int i = 0; i < picList.size(); i++) {
            String pic = picList.get(i);
            String content = "<div class='col-md-3 ftco-animate'> <a href='images/gallery/" + pic + "' class='gallery image-popup img d-flex align-items-center' style='background-image: url(images/gallery/" + pic + ");'>"; 
            content += "<div class='icon mb-4 d-flex align-items-center justify-content-center'> <span class='icon-instagram'></span> ";
            content += "</div> </a>"; 
            content += "</div>";
            photosDiv.append(content);
        }

        writeToFile(doc, fileName);
    }

    public static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> pics = new ArrayList<String>();
        
        File[] files = folder.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
       
        for (final File fileEntry : files) {
            if(fileEntry.getName().endsWith(".webp")){
                pics.add(fileEntry.getName());
            }
        }
        System.out.println(pics);
        return pics;
    }

    class Testimony {
        String testimony;
        String name;
        String photo;
        String language;

        Testimony(String testimony,
            String name,
            String photo,
            String language){
            this.testimony = testimony;
            this.name = name;
            this.photo = photo;
            this.language = language;
        }

    }

    class Program {
        String id;
        String programName;
        String tagLine1;
        String tagLine2;
        String language;
        String date;
        String time;
        String ageLimit;
        String eflyerImg1;
        String eflyerImg2;
        String eflyerImg3;
        String fee;
        String bannerImagePath;
        boolean showInterestBtn; 
        boolean showUpcomingProgsBtn;
        boolean showRegisterNowBtn;
        boolean showLearnMoreBtn;
        String amount;

        Program (String id,
            String programName,
            String tagLine1,
            String tagLine2,
            String language,
            String date,
            String time,
            String age,
            String eflyerImg1,
            String eflyerImg2,
            String eflyerImg3,
            String fee,
            String bannerImagePath,
            boolean showInterestBtn, 
            boolean showUpcomingProgsBtn,
            boolean showRegisterNowBtn,
            boolean showLearnMoreBtn) {
            this.id = id;
            this.programName = convertToUTF8(programName);
            this.tagLine1 = tagLine1;
            this.tagLine2 = tagLine2;
            this.language = language;
            this.date = date;
            this.time = time;
            this.ageLimit = age;
            this.eflyerImg1 = eflyerImg1;
            this.eflyerImg2 = eflyerImg2;
            this.eflyerImg3 = eflyerImg3;
            this.fee = fee;
            this.bannerImagePath = bannerImagePath;
            this.showInterestBtn = showInterestBtn; 
            this.showUpcomingProgsBtn = showUpcomingProgsBtn;
            this.showRegisterNowBtn = showRegisterNowBtn;
            if(fee != null && !fee.equals("")){
                this.amount = getAmount(this.fee);
            }
            this.showLearnMoreBtn = showLearnMoreBtn;
        }

        private String convertToUTF8(String rawString){
            byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
            String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
            return utf8EncodedString;
        }

        public String toString(){
            return this.id + "\n" +
            this.programName + "\n" +
            this.tagLine1 + "\n" +
            this.tagLine2 + "\n" +
            this.language + "\n" +
            this.date + "\n" +
            this.time + "\n" +
            this.ageLimit + "\n" +
            this.eflyerImg1 + "\n" +
            this.eflyerImg2 + "\n" +
            this.eflyerImg3 + "\n" +
            this.fee + "\n" +
            this.bannerImagePath + "\n" +
            this.showInterestBtn + "\n" + 
            this.showUpcomingProgsBtn + "\n" +
            this.showRegisterNowBtn + "\n" +
            this.showLearnMoreBtn + "\n" +
            this.amount;
        }
    }
    
}