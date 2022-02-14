public class Person {
    private String id;
    private String name;
    private String surname;
    private String country;

    public Person(String id,String name,String surname,String country){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.country=country;
    }

    public Person(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString(){
        return this.id+"\t"+this.name+"\t"+this.surname+"\t"+this.country;
    }
}
