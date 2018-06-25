package myDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//存数据？
public class Line implements Serializable{
    List<String> datas;
    public Line(){
        datas = new ArrayList<>();
    }

    public Line(List<String> datas) {
        this.datas = datas;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public void addData(String data){
        datas.add(data);
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        for(String value:datas){
            stringBuffer.append(value+"   ");
        }

        return stringBuffer.toString();
    }
}
