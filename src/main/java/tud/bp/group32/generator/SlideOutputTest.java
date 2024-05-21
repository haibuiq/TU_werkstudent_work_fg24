// package tud.bp.group32.generator;

// import org.apache.poi.xslf.usermodel.*;

// import tud.bp.group32.ExceptionRoutines;
// import tud.bp.group32.constants.AnredeTyp;
// import tud.bp.group32.constants.ArtOfAbschluss;
// import tud.bp.group32.constants.Auszeichnung;
// import tud.bp.group32.constants.ShortArtOfAbschluss;
// import tud.bp.group32.student.StudentControlfile;

// import java.awt.geom.Rectangle2D;
// import java.io.*;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Objects;

// public class SlideOutputTest {
//     private static String fis1 = "src/main/resources/output_test_template.pptx";
//     private static String fis2 = "src/main/resources/TUDa_PowerPoint_Sublogo_2023_etitlang.pptx";

//     private static String fos0 = "src/main/resources/output_test_template.pptx";
//     private static String fos1 = "src/main/resources/test.pptx";
//     private static String fos2 = "src/main/resources/test_other.pptx";

//     public static void setUpForTest(int caseId){

//         //test of the fixing box text
//         try{
//             switch (caseId){
//                 //fix text box shape of output_test_template
//                 case 1: {
//                     int slideNr = 3, shapeNr = 0;
//                     FileInputStream fis = new FileInputStream(fis1);
//                     FileOutputStream fos = new FileOutputStream(fos1);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);
//                     fixTextShape(slideShow, slideNr, shapeNr);
//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }
//                 //Fix text box shape of new TUDA template (by generating some random student slides with errors)
//                 case 2: {
//                     //Not sure if it works
//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);

//                     //setting up for the case
//                     randomStudentSlide(slideShow);

//                     int slideNr = slideShow.getSlides().size()-1, shapeNr = 1;
//                     fixTextShape(slideShow, slideNr, shapeNr);
//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }
//                 //Writing infos about the slide masters of the given template and also about all their layouts
//                 case 3:{
//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileWriter fw = new FileWriter(fos0);
//                     StringBuilder sb = new StringBuilder();
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);

//                     getInfoSlideMaster(sb, slideShow);
//                     fw.write(String.valueOf(sb));
//                     fw.close();
//                     break;
//                 }
//                 //Writing infos of all slides from the given .pptx files
//                 //( Methods is also public, so this test case is just an example of the usage)
//                 case 4:{
//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileWriter fw = new FileWriter(fos0);
//                     StringBuilder sb = new StringBuilder();
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);

//                     getInfoSlides(sb, slideShow);

//                     fw.write(String.valueOf(sb));
//                     fw.close();
//                     break;
//                 }
//                 //Generate all possible layouts from the new template
//                 case 5:{
//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);

//                     boolean changeFooter = true;

//                     createLayoutAll(slideShow, changeFooter);
//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }
//                 //Generate (random) student slides
//                 case 6:{
//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);
//                     fis.close();

//                     StudentControlfile[] students = generateRandomStudents();
//                     for(StudentControlfile s: students){
//                         StudentSlideGenerator.generateSlideAsGroup(slideShow, s);
//                     }

//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }
//                 //A combination of generating all layouts and random students
//                 case 7: {
//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);
//                     fis.close();

//                     createLayoutAll(slideShow, true);
//                     randomStudentSlide(slideShow);

//                     slideShow.write(fos);
//                     fos.close();
//                 }
//                 //degree slide
//                 case 8: {

//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);
//                     OtherSlideSetting.changeMasterFooterPropertyTwo(slideShow);
//                     fis.close();

//                     String[] degrees = new String[]{"Bachelor of Science", "Master of Science", "international Abschluesse", "Doppeldiplom"};
//                     for(String degree: degrees){
//                         DegreeSlide.generate(slideShow, degree);
//                     }

//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }
//                 //group foto
//                 case 9: {

//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);
//                     OtherSlideSetting.changeMasterFooterPropertyTwo(slideShow);
//                     fis.close();

//                     StudentControlfile[] students = generateRandomStudents();
//                     StudentControlfile[] students2 = new StudentControlfile[]{students[0], students[1], students[2], students[1], students[2]};
//                     GroupfotoSlideGenerator.generate(slideShow, students, students);
//                     GroupfotoSlideGenerator.generate(slideShow, students2, students2);
//                     GroupfotoSlideGenerator.generate(slideShow, students, null);



//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }
//                 //Table slide
//                 case 10:{

//                     FileInputStream fis = new FileInputStream(fis2);
//                     FileOutputStream fos = new FileOutputStream(fos2);
//                     XMLSlideShow slideShow = new XMLSlideShow(fis);
//                     OtherSlideSetting.changeMasterFooterPropertyTwo(slideShow);
//                     fis.close();

//                     int[] numbers = new int[]{64, 74, 6, 3, 27};
//                     TableSlideGenerator.generateSlide(slideShow, numbers[0], numbers[1], numbers[2], numbers[3], numbers[4]);

//                     slideShow.write(fos);
//                     fos.close();
//                     break;
//                 }

//                 //should not happen
//                 default: {
//                     System.out.println("Sorry mate, u called the wrong number :p");
//                 }

//             }
//         } catch (FileNotFoundException e) {
//             System.out.println("didnt find "+e.getCause());
//             throw new RuntimeException(e);
//         } catch (IOException e) {
//             ExceptionRoutines.exceptionRoutine(e);
//         }

//     }


//     /**
//      * Printing the Infos about general and shapes infos of a given template with the id
//      * @param slideShow         The given .pptx template
//      * @param id                Id of the layout from template (slide master)   //might not needed since there could be more than one slide master in case of other
//      * @param stringBuilder     Builder of the text output
//      */
//     private static void getSpecificLayoutInfo(XMLSlideShow slideShow, int id, StringBuilder stringBuilder){
//         XSLFSlideLayout layout = slideShow.getSlideMasters().get(0).getSlideLayouts()[id];
//         stringBuilder.append(String.format("== LayoutName: %s; Id: %d; Type: %s; Shape nr: %d; ph nr: %d; \n", layout.getName(), id, layout.getType(), layout.getShapes().size(), layout.getPlaceholders().length));
//         getShapeInfo(layout.getShapes(), stringBuilder);
//         stringBuilder.append("\n");
//     }

//     private static void getShapeInfo(List<XSLFShape> shapes, StringBuilder stringBuilder){
//         for(XSLFShape shape: shapes){
//             stringBuilder.append(String.format("=== ShapeName: %s; isPH: %s; anchor: %s\n", shape.getShapeName(), shape.isPlaceholder(), shape.getAnchor()));
//             if(shape instanceof XSLFTextShape) stringBuilder.append("==== Shape is also a textbox with: ").append(((XSLFTextShape) shape).getText()).append("\n");
//         }
//     }

//     /**
//      * Getting the infos about all the slide masters of a specific template which include it's layout infos as well
//      * @param stringBuilder Use to set up the string for output
//      * @param slideShow     The given .pptx template we want to get the infos of
//      */
//     private static void getInfoSlideMaster(StringBuilder stringBuilder, XMLSlideShow slideShow){
//         stringBuilder.append("General Infos of the slide masters\n");
//         stringBuilder.append(String.format("Slide toatal Nr: %d;\n", slideShow.getSlideMasters().size()));
//         for(XSLFSlideMaster master: slideShow.getSlideMasters()){
//             stringBuilder.append(String.format("= Layouts nr: %d; Shape nr: %d; ph nr: %d\n",
//                     master.getSlideLayouts().length, master.getShapes().size(), master.getPlaceholders().length));
//             getShapeInfo(master.getShapes(), stringBuilder);
//             stringBuilder.append("----------------------------------------------------------------------------------------\n");
//             for (int i = 0; i < master.getSlideLayouts().length; i++) {
//                 getSpecificLayoutInfo(slideShow, i, stringBuilder);
//             }
//             stringBuilder.append("========================================================================================\n");
//         }
//     }

//     public static void getInfoSlides(StringBuilder stringBuilder, XMLSlideShow slideShow){
//         stringBuilder.append(String.format("General Infos of generated %d slides and their pageSize %s\n",
//                 slideShow.getSlides().size(), slideShow.getPageSize()));
//         for(XSLFSlide slide: slideShow.getSlides()){
//             stringBuilder.append(String.format("= Slide id: %d; Name: %s; Layout: %s\n", slide.getSlideNumber(), slide.getSlideName(), slide.getSlideLayout()));
//             getShapeInfo(slide.getShapes(), stringBuilder);
//             stringBuilder.append("\n");
//         }
//         stringBuilder.append("========================================================================================\n");
//     }


//     private static void randomStudentSlide(XMLSlideShow slideShow){
//         for(StudentControlfile s: generateRandomStudents()){
// //                System.out.printf("mnr: %d; badge: %s; first: %s, last: %s\n", s.getMatrikelnr(), s.getAuszeichnung(), s.getVorname(), s.getNachname());
//             StudentSlideGenerator.generateSlideAsGroup(slideShow, s);
//         }
//     }

//     private static StudentControlfile[] generateRandomStudents(){
//         String matrikel1 = "11111";
//         String matrikel2 = "22222";
//         String matrikel3 = "33333";

//         String abs1 = "I wish i already know";
//         String examiner = "Prof. Dr. Karsten Weiher";
//         String fachrichtung = "Automatisierungstechnik";
//         StudentControlfile s1 = new StudentControlfile(matrikel1, AnredeTyp.FRAU, ShortArtOfAbschluss.SHORT_MASTER_SC,
//                 ArtOfAbschluss.ART_MASTER_SC, "Max", "Mustermann", fachrichtung, abs1, false, examiner, Auszeichnung.NONE, true, "1.5", "sehr gut");
//         StudentControlfile s2 = new StudentControlfile(matrikel2, AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
//                 ArtOfAbschluss.ART_BACHELOR_SC, "Erika", "Mustermann", fachrichtung, abs1, false, examiner, Auszeichnung.SILVER, true, "3.00", "gut");
//         StudentControlfile s3 = new StudentControlfile(matrikel3, AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_MASTER_ED,
//                 ArtOfAbschluss.ART_MASTER_SC, "Erika-zwei", "Mustermann", fachrichtung,
//                 "Design und Realisierung eines Demonstrators zur Wasserstoff-Herstellung durch Elektrolyse von Wasser und anschließende Rück-verstromung in einer Polymerelektrolytmembran-Brennstoffzelle unter Verwendung von Elektrodenmaterialien aus der Forschung",
//                 false, examiner, Auszeichnung.GOLD, true, "1.00", "mA");

//         return new StudentControlfile[]{s1, s2, s3};
//     }

//     protected static void createLayoutAll(XMLSlideShow ppt, boolean setFooter) {
//         if (setFooter) changeMasterFooterPropertyTwo(ppt);
//         XSLFSlideLayout[] layouts = ppt.getSlideMasters().get(0).getSlideLayouts();
//         XSLFSlide[] slides = new XSLFSlide[ppt.getSlideMasters().get(0).getSlideLayouts().length];
//         for (int i = 0; i < slides.length; i++) {
//             slides[i] = ppt.createSlide(layouts[i]);
//             int finalI = i;
//             Arrays.stream(slides[i].getPlaceholders()).filter(sh -> Objects.equals(sh.getShapeName(), "Foliennummernplatzhalter 8")).forEach(ph -> ph.setText(String.valueOf(finalI + 4)));
//             slides[i].getShapes().stream().filter(sh -> Objects.equals(sh.getShapeName(), "Foliennummernplatzhalter 8")).forEach(sh -> sh.getPlaceholderDetails().setVisible(true));
//         }
//     }

//     private static void changeMasterFooterPropertyTwo(XMLSlideShow slideShow){
//         XSLFSlideMaster master = slideShow.getSlideMasters().get(0);
//         XSLFTextShape tsh = (XSLFTextShape) master.getShapes().get(6);
//         tsh.setText("Fachbereich 18 (etit) | Dekan | Absolventenfeier");
//     }



//     private static void fixTextShape(XMLSlideShow slideShow, int slideNr, int shapeNr){
//         //VERY IMPORTANT as offset from footer
//         final int OFFSET = 50;

//         //from output_test_template <3, 0>
//         XSLFTextShape tsh = (XSLFTextShape) slideShow.getSlides().get(slideNr).getShapes().get(shapeNr);

//         System.out.print("Before: "+tsh.getAnchor()+"\n");
//         for(XSLFTextParagraph p: tsh){
//             System.out.print(p.getTextRuns().stream().map(XSLFTextRun::getFontSize).toList()+"\t");
//         }
//         System.out.println();

//         //fix horizontal overlapping
//         if(tsh.getAnchor().getMaxY() >= slideShow.getPageSize().getHeight()-OFFSET) {
//             double prevX = tsh.getAnchor().getX(), prevY = tsh.getAnchor().getY(), prevWidth = tsh.getAnchor().getWidth(), prevHeight = tsh.getAnchor().getHeight();
//             double offset = tsh.getAnchor().getMaxY() - slideShow.getPageSize().getHeight() + OFFSET;
//             double x = prevWidth*prevHeight/(prevHeight - offset) -prevWidth;
//             double newX = prevX -x, newY = prevY , newWidth = prevWidth +x, newHeight = prevHeight -offset;
//             tsh.setAnchor(new Rectangle2D.Double(newX, newY, newWidth, newHeight));
//         }

//         //fix vertical overlapping
//         if(tsh.getAnchor().getMinX() < 0){
//             tsh.setAnchor(new Rectangle2D.Double(0, tsh.getAnchor().getY(), tsh.getAnchor().getWidth(), tsh.getAnchor().getHeight()));
//         }

//         System.out.print("After: "+tsh.getAnchor()+"\n");
//         for(XSLFTextParagraph p: tsh){
//             System.out.print(p.getTextRuns().stream().map(XSLFTextRun::getFontSize).toList()+"\t");
//         }
//         System.out.println();
//     }

// }
