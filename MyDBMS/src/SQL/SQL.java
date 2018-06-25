package SQL;

public abstract class SQL{
    public int type;

    public SQL() {
    }

    public SQL(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public abstract boolean isSQLLegal(String string);
}
