package SQL;

import myDatabase.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

@SuppressWarnings("ALL")
public class Util {
    static File file = new File("Datas.txt");
    static File Result = new File("Result.txt");
    public static final String path = file.getPath();
    // public static final String SQLsrc = SQLsrc.getPath();
    //public static final String Result = file.getPath();
    //出去多余空格
    public static String DeleteRepeatSpaces(String text) {
        StringBuffer str = new StringBuffer();
        if(text.contains(";")){
            int i = text.indexOf(';');
            text = text.substring(0,i);
        }
        text = text.replace(';',' ');//清除分号
        text = text.replace('\n',' ');//清楚换行符
        text = text.replace('\t',' ');//清楚水平制表符
        text=text.trim();//去首尾空格
        boolean isSpace = false;//
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)!=' ') {
                isSpace = false;
                str.append(text.charAt(i));
            }else if(!isSpace){
                str.append(text.charAt(i));
                isSpace = true;
            }else{
                continue;
            }
        }
        return str.toString();
    }

    public static int CharacterCount(String string,char c){//计算sql语句中空格数量，判断语句是否合法
        int count = 0;
        for(int i=0;i<string.length();++i){
            if(string.charAt(i)==c){
                count++;
            }
        }
        return count;
    }
    public static int CharacterCount(String string,String str){//计算sql语句中空格数量，判断语句是否合法
        int count = 0;
        while(string.contains(str)){
            count+=1;
            int i=string.indexOf(str);
            string = string.substring(i+str.length(),string.length());
        }
        return count;
    }
    public static String[] keyExtract(String string){//提取单词
        String []keys = string.split(" ");
        return keys;
    }
    //判断sql语句类型
    public static void ParseSQL(String string){
        string = DeleteRepeatSpaces(string);
        String firstWord = getFirstWord(string);
        String secondWord = getSecondWord(string);
        String thirdWord = getThirdWord(string);
        SQL sql = null;
        if(firstWord.equalsIgnoreCase("create")&&secondWord.equalsIgnoreCase("database")){
             sql = new SQLCreateDatabase(ConstantValues.CREATE_DATABASE,string);
        }else if(firstWord.equalsIgnoreCase("create")&&secondWord.equalsIgnoreCase("table")){
             sql = new SQLCreateTable(ConstantValues.CREATE_TABLE,string);
        }else if(firstWord.equalsIgnoreCase("use")&&secondWord.equalsIgnoreCase("database")){
             sql = new SQLUseDatabase(ConstantValues.USE_DATABASE,string);
        }else if(firstWord.equalsIgnoreCase("use")&&secondWord.equalsIgnoreCase("table")){
             sql = new SQLUseTable(ConstantValues.USE_TABLE,string);
        }else if(firstWord.equalsIgnoreCase("show")&&secondWord.equalsIgnoreCase("databases")){
             sql = new SQLShowDatabases(ConstantValues.SHOW_DATABASES,string);
        }else if(firstWord.equalsIgnoreCase("show")&&secondWord.equalsIgnoreCase("tables")){
             sql = new SQLShowTables(ConstantValues.SHOW_TABLES,string);
        }else if(firstWord.equalsIgnoreCase("drop")&&secondWord.equalsIgnoreCase("database")){
             sql = new SQLDropDatabase(ConstantValues.DROP_DATABASE,string);
        }else if(firstWord.equalsIgnoreCase("drop")&&secondWord.equalsIgnoreCase("table")){
             sql = new SQLDropTable(ConstantValues.DROP_TABLE,string);
        }else if(firstWord.equalsIgnoreCase("alter")&&secondWord.equalsIgnoreCase("table")){
            if(getForthWord(string).equalsIgnoreCase("add")&&getFifthWord(string).equalsIgnoreCase("column")){
                 sql = new SQLAlterTableAddColumn(ConstantValues.ALTER_TABLE_ADD_COLUMN,string);
            }else if(getForthWord(string).equalsIgnoreCase("drop")&&getFifthWord(string).equalsIgnoreCase("column")){
                 sql = new SQLAlterTableDropColumn(ConstantValues.ALTER_TABLE_DROP_COLUMN,string);
            }else{
                cacheOutput = "你所输入的SQL语句不合法";
                System.out.println("你所输入的SQL语句不合法");
            }
        }else if(firstWord.equalsIgnoreCase("insert")&&secondWord.equalsIgnoreCase("into")&&Util.getForthWord(string).equalsIgnoreCase("values")){
             sql = new SQLInsertColumn(ConstantValues.INSERT_COLUMN,string);
        }else if(firstWord.equalsIgnoreCase("select")){
            if(secondWord.equalsIgnoreCase("*")){
                if(Util.getThirdWord(string).toLowerCase().equals("from")&&Util.getForthWord(string).toLowerCase().equals("where")){
                    sql = new SQLSelectWhere(ConstantValues.SELECT_WHERE,string);
                }else if(Util.getThirdWord(string).toLowerCase().equals("from")){
                    sql = new SQLSelect(ConstantValues.SELECT,string);
                }else{
                    cacheOutput = "你所输入的SQL语句不合法";
                    System.out.println("你所输入的SQL语句不合法");
                }
            }else{
                String str = string.toLowerCase();
                 if(str.contains("from")&&string.contains("where")){
                     int i = string.indexOf("from");
                     int j = string.indexOf("where");
                     str = string.substring(i+4,j).trim();
                     if(str.contains(",")){
                         sql = new SQLSelectMultiTable(ConstantValues.Select_Multi_Table,string);
                     }else{
                         sql = new SQLSelectColumn(ConstantValues.SELECT_COLUMN,string);
                     }
                 }else if(str.contains("from")){
                     sql = new SQLSelectColumn(ConstantValues.SELECT_COLUMN,string);
                 }else{
                     cacheOutput = "你所输入的SQL语句不合法";
                     System.out.println("你所输入的SQL语句不合法");
                 }
            }
        }else if(firstWord.equalsIgnoreCase("desc")&&secondWord.equalsIgnoreCase("table")){
             sql = new SQLDescTable(ConstantValues.DESC_TABLE,string);
        }
//        else if(firstWord.equalsIgnoreCase("select")&&getSecondWord(string).equalsIgnoreCase("*")&&getFifthWord(string).equalsIgnoreCase("where")) {
//
//        }
        else if(firstWord.equalsIgnoreCase("update")&&getThirdWord(string).equalsIgnoreCase("set")&&string.toLowerCase().contains("where")){
             sql = new SQLUpdate(ConstantValues.UPDATE,string);
        }else if(firstWord.equalsIgnoreCase("serial")){
            Util.Serial();
        }else if(firstWord.equalsIgnoreCase("read")){
            Util.Read();
        }else if(firstWord.equalsIgnoreCase("delete")&&secondWord.equalsIgnoreCase("from")&&Util.getForthWord(string).equalsIgnoreCase("where")){
            sql = new SQLDelete(ConstantValues.Delete,string);
        }
//        else if(firstWord.equalsIgnoreCase("select")&&string.toLowerCase().contains("from")&&string.toLowerCase().contains("where")){
//
//        }
        else{
            cacheOutput = "你所输入的SQL语句不合法";
            System.out.println("你所输入的SQL语句不合法");
        }

    }


    public static String getFirstWord(String str){
        int i = 0;
        String string;
        for(i=0;i<str.length();i++){
            if(str.charAt(i)==' '||str.charAt(i)=='\n'){
                break;
            }
        }
        string = str.substring(0,i);
        return string;
    }

    public static String getSecondWord(String str){
        int i = str.indexOf(" ");
        int j;
        for(j=i+1;j<str.length();j++){
            if(str.charAt(j)==' '||str.charAt(j)=='\n'){
                break;
            }
        }
        String string = str.substring(i+1,j);
        return string;
    }

    public static String getThirdWord(String str){
        int i = str.indexOf(" ");
        int j = str.indexOf(" ",i+1);
        int l;
        for(l = j+1;l<str.length();l++){
            if(str.charAt(l)==' '||str.charAt(l)=='\n'){
                break;
            }
        }
        String string = str.substring(j+1,l);
        return string;
    }

    public static String getForthWord(String str){
        int i = str.indexOf(" ");
        int j = str.indexOf(" ",i+1);
        int k = str.indexOf(" ",j+1);
        int l;
        for(l = k+1;l<str.length();l++){
            if(str.charAt(l)==' '||str.charAt(l)=='\n'||str.charAt(l)=='('){
                break;
            }
        }
        String string = str.substring(k+1,l);
        return string;
    }

    public static String getFifthWord(String str){
        int i = str.indexOf(" ");
        int j = str.indexOf(" ",i+1);
        int k = str.indexOf(" ",j+1);
        int t = str.indexOf(" ",k+1);
        int l;
        for(l = t+1;l<str.length();l++){
            if(str.charAt(l)==' '||str.charAt(l)=='\n'){
                break;
            }
        }
        String string = str.substring(t+1,l);
        return string;
    }

    public static String getLastFirstWord(String string){
        int i = string.lastIndexOf(" ");
        int j;
        for(j = i+1;j<string.length();j++){
            if(string.charAt(j)=='\n'){
                break;
            }
        }
        String str = string.substring(i+1,j);
        return str;
    }

    public static String getLastSecondWord(String string){
        int i = string.lastIndexOf(" ");
        int j = string.lastIndexOf(" ",i-1);
        String str = string.substring(j+1,i);
        return str;
    }

    public static boolean isDigit(char c){
        if(c>='0'&&c<='9')return true;
        return false;
    }

    public static boolean isLetter(char c){
        if(c>='A'&&c<='z')return true;
        return false;
    }

    public static void setColumn(String tableName,String value,int row,int col) {
        Table table = userManager.getTable(tableName);
        if(col<table.getLines().get(row).getDatas().size()){
            table.getLines().get(row).getDatas().set(col,value);
        }else{
            Line line = new Line();
            Line l = table.getLines().get(row);
            for(int i=0;i<l.getDatas().size();i++){
                line.addData(l.getDatas().get(i));
            }
            for(int i=l.getDatas().size();i<=col;i++){
                line.addData(value);
            }
            table.getLines().set(row,line);
        }
    }

    public static List<Integer> getRowNum(String []columnNames,String tableName){
        List<Integer> colNums = new ArrayList<>();
        Table table  = userManager.getTable(tableName);
        for(int i=0;i<columnNames.length;i++){
            for(int j=0;j<table.getColumns().size();j++){
                if(columnNames[i].equals(table.getColumns().get(j).getName())){
                    colNums.add(j);
                }
            }
        }
        return colNums;
    }

    public static int getColNum(String columnName,String tableName){
        Table table = userManager.getTable(tableName);
        for(int i=0;i<table.getColumns().size();i++){
            if(table.getColumns().get(i).getName().equals(columnName)){
                return i;
            }
        }
        return -1;
    }

    public static List getColumnDatas(String columnName,String tableName,int col){
        Table table = userManager.getTable(tableName);
        List columnDatas = new ArrayList();
        for(int i=0;i<table.getLines().size();i++){
            columnDatas.add(table.getLines().get(i).getDatas().get(col)) ;
        }
        return columnDatas;
    }
    public static List<Integer> conditonHandle(String tableName,String columnName,String columnValue,String operator){
        List<Integer> lineNum = new ArrayList<>();
        List<String>columnValues = new ArrayList();
        Table table = userManager.getTable(tableName);
        int k=0;
        for(int i=0;i<table.getColumns().size();i++) {
            if (table.getColumns().get(i).getName().equals(columnName)) {
                k = i;
                break;
            }
        }
        for(int i=0;i<userManager.getTableSize();i++){
            columnValues.add(table.getLines().get(i).getDatas().get(k));
        }

        switch (operator){
            case "=":
                for(int i=0;i<columnValues.size();i++){
                    if(columnValues.get(i).equals(columnValue)){
                        lineNum.add(i);
                    }
                }
                break;
            case "<":
                for(int i=0;i<columnValues.size();i++){
                    if(columnValues.get(i).compareTo(columnValue)<0){
                        lineNum.add(i);
                    }
                }
                break;
            case ">":
                for(int i=0;i<columnValues.size();i++){
                    if(columnValues.get(i).compareTo(columnValue)>0){
                        lineNum.add(i);
                    }
                }
                break;
            case "<=":
                for(int i=0;i<columnValues.size();i++){
                    if(columnValues.get(i).compareTo(columnValue)<=0){
                        lineNum.add(i);
                    }
                }
                break;

            case ">=":
                for(int i=0;i<columnValues.size();i++){
                    if(columnValues.get(i).compareTo(columnValue)>=0){
                        lineNum.add(i);
                    }
                }
                break;

            case "!=":
                for(int i=0;i<columnValues.size();i++){
                    if(!columnValues.get(i).equals(columnValue)){
                        lineNum.add(i);
                    }
                }
                break;
        }
        return lineNum;
    }
//序列化
    public static void Serial(){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObjectOutputStream oStream = null;
        try {
            oStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oStream.writeObject(userManager);
            oStream.flush();
            oStream.close();
            cacheOutput = "序列化成功";
            System.out.println(cacheOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//反序列化
    public static void Read(){
        File file = new File(path);
        try {
            ObjectInputStream iStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            userManager  = (UserManager)iStream.readObject();
            cacheOutput = "读取数据库数据成功";
            System.out.println(cacheOutput);
            iStream.close();
        }catch (EOFException e){

        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //程序化执行
    public static void ProgramMode(){
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("SQLsrc.txt")));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Result)));
            String sql;
            StringBuffer sb = new StringBuffer();
            while((sql=br.readLine())!=null){
                Util.ParseSQL(sql);
                sb.append("\r\n"+sql+"\r\n"+cacheOutput+"\r\n");
                cacheOutput = "";
            }
            bw.write(sb.toString());
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
