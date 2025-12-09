package paagbi.bat;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import org.bson.conversions.Bson;


public class KontsultakWithouPojo {
    static String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net";

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase mflixDatabase = mongoClient.getDatabase("sample_mflix");
            MongoDatabase geospatialDatabase = mongoClient.getDatabase("sample_geospatial");
            MongoCollection<Document> moviesCollection = mflixDatabase.getCollection("movies");
            MongoCollection<Document> theatersCollection = geospatialDatabase.getCollection("shipwrecks");
            
            Bson projectionFields = Projections.fields( // eremu zehatzak aukeratzeko
                    Projections.include("title", "year", "directors", "countries"), // beharrezko eremuak
                    Projections.excludeId()); // _id eremua ez erakusteko
            
            // Movies released in 1920
            FindIterable<Document> docs1 = moviesCollection.find(eq("year", 1920))
                .projection(projectionFields) // eremu zehatzak aukeratu
                .sort(Sorts.ascending("title")); // ordenatu izenaren arabera
            
            System.out.println("Movies released in 1920:");
            docs1.forEach(doc -> System.out.println("- " + doc.get("title") + " (" + doc.get("year") + ")\n- Directed by: " + doc.get("directors")));
            System.out.println();
            
            // Movies directed by Quentin Tarantino
            FindIterable<Document> docs2 = moviesCollection.find(eq("directors", "Quentin Tarantino"))
                .projection(projectionFields)
                .sort(Sorts.ascending("year"));
            
            System.out.println("\nMovies directed by Quentin Tarantino:");
            docs2.forEach(doc -> System.out.println("- " + doc.get("title") + " (" + doc.get("year") + ") - Directed by: " + doc.get("directors")));
            System.out.println();

            //Movies from outside USA
            Bson filterUSA = Filters.ne("countries", "USA");

            FindIterable<Document> docs3 = moviesCollection.find(filterUSA) // .find(Filters.ne("countries", "USA")) <-- USAtik kanpoko filmak bilatzeko
                .projection(projectionFields) // eremu zehatzak aukeratu
                .sort(Sorts.ascending("countries"));
        
            System.out.println("\nMovies from outside USA:");
            docs3.forEach(doc -> System.out.println("- " + doc.get("title") + " (" + doc.get("year") + ") - Countries: " + doc.get("countries")));
            System.out.println();

            // Movies from Spain released after 2014
            Bson filterSPAIN = Filters.and( // bi irizpideak bete behar dira
                Filters.eq("countries", "Spain"),
                Filters.gt("year", 2014)
            );

            FindIterable<Document> docs4 = moviesCollection.find(filterSPAIN)
                .projection(projectionFields)
                .sort(Sorts.ascending("year"));
        
            System.out.println("\nMovies from Spain released after 2014:");
            docs4.forEach(doc -> System.out.println("- " + doc.get("title") + " (" + doc.get("year") + ") - Countries: " + doc.get("countries")));
            System.out.println();

            //List of all movies sorted by year 
            FindIterable<Document> docs5 = moviesCollection.find()
                .projection(projectionFields)
                .sort(Sorts.ascending("year"));
            
            System.out.println("\nList of all movies sorted by year:");
            docs5.forEach(doc -> System.out.println("- " + doc.get("title") + " (" + doc.get("year") + ")"));

            //Shipwrecks in the Geospatial Database
            //Theaters near a specified one (Shipwrecks collection)

        }
    }
}
