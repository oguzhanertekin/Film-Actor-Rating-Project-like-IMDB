import java.util.ArrayList;

public class Films {
    private int rateScore=0;
    private String id;
    private String title;
    private String language;
    private ArrayList<String> directors=new ArrayList<String>();
    private String runtime;
    private String country;
    private ArrayList<String> performers= new ArrayList<String>();

    public Films(String id,String title,String language,ArrayList<String> directors,
                 String runtime,String country,ArrayList<String> performers){
        this.id=id;
        this.title=title;
        this.language=language;
        this.directors=directors;
        this.runtime=runtime;
        this.country=country;
        this.performers=performers; }

    public Films(){ }

    public int getRateScore() { return rateScore; }

    public void setRateScore(int rateScore) {
        if(rateScore==0){
            this.rateScore=0; }
        if (this.rateScore!=0){
            this.rateScore += rateScore; }
        else if(this.rateScore==0){
            this.rateScore=rateScore; }
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public ArrayList<String> getDirectors() { return directors; }

    public void setDirectors(ArrayList<String> directors) { this.directors = directors; }

    public String getRuntime() { return runtime; }

    public void setRuntime(String runtime) { this.runtime = runtime; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public ArrayList<String> getPerformers() { return performers; }

    public void setPerformers(ArrayList<String> performers) { this.performers = performers; }

    public String toString(){ return this.id+"\t"+this.title+"\t"+this.language+"\t"
            +this.directors+"\t"+this.runtime+"\t"+this.country+"\t"+this.performers;}
}

