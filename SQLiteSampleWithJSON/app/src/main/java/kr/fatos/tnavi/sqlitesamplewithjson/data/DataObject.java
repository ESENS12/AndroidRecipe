package kr.fatos.tnavi.sqlitesamplewithjson.data;

public class DataObject {

    private String name;
    private String phone;
    private int no;

    public DataObject() {
    }

    public DataObject(String name, String phone, int no) {
        this.name = name;
        this.phone = phone;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
