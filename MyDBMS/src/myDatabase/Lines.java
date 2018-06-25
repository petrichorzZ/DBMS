package myDatabase;

import java.util.List;
//存放一个表的实际内容
public class Lines {
    
    private List<Column> lines;

    public Lines() {
    }

    public Lines(List<Column> lines) {
        this.lines = lines;
    }

    public List<Column> getLine() {
        return lines;
    }

    public void setLine(List<Column> lines) {
        this.lines = lines;
    }
}
