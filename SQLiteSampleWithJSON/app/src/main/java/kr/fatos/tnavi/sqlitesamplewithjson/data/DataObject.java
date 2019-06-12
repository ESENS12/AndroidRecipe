package kr.fatos.tnavi.sqlitesamplewithjson.data;

public class DataObject {

    public String name;
    public String phone;
    public int no;
    public int id;

    public DataObject() {
    }

    public DataObject(String name, String phone, int no) {
        this.name = name;
        this.phone = phone;
        this.no = no;
    }

    public DataObject(int id, String name, String phone, int no) {
        this.id = id;
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
