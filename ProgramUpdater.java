
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
    public static String REGISTER_NOW_HINDI;
    public static String UPCOMING_PROGS_HINDI;
    public static String REGISTRATION_CLOSED_HINDI;
    public static String VIEW_ALL_UPCOMING_PROGS_HINDI;
    public static String SHOW_YOUR_INTEREST_HINDI;
    public static ArrayList<Program> progArrEng = new ArrayList<Program>();
    public static ArrayList<Program> progArrHindi = new ArrayList<Program>();;
    
    public static void main(String[] args) throws IOException {
        initHindiProgs();  
        initEnglishProgs();  

        // english pages
        updateClassesPage(progArrEng, "./classes.html");
        updateRegistrationPage(progArrEng, "./registration_page.html");
        updateHomePage(progArrEng, "./index.html");
        updateGalleryWithLatestPics("./index.html");

        // hindi pages
        updateClassesPage(progArrHindi, "./classes_hi.html");
        updateRegistrationPage(progArrHindi, "./registration_page_hi.html");
        updateHomePage(progArrHindi, "./index_hi.html");
        updateGalleryWithLatestPics("./index_hi.html");
    }

    private static void initHindiProgs() throws IOException {
        // load hindi programs
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inStream = new FileInputStream("./Hindi Classes.txt");
        final InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
        
        // initialize hindi variables
        REGISTER_NOW_HINDI = bufferedReader.readLine();
        REGISTRATION_CLOSED_HINDI = bufferedReader.readLine();
        UPCOMING_PROGS_HINDI = bufferedReader.readLine();
        VIEW_ALL_UPCOMING_PROGS_HINDI = bufferedReader.readLine();
        SHOW_YOUR_INTEREST_HINDI = bufferedReader.readLine();
        String line = null;
        // read program info hindi
        System.out.println("line - " + line);
        while ((line = bufferedReader.readLine()) != null) {
            Program prog = new ProgramUpdater().new Program(
                bufferedReader.readLine().trim(), // program code
                bufferedReader.readLine().trim(), // program name
                bufferedReader.readLine().trim(), // tag line 
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
                Boolean.parseBoolean(bufferedReader.readLine().trim()) // showRegisterNowBtn
                ); 
            progArrHindi.add(prog);   
        }    
        System.out.println("Hindi size - "+progArrHindi.size());
    }

    private static void initEnglishProgs() throws IOException {
        // load hindi programs
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inStream = new FileInputStream("./English Classes.txt");
        final InputStreamReader streamReader = new InputStreamReader(inStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
    
        // read program info hindi
        String line = null;
        // read program info hindi
        System.out.println("line - " + line);
        while ((line = bufferedReader.readLine()) != null) {
            Program prog = new ProgramUpdater().new Program(
                bufferedReader.readLine().trim(), // program code
                bufferedReader.readLine().trim(), // program name
                bufferedReader.readLine().trim(), // tag line 
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
                Boolean.parseBoolean(bufferedReader.readLine().trim()) // showRegisterNowBtn
                ); 
            progArrEng.add(prog);   
        }    
        System.out.println("English size - " + progArrEng.size());
    }

    private static void updateRegistrationPage(ArrayList<Program> progArr, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        File input = new File(fileName);
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateRegistrationPage - " + language);
        // parse html
        Document doc = Jsoup.parse(input, "UTF-8", "");
        
        // start clean
        Element programSelectTag = doc.getElementById("program");
        if(programSelectTag == null) return;
        programSelectTag.html("");

        // add new options
        String optionsHtml = "<option value=''>- Select Program -</option>";
        for(int i = 0; i < progArr.size(); i++){
            Program program = progArr.get(i);
            if (program.showRegisterNowBtn) {
                optionsHtml += "<option value='" + program.id + "_" + program.price + "'>" 
                    + program.programName + " | " + program.date + " | " + program.time 
                    + " - " + program.price + "</option>";
            }
        }
        programSelectTag.html(optionsHtml);

        // write to file
        String docHtml = doc.html();
        Writer out = new BufferedWriter(new OutputStreamWriter
                           (new FileOutputStream(fileName), StandardCharsets.UTF_8));
        out.append(docHtml);
        out.flush();
        out.close();
        System.out.println("Updated Registration Page");
    }

    private static void updateClassesPage(ArrayList<Program> progArr, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        File input = new File(fileName);
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateClassesPage - " + language);
        // parse html
        Document doc = Jsoup.parse(input, "UTF-8", "");
        
        // start clean container
        Element upDivParent = doc.getElementById("up");
        if(upDivParent == null) return;
        clearUpcomingPrograms(upDivParent, language);
        
        Element upDiv = doc.getElementById("upcomingPrograms");
        if(upDiv == null) return;
       
        // add programs
        for (int i = 0; i < progArr.size(); i++){
            if(progArr.get(i) != null){
                upDiv.after(getContentForClasses(progArr.get(i), language));
            }
        }
        
       // write to file
        String docHtml = doc.html();
        // byte[] htmlToBytes = docHtml.getBytes();
        // Files.write(path, htmlToBytes);

        Writer out = new BufferedWriter(new OutputStreamWriter
                           (new FileOutputStream(fileName), StandardCharsets.UTF_8));
        out.append(docHtml);
        out.flush();
        out.close();
        System.out.println("Updated Classes Page");
    }

    private static void updateHomePage(ArrayList<Program> progArr, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        File input = new File(fileName);
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateHomePage - " + language);
        // parse html
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element sliderDiv = doc.getElementById("slider");
        if(sliderDiv == null) return;
        // start clean
        sliderDiv.html("");
        
        // add program banners
        for (int i = 0; i < progArr.size(); i++) {
            sliderDiv.append(getContentForHomePage(progArr.get(i), language));
        }

        // write to file
        String docHtml = doc.html();
        // byte[] htmlToBytes = docHtml.getBytes();
        // Files.write(path, htmlToBytes);

        Writer out = new BufferedWriter(new OutputStreamWriter
                           (new FileOutputStream(fileName), StandardCharsets.UTF_8));
        out.append(docHtml);
        out.flush();
        out.close();
        System.out.println("Updated Home Page");
    }
    

    private static String getContentForClasses(Program program, String language){ 
        String content = "<div class='row' id='" + program.id + "'>";
        content += "<div class='col-md-12'>";
        content += "<div class='services-2 ftco-animate d-flex w-100'>";
        content += "<div class='icon d-flex justify-content-center align-items-center'>";
        content += "<span class='flaticon-meditation'></span>";
        content += "</div>";
        content += "<div class='text text-left pl-4'>";
        content += "<h3> <b>" + program.programName + "</b> - " + program.tagLine + " <b> (" + program.language + ")</b> </h3>";
        content += "<p class='up-details'>" + program.date + " | " + program.time + " | " + program.ageLimit + " </p>";
        if (program.showRegisterNowBtn) {
            if(language.equals("Hindi")){
                content += "<a href='registration_page_hi.html' target='_blank' class='btn btn-white px-4 py-3'> " + REGISTER_NOW_HINDI + " <span class='ion-ios-arrow-round-forward'></span></a><p></p>";
            } else {
                content += "<a href='registration_page.html' target='_blank' class='btn btn-white px-4 py-3'> Register Now <span class='ion-ios-arrow-round-forward'></span></a><p></p>";
            }
        } else {
            if(language.equals("Hindi")){
                content += "<p>" + REGISTRATION_CLOSED_HINDI + "</p> ";
            } else {
                content += "<p>Registeration Closed</p> ";
            }
        } 
        content += "<div class='container-fluid px-md-5'>";
        content += "<div class='row'>";
         
        if(program.eflyerImg1 != null && !"".equals(program.eflyerImg1)) {
            content += "<div class='col-md-3 ftco-animate'>";
            content += "<a href='" + program.eflyerImg1 ;
            content += "' class='gallery image-popup img d-flex align-items-center' style='background-image: url(" + program.eflyerImg1 + ");'></a>";
            content += "</div>";
        }
        if(program.eflyerImg2 != null && !"".equals(program.eflyerImg2)) {
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

    private static String getContentForHomePage(Program program, String language){
        System.out.println(program.toString());
        String content = 
        "<div class='slider-item js-fullheight' style='background-image:url(" + program.bannerImagePath + ");'>";
      	content += "<div class='overlay'></div>";
        content += "<div class='container'>";
        content += "<div class='row no-gutters slider-text js-fullheight align-items-center justify-content-center' data-scrollax-parent='true'>";
        content += "<div class='col-md-10 text ftco-animate'>";
        content += "<h1 class='mb-4' style='color: rgb(252, 251, 249);'>" + program.programName + " - " + program.tagLine + "</h1>";
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
        
        if(program.showUpcomingProgsBtn) {
            if(language.equals("Hindi")){
                content += "<a href='classes_hi.html#up' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>" + VIEW_ALL_UPCOMING_PROGS_HINDI + "</a> ";
            } else {
                content += "<a href='classes.html#up' class='btn btn-white btn-outline-white px-4 py-3 mt-3' ";
                content += "style='background-color: #ffb5b5; opacity: 90%;'>View All Upcoming Classes</a> ";
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
        content += "</div>";
        content += "</div>";
        content += "</div>";
        content += "</div>";
        return content;
    }

    private static void updateGalleryWithLatestPics(String fileName) throws IOException{
        final File folder = new File("./images/gallery");
        ArrayList<String> picList = listFilesForFolder(folder);

        Path path = Paths.get(fileName);
        File input = new File(fileName);
        String language = fileName.endsWith("_hi.html")? "Hindi" : "English";
        System.out.println(fileName + "    |    updateGallery - " + language);

        // parse html
        Document doc = Jsoup.parse(input, "UTF-8", "");
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

        // write to file
        String docHtml = doc.html();
        // byte[] htmlToBytes = docHtml.getBytes();
        // Files.write(path, htmlToBytes);

        Writer out = new BufferedWriter(new OutputStreamWriter
                           (new FileOutputStream(fileName), StandardCharsets.UTF_8));
        out.append(docHtml);
        out.flush();
        out.close();
        System.out.println("Updated Gallery");
    }

    public static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> pics = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if(fileEntry.getName().endsWith("webp")){
                pics.add(fileEntry.getName());
            }
        }
        System.out.println(pics);
        return pics;
    }

    class Program {
        String id;
        String programName;
        String tagLine;
        String language;
        String date;
        String time;
        String ageLimit;
        String eflyerImg1;
        String eflyerImg2;
        String eflyerImg3;
        String price;
        String bannerImagePath;
        boolean showInterestBtn; 
        boolean showUpcomingProgsBtn;
        boolean showRegisterNowBtn;

        Program (String id,
            String programName,
            String tagLine,
            String language,
            String date,
            String time,
            String age,
            String eflyerImg1,
            String eflyerImg2,
            String eflyerImg3,
            String price,
            String bannerImagePath,
            boolean showInterestBtn, 
            boolean showUpcomingProgsBtn,
            boolean showRegisterNowBtn) {
            this.id = id;
            this.programName = convertToUTF8(programName);
            this.tagLine = tagLine;
            this.language = language;
            this.date = date;
            this.time = time;
            this.ageLimit = age;
            this.eflyerImg1 = eflyerImg1;
            this.eflyerImg2 = eflyerImg2;
            this.eflyerImg3 = eflyerImg3;
            this.price = price;
            this.bannerImagePath = bannerImagePath;
            this.showInterestBtn = showInterestBtn; 
            this.showUpcomingProgsBtn = showUpcomingProgsBtn;
            this.showRegisterNowBtn = showRegisterNowBtn;
        }

        private String convertToUTF8(String rawString){
            byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
            String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
            return utf8EncodedString;
        }

        public String toString(){
            return this.id + "\n" +
            this.programName + "\n" +
            this.tagLine + "\n" +
            this.language + "\n" +
            this.date + "\n" +
            this.time + "\n" +
            this.ageLimit + "\n" +
            this.eflyerImg1 + "\n" +
            this.eflyerImg2 + "\n" +
            this.eflyerImg3 + "\n" +
            this.price + "\n" +
            this.bannerImagePath + "\n" +
            this.showInterestBtn + "\n" + 
            this.showUpcomingProgsBtn + "\n" +
            this.showRegisterNowBtn;
        }
    }
    
}