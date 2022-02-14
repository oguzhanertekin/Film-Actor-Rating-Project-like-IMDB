import java.io.*;
import java.util.*;

public class Main {
    public static LinkedHashMap<String, Person> peopleList = new LinkedHashMap<String, Person>();
    public static LinkedHashMap<String,Films> filmList=new LinkedHashMap<String,Films>();
    public static LinkedHashMap<String,LinkedHashMap<String, Integer>> ratedList= new LinkedHashMap<String,LinkedHashMap<String,Integer>>();

    public static String ratingScore(String id){   /*This method calculates the film's average rating score out of 10.
                                                     And returns it.*/
        int flag=0;
        for (HashMap.Entry<String, LinkedHashMap<String, Integer>> entry : Main.ratedList.entrySet()){
            if(entry.getValue().containsKey(id))
                flag++;
        }
        double userNumber=flag;
        double ratingScore;

        ratingScore= filmList.get(id).getRateScore()/userNumber;
        String score= Double.toString(ratingScore).replace(".",",").substring(0,3); // Formatting Double with comma
        score = (score.endsWith("0")) ? score.substring(0,1) : score;

        if(flag!=0){
            String result=String.format("Ratings: %s/10 from %d users",score,flag);
            return result;
        }
        else{
            return "0";
        }
    }

    public static void command(String peopleFile,String filmFile,String  commandFile,String output) throws IOException {  /*This method takes 4 arguments.
                                                                                                                           It reads commandFile and executes the operations.
                                                                                                                            And writes those in outputFile*/

        peopleList=Operations.peopleList(peopleFile);  /* People Hashmap. Keys are ID and Values are Person(Class) */
        filmList=Operations.filmList(filmFile);  /* Films Hashmap. Keys are ID and Values are Films(Class) */

        for (HashMap.Entry<String, Person> entry : peopleList.entrySet()){
            ratedList.put(entry.getKey(),new LinkedHashMap<String, Integer>());
        }

        File file = new File(commandFile);
        FileReader fReader = new FileReader(file);  /* Reading commandFile */
        BufferedReader bReader = new BufferedReader(fReader);
        String line;
        ArrayList<String> lineList = new ArrayList<String>();       /* list for Adding every lines */
        while ((line = bReader.readLine()) != null) {
            lineList.add(line);
        }
        bReader.close();

        File outputFile = new File(output);   /* Creating outputFile */
        FileWriter fWriter = new FileWriter(outputFile, false);
        BufferedWriter bWriter = new BufferedWriter(fWriter);

        for(String command:lineList){    /* Reading commandFile line by line*/
            bWriter.write(command+"\n\n");
            if(command.startsWith("RATE")){
                String userID = command.split("\t")[1];
                String filmID = command.split("\t")[2];
                int ratingPoint = Integer.parseInt(command.split("\t")[3]);


                if((peopleList.containsKey(userID) && peopleList.get(userID) instanceof User) && filmList.containsKey(filmID)) {
                    if (!(ratedList.get(userID).containsKey(filmID))) {

                        filmList.get(filmID).setRateScore(ratingPoint);
                        bWriter.write("Film rated successfully\n");
                        bWriter.write("Film type: "+filmList.get(filmID).getClass().getName()+"\n");
                        bWriter.write("Film title: "+filmList.get(filmID).getTitle()+"\n");
                        (ratedList.get(userID)).put(filmID,ratingPoint);

                    }
                    else{
                        bWriter.write("This film was earlier rated\n");}

                }else{
                    bWriter.write("Command Failed\n");
                    bWriter.write("User ID: "+userID+"\n");
                    bWriter.write("Film ID: "+filmID+"\n");
                }
            }

            else if(command.startsWith("ADD\tFEATUREFILM")){
                /* Spliting the command text and creating featureFilms with every splitted part*/
                String filmID = command.split("\t")[2];
                String title= command.split("\t")[3];
                String language= command.split("\t")[4];
                ArrayList<String> directors= new ArrayList<String>();    /* directors list of featureFilm  */
                String runtime=  command.split("\t")[6];
                String country= command.split("\t")[7];
                ArrayList<String> performers= new ArrayList<String>();  /* performers list of featureFilm */
                ArrayList<String> genres= new ArrayList<String>();    /* genres list of featureFilm */
                String releaseDate= command.split("\t")[10];
                ArrayList<String> writers= new ArrayList<String>(); /* writers list of featureFilm */
                String budget= command.split("\t")[12];

                for(String id: command.split("\t")[5].split(",")){
                    directors.add(id);
                }
                for(String id: command.split("\t")[8].split(",")){
                    performers.add(id);
                }
                for(String genre: command.split("\t")[9].split(",")){
                    genres.add(genre);
                }
                for(String id: command.split("\t")[11].split(",")){
                    writers.add(id);
                }

                FeatureFilm film=new FeatureFilm(filmID,title,language,directors,runtime, country,
                                                performers,genres,releaseDate,writers,budget);

                if (!filmList.containsKey(filmID)){
                    boolean key=true;
                    for(String ID: film.getDirectors()){  /*  Check whether film's directors is valid or invalid*/
                        if(!peopleList.containsKey(ID)){
                            key=false;
                            break; }
                    }
                    for(String ID: film.getPerformers()){  /*  Check whether film's performers is valid or invalid*/
                        if(!peopleList.containsKey(ID)){
                            key=false;
                            break; }
                    }
                    for(String ID: film.getWriters()){  /*  Check whether film's writers is valid or invalid*/
                        if(!peopleList.containsKey(ID)){
                            key=false;
                            break; }
                    }
                    if(key){
                        filmList.put(filmID,film);     /* Adding featureFilm to HashMap*/
                        bWriter.write("FeatureFilm added successfully\n");
                        bWriter.write("Film ID: "+filmID+"\n");
                        bWriter.write("Film title: "+film.getTitle()+"\n"); }

                    else if(!key){
                        bWriter.write("Command Failed\n");
                        bWriter.write("Film ID: "+filmID+"\n");
                        bWriter.write("Film title: "+film.getTitle()+"\n"); }
                }
                else{
                    bWriter.write("Command Failed\n");
                    bWriter.write("Film ID: "+filmID+"\n");
                    bWriter.write("Film title: "+film.getTitle()+"\n");}
            }

            else if(command.contains("VIEWFILM")){
                String filmID=command.split("\t")[1];

                if(filmList.containsKey(filmID)){

                    if(filmList.get(filmID) instanceof FeatureFilm){  /* Runs If filmID is FeatureFilm's id */
                        FeatureFilm film=new FeatureFilm();
                        film= (FeatureFilm) filmList.get(filmID);
                        String title= film.getTitle();
                        String releaseDate= (film.getReleaseDate()).substring(6,10);

                        ArrayList<String> writers=new ArrayList<String>();
                        ArrayList<String> directors=new ArrayList<String>();
                        ArrayList<String> performers=new ArrayList<String>();
                        for(String id: film.getWriters() ){
                            writers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getDirectors()){
                            directors.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getPerformers()){
                            performers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        bWriter.write(String.format("%s (%s)",title,releaseDate)+"\n");
                        bWriter.write(String.join(", ",film.getGenres())+"\n");
                        bWriter.write("Writers: "+String.join(", ",writers)+"\n");
                        bWriter.write("Directors: "+String.join(", ",directors)+"\n");
                        bWriter.write("Stars: "+String.join(", ",performers)+"\n");

                        if(ratingScore(filmID).equals("0")){
                            bWriter.write("Awaiting for votes\n"); }
                        else{
                            bWriter.write(ratingScore(filmID)+"\n"); }
                    }

                    else if(filmList.get(filmID) instanceof ShortFilm){  /* Runs If filmID is ShortFilm's id */
                        ShortFilm film =new ShortFilm();
                        film= (ShortFilm) filmList.get(filmID);
                        String title= film.getTitle();
                        String releaseDate= (film.getReleaseDate()).substring(6,10);

                        ArrayList<String> writers=new ArrayList<String>();
                        ArrayList<String> directors=new ArrayList<String>();
                        ArrayList<String> performers=new ArrayList<String>();
                        for(String id: film.getWriters() ){
                            writers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getDirectors()){
                            directors.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getPerformers()){
                            performers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        bWriter.write(String.format("%s (%s)",title,releaseDate)+"\n");
                        bWriter.write(String.join(", ",film.getGenres())+"\n");
                        bWriter.write("Writers: "+String.join(", ",writers)+"\n");
                        bWriter.write("Directors: "+String.join(", ",directors)+"\n");
                        bWriter.write("Stars: "+String.join(", ",performers)+"\n");

                        if(ratingScore(filmID).equals("0")){
                            bWriter.write("Awaiting for votes\n"); }
                        else{
                            bWriter.write(ratingScore(filmID)+"\n"); }

                    }

                    else if (filmList.get(filmID) instanceof Documentary){  /* Runs If filmID is Documentary's id */
                        Documentary film =new Documentary();
                        film= (Documentary) filmList.get(filmID);
                        String title= film.getTitle();
                        String releaseDate= (film.getReleaseDate()).substring(6,10);
                        ArrayList<String> directors=new ArrayList<String>();
                        ArrayList<String> performers=new ArrayList<String>();

                        for(String id: film.getDirectors()){
                            directors.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getPerformers()){
                            performers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        bWriter.write(String.format("%s (%s)",title,releaseDate)+"\n");
                        bWriter.write("Directors: "+String.join(", ",directors)+"\n");
                        bWriter.write("Stars: "+String.join(", ",performers)+"\n");

                        if(ratingScore(filmID).equals("0")){
                            bWriter.write("Awaiting for votes\n"); }
                        else{
                            bWriter.write(ratingScore(filmID)+"\n"); }

                    }

                    else if (filmList.get(filmID) instanceof TVSeries){  /* Runs If filmID is TVSeries's id */
                        TVSeries film =new TVSeries();
                        film= (TVSeries) filmList.get(filmID);
                        String title= film.getTitle();
                        String startDate= (film.getStartDate()).substring(6,10);
                        String endDate=(film.getEndDate()).substring(6,10);
                        ArrayList<String> directors=new ArrayList<String>();
                        ArrayList<String> performers=new ArrayList<String>();
                        ArrayList<String> writers=new ArrayList<String>();
                        for(String id: film.getWriters() ){
                            writers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getDirectors()){
                            directors.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        for(String id: film.getPerformers()){
                            performers.add(String.format("%s %s",peopleList.get(id).getName(),peopleList.get(id).getSurname()));
                        }
                        bWriter.write(String.format("%s (%s-%s)",title,startDate,endDate)+"\n");
                        bWriter.write(String.format("%s seasons, %s episodes",film.getSeasons(),film.getEpisodes())+"\n");
                        bWriter.write(String.join(", ",film.getGenres())+"\n");
                        bWriter.write("Writers: "+String.join(", ",writers)+"\n");
                        bWriter.write("Directors: "+String.join(", ",directors)+"\n");
                        bWriter.write("Stars: "+String.join(", ",performers)+"\n");

                        if(ratingScore(filmID).equals("0")){
                            bWriter.write("Awaiting for votes\n"); }
                        else{
                            bWriter.write(ratingScore(filmID)+"\n"); }
                    }
                }
                else{
                    bWriter.write("Command Failed\n");
                    bWriter.write("Film ID: "+filmID+"\n");
                }
            }

            else if(command.contains("LIST")){   /* LIST USER ID RATES - COMMAND */
                if(command.contains("RATES")){
                    String userID= command.split("\t")[2];
                    if(!(peopleList.get(userID) instanceof User)){ /* If userID is unvalid, returns Command Failed */
                        bWriter.write("Command Failed\n");
                        bWriter.write("User ID: "+userID+"\n");
                    }
                    else if(ratedList.get(userID).isEmpty()){   /* Runs , If user didn't rate yet */
                        bWriter.write("There is not any ratings so far\n"); }
                    else if(!ratedList.get(userID).isEmpty()){

                        for(String id: ratedList.get(userID).keySet()){  /* Writes every film that rated by user */
                            int ratingScore=ratedList.get(userID).get(id);
                            bWriter.write(String.format("%s: %d",filmList.get(id).getTitle(),ratingScore)+"\n");
                        }
                    }
                }
                else if(command.contains("FILM\tSERIES")){  /* LIST FILM SERIES - COMMAND */
                    int TvseriesCount=0;
                    for(HashMap.Entry<String, Films> entry : filmList.entrySet()){
                        if(entry.getValue() instanceof TVSeries){
                            TvseriesCount++;
                            TVSeries film=new TVSeries();
                            film= (TVSeries) entry.getValue();
                            bWriter.write(String.format("%s (%s-%s)",
                                    film.getTitle(),film.getStartDate().substring(6,10),film.getEndDate().substring(6,10))+"\n");
                            bWriter.write(String.format("%s seasons and %s episodes\n\n",film.getSeasons(),film.getEpisodes()));
                        }
                    }
                    if(TvseriesCount==0){
                        bWriter.write("No result\n");
                    }
                }
                else if(command.contains("FILMS\tBY\tCOUNTRY")){  /* LIST FILMS BY COUNTRY - COMMAND */
                    String country=command.split("\t")[4];
                    int countryCount=0;
                    for(HashMap.Entry<String, Films> entry : filmList.entrySet()){
                        if((entry.getValue().getCountry()).equals(country)){
                            countryCount++;
                            bWriter.write(String.format("Film title: %s",entry.getValue().getTitle())+"\n");
                            bWriter.write(String.format("%s min",entry.getValue().getRuntime())+"\n");
                            bWriter.write(String.format("Language: %s",entry.getValue().getLanguage())+"\n\n");
                        }
                    }
                    if(countryCount==0){
                        bWriter.write("No result\n"); }
                }

                else if(command.contains("FEATUREFILMS\tBEFORE") || command.contains("FEATUREFILMS\tAFTER")){
                    String key=command.split("\t")[2];
                    int year= Integer.parseInt(command.split("\t")[3]);
                    int filmCount=0;
                    for(HashMap.Entry<String, Films> entry : filmList.entrySet()){
                        if(entry.getValue() instanceof FeatureFilm){
                            FeatureFilm film=new FeatureFilm();
                            film= (FeatureFilm) entry.getValue();

                            if(key.equals("BEFORE") && Integer.parseInt(film.getReleaseDate().substring(6,10))<year){
                                filmCount++;
                                bWriter.write(String.format("Film title: %s (%s)",film.getTitle(),film.getReleaseDate().substring(6,10))+"\n");
                                bWriter.write(String.format("%s min",film.getRuntime())+"\n");
                                bWriter.write(String.format("Language: %s",film.getLanguage())+"\n\n"); }

                            else if(key.equals("AFTER") && Integer.parseInt(film.getReleaseDate().substring(6,10))>=year){
                                filmCount++;
                                bWriter.write(String.format("Film title: %s (%s)",film.getTitle(),film.getReleaseDate().substring(6,10))+"\n");
                                bWriter.write(String.format("%s min",film.getRuntime())+"\n");
                                bWriter.write(String.format("Language: %s",film.getLanguage())+"\n\n"); }
                        }
                    }
                    if(filmCount==0){
                        bWriter.write("No result\n");
                    }
                }
                else if(command.contains("FILMS\tBY\tRATE")){    /* LIST FILMS BY RATES - COMMAND */
                    ArrayList<FeatureFilm> featureFilms=new ArrayList<FeatureFilm>();  /* FeatureFilms list */
                    ArrayList<ShortFilm> shortFilms=new ArrayList<ShortFilm>();   /* ShortFims list */
                    ArrayList<Documentary> documentaries= new ArrayList<Documentary>(); /* Documentary list */
                    ArrayList<TVSeries> tvSeries= new ArrayList<TVSeries>();  /* TVSeries list */

                    HashMap<String,ArrayList<String>> films= new HashMap<String,ArrayList<String>>();
                    films.put("featureFilms",new ArrayList<String>());
                    films.put("shortFilms",new ArrayList<String>());
                    films.put("documentaries",new ArrayList<String>());
                    films.put("tvSeries",new ArrayList<String>());
                    String result="";

                    for(HashMap.Entry<String, Films> entry : filmList.entrySet()){
                        if ( entry.getValue() instanceof FeatureFilm){
                            Films film= (FeatureFilm) entry.getValue();
                            if (ratingScore(film.getId()).contains("Ratings")){
                                result=String.format("%s (%s) %s", film.getTitle(),
                                        ((FeatureFilm) film).getReleaseDate().substring(6, 10),
                                        ratingScore(film.getId()));
                                films.get("featureFilms").add(result); }
                            else {
                                result=String.format("%s (%s) Ratings: 0/10 from 0 users",
                                        film.getTitle(), ((FeatureFilm) film).getReleaseDate().substring(6, 10));
                                films.get("featureFilms").add(result); }
                        }

                        else if(entry.getValue() instanceof ShortFilm){
                            Films film= (ShortFilm) entry.getValue();
                            if (ratingScore(film.getId()).contains("Ratings")){
                                result=String.format("%s (%s) %s", film.getTitle(),
                                        ((ShortFilm) film).getReleaseDate().substring(6, 10),
                                        ratingScore(film.getId()));
                                films.get("shortFilms").add(result); }
                            else {
                                result=String.format("%s (%s) Ratings: 0/10 from 0 users",
                                        film.getTitle(), ((ShortFilm) film).getReleaseDate().substring(6, 10));
                                films.get("shortFilms").add(result); }
                        }

                        else if(entry.getValue() instanceof Documentary){
                            Films film= (Documentary) entry.getValue();
                            if (ratingScore(film.getId()).contains("Ratings")){
                                result=String.format("%s (%s) %s", film.getTitle(),
                                        ((Documentary) film).getReleaseDate().substring(6, 10),
                                        ratingScore(film.getId()));
                                films.get("documentaries").add(result); }
                            else {
                                result=String.format("%s (%s) Ratings: 0/10 from 0 users",
                                        film.getTitle(), ((Documentary) film).getReleaseDate().substring(6, 10));
                                films.get("documentaries").add(result);}
                        }

                        else if(entry.getValue() instanceof TVSeries){
                            Films film= (TVSeries) entry.getValue();
                            if (ratingScore(film.getId()).contains("Ratings")){
                                result=String.format("%s (%s-%s) %s",
                                        film.getTitle(), ((TVSeries) film).getStartDate().substring(6, 10),
                                        ((TVSeries) film).getEndDate().substring(6, 10)
                                        , ratingScore(film.getId()));
                                films.get("tvSeries").add(result); }
                            else {
                                result=String.format("%s (%s) Ratings: 0/10 from 0 users",
                                        film.getTitle(), ((TVSeries) film).getStartDate().substring(6, 10),
                                        ((TVSeries) film).getEndDate().substring(6, 10));
                                films.get("tvSeries").add(result); }
                        }
                    }

                    for(HashMap.Entry<String, ArrayList<String>> entry : films.entrySet()){
                        int i=0;
                        while(i<entry.getValue().size()){
                            String rate= entry.getValue().get(i).split(" ")[3].split("/")[0];
                            entry.getValue().set(i,rate+" "+entry.getValue().get(i));
                            i++;

                        }Collections.sort(entry.getValue());
                        Collections.reverse(entry.getValue());
                    }

                    bWriter.write("FeatureFilm:\n");
                    if( films.get("featureFilms").isEmpty())
                        bWriter.write("No result");
                    else {
                        for(String film: films.get("featureFilms")){
                            String end= film.split(" ",2)[1];
                            bWriter.write(end+"\n"); }
                    }

                    bWriter.write("\n");
                    bWriter.write("ShortFilm:\n");
                    if( films.get("shortFilms").isEmpty())
                        bWriter.write("No result");
                    else {
                        for(String film: films.get("shortFilms")){
                            String end= film.split(" ",2)[1];
                            bWriter.write(end+"\n");}
                    }

                    bWriter.write("\n");
                    bWriter.write("Documentary:\n");
                    if( films.get("documentaries").isEmpty())
                        bWriter.write("No result");
                    else {
                        for(String film: films.get("documentaries")){
                            String end= film.split(" ",2)[1];
                            bWriter.write(end+"\n"); }
                    }

                    bWriter.write("\n");
                    bWriter.write("TVSeries:\n");
                    if( films.get("tvSeries").isEmpty())
                        bWriter.write("No result");
                    else {
                        for(String film: films.get("tvSeries")){
                            String end= film.split(" ",2)[1];
                            bWriter.write(end+"\n"); }
                    }


                }
                else if(command.contains("ARTISTS\tFROM")){   /* LIST ARTISTS FROM COUNTRY - COMMAND */
                    String country=  command.split("\t")[3];
                    ArrayList<Director> directors=new ArrayList<Director>();  /* Directors list */
                    ArrayList<Writer> writers=new ArrayList<Writer>();     /* Writers list */
                    ArrayList<Actor> actors= new ArrayList<Actor>();   /* Actors list */
                    ArrayList<ChildActor> childActors= new ArrayList<ChildActor>();  /* childActors list */
                    ArrayList<StuntPerformer> stuntPerformers= new ArrayList<StuntPerformer>();  /* StuntPerformers list */
                    for(HashMap.Entry<String, Person> entry : peopleList.entrySet()){
                        if ( entry.getValue() instanceof Director && entry.getValue().getCountry().equals(country)){
                            directors.add((Director) entry.getValue());
                        }
                        else if(entry.getValue() instanceof Writer && entry.getValue().getCountry().equals(country)){
                            writers.add((Writer) entry.getValue());
                        }
                        else if(entry.getValue() instanceof Actor && entry.getValue().getCountry().equals(country)){
                            actors.add((Actor) entry.getValue());
                        }
                        else if(entry.getValue() instanceof ChildActor && entry.getValue().getCountry().equals(country)){
                            childActors.add((ChildActor) entry.getValue());
                        }
                        else if(entry.getValue() instanceof StuntPerformer && entry.getValue().getCountry().equals(country)){
                            stuntPerformers.add((StuntPerformer) entry.getValue());
                        }
                    }

                    bWriter.write("Directors:\n");
                    if(directors.isEmpty()){
                        bWriter.write("No result\n\n"); }
                    else{
                        for (Director director:directors){
                            bWriter.write(director.getName()+" "+director.getSurname()+" "+director.getAgent()+"\n"); }
                    }


                    bWriter.write("\nWriters:\n");
                    if(writers.isEmpty()){
                        bWriter.write("No result\n\n"); }
                    else{
                        for (Writer writer: writers){
                            bWriter.write(writer.getName()+" "+writer.getSurname()+" "+writer.getType()+"\n"); }
                    }


                    bWriter.write("\nActors:\n");
                    if(actors.isEmpty()){
                        bWriter.write("No result\n\n"); }
                    else{
                        for (Actor actor: actors){
                            bWriter.write(actor.getName()+" "+actor.getSurname()+" "+actor.getHeight()+" cm"+"\n"); }
                    }


                    bWriter.write("\nChildActors:\n");
                    if(childActors.isEmpty()){
                        bWriter.write("No result\n\n"); }
                    else{
                        for (ChildActor cActor: childActors){
                            bWriter.write(cActor.getName()+" "+cActor.getSurname()+" "+cActor.getAge()+"\n"); }
                    }


                    bWriter.write("\nStuntPerformers:\n");
                    if(stuntPerformers.isEmpty()){
                        bWriter.write("No result\n"); }
                    else{
                        for (StuntPerformer sPerformer: stuntPerformers){
                            bWriter.write(sPerformer.getName()+" "+sPerformer.getSurname()+" "+sPerformer.getHeight()+" cm"+"\n"); }
                    }
                }
            }
            else if(command.contains("EDIT\tRATE")){  /* EDIT RATE -COMMAND*/
                String userID=command.split("\t")[2];
                String filmID=command.split("\t")[3];
                int newratingPoint = Integer.parseInt(command.split("\t")[4]);

                if(!peopleList.containsKey(userID) || !filmList.containsKey(filmID) || !ratedList.get(userID).containsKey(filmID)){ /* Checks if the movie has already been rated by the user*/
                    bWriter.write("Command Failed\n");
                    bWriter.write("User ID: "+userID+"\n");
                    bWriter.write("Film ID: "+filmID+"\n");
                }
                else{
                    int newPoint=filmList.get(filmID).getRateScore()-ratedList.get(userID).get(filmID)+newratingPoint; /* New film total rate */
                    filmList.get(filmID).setRateScore(0); /* First set film's rate to zero, then set film's rate to new rate */
                    filmList.get(filmID).setRateScore(newPoint);
                    ratedList.get(userID).put(filmID,newratingPoint);

                    bWriter.write("New ratings done successfully\n");
                    bWriter.write("Film title: "+filmList.get(filmID).getTitle()+"\n");
                    bWriter.write(String.format("Your rating: %d",newratingPoint)+"\n");
                }
            }
            else if(command.contains("REMOVE\tRATE")){  /* REMOVE RATE- COMMAND */
                String userID=command.split("\t")[2];
                String filmID=command.split("\t")[3];

                if(!peopleList.containsKey(userID) || !filmList.containsKey(filmID) || !ratedList.get(userID).containsKey(filmID)){ /* Checks if the movie has already been rated by the user*/
                    bWriter.write("Command Failed\n");
                    bWriter.write("User ID: "+userID+"\n");
                    bWriter.write("Film ID: "+filmID+"\n"); }

                else{
                    int newPoint=filmList.get(filmID).getRateScore()-ratedList.get(userID).get(filmID); /* Removes rate from ratedList and obtains film's new total rate */
                    filmList.get(filmID).setRateScore(0);  /* First set film's rate to zero, then set film's rate to new rate */
                    filmList.get(filmID).setRateScore(newPoint);
                    ratedList.get(userID).remove(filmID);
                    bWriter.write("Your film rating was removed successfully\n");
                    bWriter.write("Film title: "+filmList.get(filmID).getTitle()+"\n"); }
            }
            bWriter.write("\n-----------------------------------------------------------------------------------------------------\n");
        }bWriter.close();
    }

    public static void main(String[] args) throws IOException{
        String peopleFile=args[0];
        String filmFile=args[1];
        String commandFile=args[2];
        String outputFile=args[3];
        command(peopleFile,filmFile,commandFile,outputFile);
    }
}