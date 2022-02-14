public class ChildActor extends Performer {
    private String age;

    public ChildActor(String id, String name,String surname,String country,String age){
        super(id,name,surname,country);
        this.age=age; }
    public ChildActor(){
    }

    public String getAge() {
        return this.age;
    }

    @Override
    public String toString(){
        return super.toString()+"\t"+this.age;
    }
}