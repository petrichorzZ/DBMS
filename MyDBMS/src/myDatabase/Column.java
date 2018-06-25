package myDatabase;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//字段
public class Column implements Serializable{
    private String name;
    private String dataType;//int  string  double ....
    private List<Attribute> attributes;

    public Column() {
        name = null;
        dataType = null;
        attributes = new ArrayList<>();
    }

    public Column(String name){
        this();
        this.name = name;
    }
    public Column(String name, String dataType, List<String> datas, List<Attribute> attributes) {
        this.name = name;
        this.dataType = dataType;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }

    public String toString(){
        StringBuffer result = new StringBuffer();
        for(Attribute attribute:attributes){
            result.append(attribute.getName()+"\t");
        }
        return result.toString()+"\n";
    }
}
