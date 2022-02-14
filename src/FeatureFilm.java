import java.util.ArrayList;

public class FeatureFilm extends Films{
    private String releaseDate;
    private String budget;
    private ArrayList<String> writers= new ArrayList<String>();
    private ArrayList<String> genres=new ArrayList<String >();

    public FeatureFilm(String id,String title,String language,ArrayList<String> directors,
                       String runtime,String country,ArrayList<String> performers,ArrayList<String> genres,
                       String releaseDate,ArrayList<String> writers,String budget){
        super(id,title,language,directors,runtime,country,performers);
        this.genres=genres;
        this.releaseDate=releaseDate;
        this.writers=writers;
        this.budget=budget;

    }

    public FeatureFilm(){ }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getBudget() { return budget; }

    public void setBudget(String budget) { this.budget = budget; }

    public ArrayList<String> getWriters() { return writers; }

    public void setWriters(ArrayList<String> writers) { this.writers = writers; }

    public ArrayList<String> getGenres() { return genres; }

    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    @Override
    public String toString(){ return super.toString()+"\t"+this.genres+
            "\t"+this.releaseDate+"\t"+this.writers+"\t"+this.budget;}
}