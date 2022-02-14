import java.util.ArrayList;

public class Documentary extends Films {
    private String releaseDate;

    public Documentary(String id,String title,String language,ArrayList<String> directors,
                     String runtime,String country,ArrayList<String> performers,String releaseDate){

        super(id,title,language,directors,runtime,country,performers);
        this.releaseDate=releaseDate; }

    public Documentary(){ }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    @Override
    public String toString(){ return super.toString()+"\t"+this.releaseDate;}
}
