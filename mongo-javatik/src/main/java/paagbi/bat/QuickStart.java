package paagbi.bat;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class QuickStart {
    public static void main( String[] args ) {

        // Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://user1:user1@cluster0.wcdu5.mongodb.net";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix"); //Erabili nahi den "datubase"-aren izena
            MongoCollection<Document> collection = database.getCollection("movies"); //datu basearen barruan dagoen "collection"-aren izena ("taula"-ren izeana)

            Document doc = collection.find(eq("title", "Back to the Future")).first(); //bilatu "title" eremuan "Back to the Future" den lehenengo dokumentua
                /*
                    * collection.find(eq("title", "Back to the Future")): 
                        "title" eremuan "Back to the Future" duen dokumentu guztiak bilatzen ditu.
                    * .first(): 
                        Bilaketa horretatik lehenengo dokumentua hartzen du.
                */
            System.out.println("A - "+doc.toJson());

            System.out.println("B - " + doc.get("cast"));
            
            // if (doc != null) {
            //     System.out.println(doc.toJson());
            // } else {
            //     System.out.println("No matching documents found.");
            // }
        }
    }
}
