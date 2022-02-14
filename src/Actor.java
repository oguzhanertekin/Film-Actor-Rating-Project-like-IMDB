public class Actor extends Performer{
    private String height;

    public Actor(String id, String name,String surname,String country,String height){
        super(id,name,surname,country);
        this.height=height; }

    public Actor(){ }

    public void setHeight(String height) { this.height = height; }

    public String getHeight() {
        return this.height;
    }

    @Override
    public String toString(){
        return super.toString()+"\t"+this.height;
    }
}