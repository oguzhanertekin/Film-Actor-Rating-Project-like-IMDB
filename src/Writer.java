public class Writer extends Artist{
    private String type;

    public Writer(String id, String name, String surname, String country,String type) {
        super(id, name, surname, country);
        this.type=type;
    }
    public Writer() { }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @Override
    public String toString() { return super.toString()+"\t"+this.type; }
}
