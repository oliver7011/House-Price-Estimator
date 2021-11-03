import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.io.*;
public class HousePriceEstimator {
    private ArrayList<ArrayList<String>> house_data;
    private long startTime;
    private String url = "";
   public void web_scrape(int number) throws IOException {
      startTime = System.currentTimeMillis();
      house_data = new ArrayList<ArrayList<String>>();
      int page_size = 0;
      int pages = 1;
      int last = 0;
      if (number <= 100) {
          pages = 1;
          last = number;
      } else {
          pages = (number / 100) + 1;
          last = number % 100; 
      }
      for (int page=1; page <= pages; page++) {
          page_size = 100;
          if (page == pages) {
              page_size = last;
          }
          url = "https://www.zoopla.co.uk/for-sale/property/putney/putney/?page_size=" + page_size + "&q=putney&radius=0&results_sort=newest_listings&search_source=refine&pn=" + page;
          Document document;
          document = Jsoup.connect(url).get();
          System.out.println(document.title());
          Elements paragraphs = document.getElementsByTag("p");
          Elements houses = document.getElementsByClass("earci3d1 css-tk5q7b-Wrapper-ListingCard-StyledListingCard e2uk8e10");     
          String house_price = null;
          String house_station_distance = null;
          String house_location = null;
          for (Element house : houses) {
             ArrayList<String> property = new ArrayList<String>();
             String house_string = house.text();
             Elements rooms = house.getElementsByClass("css-58bgfg-WrapperFeatures e2uk8e15");
             Elements prices = house.getElementsByClass("css-qmlb99-CardHeader e2uk8e9");        
             Elements locations = house.getElementsByClass("e2uk8e4 css-16zqmgg-StyledLink-Link-FullCardLink e33dvwd0");
             Elements station_distance = house.getElementsByClass("ejjz7ko0 css-l6ka86-Wrapper-IconAndText e3e3fzo1");
             for (Element price: prices) {
                 house_price = price.text();
             }
             for (Element distance: station_distance) {
                house_station_distance = station_distance.text();
                }
             for (Element location: locations) {
                 Elements locations2 = location.getElementsByClass("css-wfe1rf-Text eczcs4p0");
                 for (Element location2: locations2) {
                     house_location = location2.text();
                 }
             }
             
             ArrayList<String> room_list = new ArrayList<String>();
             for (Element room : rooms) {            
                 String[] group = (room.text()).split(" ");
                 
                 for (String n : group) {
                     room_list.add(n);
                 }
                 
                 if (group.length == 1) {
                     room_list.add("1");
                     room_list.add("1");
                 } else if (group.length == 2) {
                     room_list.add("1");                
                 }
                 System.out.println(house_location + "|Price: " + house_price + "|Bedrooms: " + room_list.get(0) + "|Bathrooms: " + room_list.get(1)+ "|Living: " + room_list.get(2)+ "|Station: " + house_station_distance);         
             }
             house_price = house_price.replace(",","");
             house_price = house_price.substring(house_price.lastIndexOf('£') + 1);
             property.add(house_location.replace(",",""));
             property.add(house_price);
             property.add(room_list.get(0));
             property.add(room_list.get(1));
             property.add(room_list.get(2));
             property.add(house_station_distance);
             house_data.add(property);
    
          }
        }
     long endTime = System.currentTimeMillis();
     System.out.println("Total time taken: " + ((endTime - startTime)/1000F + " seconds"));
   }

   public ArrayList getHouse(int index) {
       ArrayList house = house_data.get(index);  
       return house;
   }
   
   public int count() {
       return house_data.size();
   }
   
   public void ExportDateToCSV(String name) throws IOException {
       String path = name += ".csv";
       FileWriter csvWriter = new FileWriter(path);
       String house_string = "";
       
       csvWriter.append("Location");
       csvWriter.append(",");
       csvWriter.append("Price");
       csvWriter.append(",");
       csvWriter.append("Bedrooms");
       csvWriter.append(",");
       csvWriter.append("Bathrooms");
       csvWriter.append(",");
       csvWriter.append("Living Rooms");
       csvWriter.append(",");
       csvWriter.append("Closest station");
       csvWriter.append("\n");
       
       for (int row=0; row<house_data.size(); row ++) {
           ArrayList house = getHouse(row);
           house_string = "";
           for (int col=0; col<house.size(); col ++) {
               house_string += house.get(col) + ",";
           }
           csvWriter.append(house_string);
           System.out.println(house_string);
           csvWriter.append("\n");
           
       }
       csvWriter.flush();
       csvWriter.close();       
   }
   
   public void multipleRegression(int bedrooms,int bathrooms, int living_rooms) {
       
       
       
       
   }
   
}