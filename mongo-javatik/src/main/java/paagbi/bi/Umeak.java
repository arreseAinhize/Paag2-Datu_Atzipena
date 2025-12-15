package paagbi.bi;

import org.bson.types.ObjectId;

import scala.collection.immutable.List;

public class Umeak {
    private ObjectId _id;
    private String izena;
    private List<String> opariak;

    public Umeak() {
    }

    public Umeak(String izena, List<String> opariak) {
        this.izena = izena;
        this.opariak = opariak;
    }

    
}
