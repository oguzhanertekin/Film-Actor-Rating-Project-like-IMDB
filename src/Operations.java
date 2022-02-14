import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Operations {

    public static LinkedHashMap<String, Person> peopleList(String peopleFile) throws IOException {
        LinkedHashMap<String,Person> personList=new LinkedHashMap<String,Person>();  /* This method returns all people list in
                                                                                        hashmap type. Keys are ID (String)
                                                                                        and Values are Person*/
        File file = new File(peopleFile);
        FileReader fReader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(fReader);
        String line;
        ArrayList<String> lineList = new ArrayList<String>();       /* list for Adding every lines */
        while ((line = bReader.readLine()) != null) {
            lineList.add(line);
        }
        bReader.close();

        for (String line2 : lineList) {

            String[] sList = line2.split("\t");
            if(sList[0].contains("Director")){          /*If line contains Director, it creates Director object and add it into hashmap*/
                Director director= new Director(sList[1],sList[2],sList[3],sList[4],sList[5]);
                personList.put(director.getId(),director);
            }

            else if(sList[0].contains("Writer")){       /*If line contains Writer, it creates Writer object and add it into hashmap*/
                Writer writer= new Writer(sList[1],sList[2],sList[3],sList[4],sList[5]);
                personList.put(writer.getId(),writer);
            }

            else if(sList[0].contains("ChildActor")){     /*If line contains ChildActor, it creates ChildActor object and add it into hashmap*/
                ChildActor childActor= new ChildActor(sList[1],sList[2],sList[3],sList[4],sList[5]);
                personList.put(childActor.getId(),childActor);
            }

            else if(sList[0].contains("Actor")){         /*If line contains Actor, it creates Actor object and add it into hashmap*/
                Actor actor= new Actor(sList[1],sList[2],sList[3],sList[4],sList[5]);
                personList.put(actor.getId(),actor);
            }

            else if(sList[0].contains("StuntPerformer")){      /*If line contains StuntPerformer, it creates StuntPerformer object and add it into hashmap*/
                ArrayList<String> realActors = new ArrayList<String>();
                for(String id: sList[6].split(",")){
                    realActors.add(id); }
                StuntPerformer sPerformer= new StuntPerformer(sList[1],sList[2],sList[3],sList[4],sList[5],realActors);
                personList.put(sPerformer.getId(),sPerformer);
            }

            else if(sList[0].contains("User")){        /*If line contains User, it creates User object and add it into hashmap*/
                User user= new User(sList[1],sList[2],sList[3],sList[4]);
                personList.put(user.getId(),user); }
        }
        return personList;
    }
    public static LinkedHashMap<String, Films> filmList(String filmFile) throws IOException{ /* This method returns all films list in
                                                                                              hashmap type. Keys are film ID (String)
                                                                                              and Values are Film*/
        LinkedHashMap<String,Films> filmList=new LinkedHashMap<String,Films>();
        File file = new File(filmFile);
        FileReader fReader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(fReader);
        String line;
        ArrayList<String> lineList = new ArrayList<String>();       /* list for Adding every lines */
        while ((line = bReader.readLine()) != null) {
            lineList.add(line); }
        bReader.close();

        for (String line2 : lineList){
            String[] sList = line2.split("\t");

            if(sList[0].contains("FeatureFilm")){  /*If line contains FeatureFilm, it creates FeatureFilm object and add it into hashmap*/
                ArrayList<String> directors = new ArrayList<String>();
                ArrayList<String> performers = new ArrayList<String>();
                ArrayList<String> genres = new ArrayList<String>();
                ArrayList<String> writers = new ArrayList<String>();
                for(String id: sList[4].split(",")){
                    directors.add(id);
                }
                for(String id: sList[7].split(",")){
                    performers.add(id);
                }
                for(String genre: sList[8].split(",")){
                    genres.add(genre);
                }

                for(String id: sList[10].split(",")){
                    writers.add(id);
                }

                FeatureFilm featureFilm= new FeatureFilm(sList[1],sList[2],sList[3],directors,sList[5],sList[6],
                                                               performers,genres,sList[9],writers,sList[11]);
                filmList.put(featureFilm.getId(),featureFilm);
            }

            else if(sList[0].contains("ShortFilm")){   /*If line contains ShortFilm, it creates ShortFilm object and add it into hashmap*/

                ArrayList<String> directors = new ArrayList<String>();
                ArrayList<String> performers = new ArrayList<String>();
                ArrayList<String> genres = new ArrayList<String>();
                ArrayList<String> writers = new ArrayList<String>();
                for(String id: sList[4].split(",")){
                    directors.add(id);
                }
                for(String id: sList[7].split(",")){
                    performers.add(id);
                }
                for(String genre: sList[8].split(",")){
                    genres.add(genre);
                }

                for(String id: sList[10].split(",")){
                    writers.add(id);
                }
                ShortFilm shortFilm= new ShortFilm(sList[1],sList[2],sList[3],directors,sList[5],sList[6],
                        performers,genres,sList[9],writers);
                if(Integer.parseInt(shortFilm.getRuntime())>40){
                    System.out.println("ERROR: The duration of the short film can't be longer than 40 minutes.");
                    continue;
                }
                filmList.put(shortFilm.getId(),shortFilm);
            }

            else if(sList[0].contains("Documentary")){  /*If line contains Documentray, it creates Documentary object and add it into hashmap*/
                ArrayList<String> directors = new ArrayList<String>();
                ArrayList<String> performers = new ArrayList<String>();

                for(String id: sList[4].split(",")){
                    directors.add(id);
                }
                for(String id: sList[7].split(",")){
                    performers.add(id);
                }
                Documentary documentary= new Documentary(sList[1],sList[2],sList[3],directors,sList[5],sList[6],
                        performers,sList[8]);
                filmList.put(documentary.getId(),documentary);
            }

            else if(sList[0].contains("TVSeries")){   /*If line contains TVSeries, it creates TVSeries object and add it into hashmap*/
                ArrayList<String> directors = new ArrayList<String>();
                ArrayList<String> performers = new ArrayList<String>();
                ArrayList<String> genres = new ArrayList<String>();
                ArrayList<String> writers = new ArrayList<String>();
                for(String id: sList[4].split(",")){
                    directors.add(id);
                }
                for(String id: sList[7].split(",")){
                    performers.add(id);
                }
                for(String genre: sList[8].split(",")){
                    genres.add(genre);
                }

                for(String id: sList[9].split(",")){
                    writers.add(id);
                }

                TVSeries tvSeries= new TVSeries(sList[1],sList[2],sList[3],directors,sList[5],sList[6],
                        performers,genres,writers,sList[10],sList[11],sList[12],sList[13]);
                filmList.put(tvSeries.getId(),tvSeries);
            }
        }
        return filmList;
    }
}