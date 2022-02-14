import java.util.ArrayList;

public class ShortFilm extends Films {
    private String releaseDate;
    private ArrayList<String> writers= new ArrayList<String>();
    private ArrayList<String> genres=new ArrayList<String>();

    public ShortFilm(String id,String title,String language,ArrayList<String> directors,
                       String runtime,String country,ArrayList<String> performers,ArrayList<String> genres,
                       String releaseDate,ArrayList<String> writers){

        super(id,title,language,directors,runtime,country,performers);
        this.genres=genres;
        this.releaseDate=releaseDate;
        this.writers=writers;

    }

    public ShortFilm(){ }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public ArrayList<String> getWriters() { return writers; }

    public void setWriters(ArrayList<String> writers) { this.writers = writers; }

    public ArrayList<String> getGenres() { return genres; }

    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    @Override
    public String toString(){ return super.toString()+"\t"+this.genres+
            "\t"+this.releaseDate+"\t"+this.writers;}

}
