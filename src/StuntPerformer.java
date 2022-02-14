import java.util.ArrayList;
import java.util.Arrays;

public class StuntPerformer extends Performer{
    private String height;
    private ArrayList<String> realActors = new ArrayList<String>();

    public StuntPerformer(String id, String name, String surname, String country, String height, ArrayList<String> realActors){
        super(id,name,surname,country);
        setHeight(height);
        setRealActors(realActors);
    }
    public StuntPerformer(){
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return this.height;
    }

    public ArrayList<String> getRealActors() {
        return realActors;
    }

    public void setRealActors(ArrayList<String> realActors) {
        this.realActors = realActors;
    }

    @Override
    public String toString(){
        return super.toString()+"\t"+this.height+"\t"+ this.realActors.toString();
    }
}
