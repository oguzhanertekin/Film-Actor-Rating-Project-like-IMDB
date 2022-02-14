import java.util.ArrayList;

public class TVSeries extends Films{

    private ArrayList<String> writers= new ArrayList<String>();
    private ArrayList<String> genres=new ArrayList<String>();
    private String startDate;
    private String endDate;
    private String seasons;
    private String episodes;

    public TVSeries(String id,String title,String language,ArrayList<String> directors,
                     String runtime,String country,ArrayList<String> performers,ArrayList<String> genres,
                     ArrayList<String> writers,String startDate,String endDate,String seasons,String episodes){

        super(id,title,language,directors,runtime,country,performers);
        this.genres=genres;
        this.writers=writers;
        this.startDate=startDate;
        this.endDate=endDate;
        this.seasons=seasons;
        this.episodes=episodes; }

    public TVSeries(){ }

    public ArrayList<String> getWriters() { return writers; }

    public void setWriters(ArrayList<String> writers) { this.writers = writers; }

    public ArrayList<String> getGenres() { return genres; }

    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getSeasons() { return seasons; }

    public void setSeasons(String seasons) { this.seasons = seasons; }

    public String getEpisodes() { return episodes; }

    public void setEpisodes(String episodes) { this.episodes = episodes; }

    @Override
    public String toString(){ return super.toString()+"\t"+this.genres+
            "\t"+this.writers+"\t"+this.startDate+"\t"+this.endDate+"\t"+this.seasons
            +"\t"+this.episodes;}

}
