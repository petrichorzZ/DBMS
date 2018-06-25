package SQL;

import myDatabase.Attribute;
import myDatabase.Line;
import myDatabase.Table;

import java.util.Arrays;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class SQLInsertColumn extends SQL {
    private String tableName;
    private String values[];
    private String pkColumnName;
    private int  pkColNum;
    public SQLInsertColumn(int SQLType,String string){
        super(SQLType);
        if(isSQLLegal(string)){
            if(userManager.getCurrentDatabase()!=null){
                if(userManager.isTableExist(tableName)){
                    if(userManager.getTable(tableName).getColumns().size()==values.length){
                        Table table  =userManager.getTable(tableName);
                        for(int i=0;i<table.getColumns().size();i++){
                            for(int j=0;j<table.getColumns().get(i).getAttributes().size();j++){
                                Attribute attribute = table.getColumns().get(i).getAttributes().get(j);
                                if(attribute.getName().toLowerCase().equals("primary_key")){
                                    this.pkColumnName = table.getColumns().get(i).getName();
                                    break;
                                }
                            }
                            if(this.pkColumnName!=null){
                                break;
                            }
                        }
                        boolean needInsert = true;
                       if(pkColumnName!=null){
                           pkColNum = Util.getColNum(pkColumnName,tableName);
                           for(int i=0;i<table.getLines().size();i++){
                               Line l= table.getLines().get(i);
                               if(l.getDatas().get(pkColNum).equals(values[pkColNum])){
                                   cacheOutput="添加数据失败，该主键字段已存在";
                                   System.out.println(cacheOutput);
                                   needInsert = false;
                                   break;
                               }
                           }
                       }
                           if(needInsert){
                               Line line = new Line();
                               line.setDatas(Arrays.asList(values));
                               userManager.getTable(tableName).addLine(line);
                               cacheOutput = "添加数据成功";
                               System.out.println("添加数据成功");
                           }


                      //  userManager.getTable(tableName).toString();
                    }else{
                        cacheOutput = "你所输入数据与表中数据不匹配";
                        System.out.println("你所输入数据与表中数据不匹配");
                    }
                }else {
                    cacheOutput = "你所要插入数据的表不存在，无法添加数据";
                    System.out.println("你所要插入数据的表不存在，无法添加数据");
                }
            }else {
                cacheOutput = "你还没有使用数据库，无法向表中插入数据";
                System.out.println("你还没有使用数据库，无法向表中插入数据");
            }
        }else {
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }
    }
    @Override
    public boolean isSQLLegal(String string) {
        int count = Util.CharacterCount(string,' ');
        int i = string.indexOf("(");
        int j = string.lastIndexOf(")");
        if(i<0||j<0||count<3){
            return false;
        }
        this.tableName = Util.getThirdWord(string);
        String subStr = string.substring(i+1,j);
        return DeleteSpace(subStr);
    }

    private boolean DeleteSpace(String string){
        String vals[] = string.split(",");
        values = new String[vals.length];
        for(int i = 0;i<vals.length;i++){
            if(vals[i].trim().equals("")){
                return false;
            }else {
                values[i] = vals[i].trim();
            }
        }
        return true;
    }
}
