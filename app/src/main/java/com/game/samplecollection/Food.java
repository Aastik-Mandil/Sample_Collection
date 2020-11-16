package com.game.samplecollection;

public class Food {
    private int id;
    private String name;
    //    private String price;
    private byte[] image;
    private String city;
    private String state;
    private String monsoon;
    private String sampleType;
    private String latLon;
    private String ph;
    private String ec;
    private String tds;
    private String arsenic;
    private String nitrate;
    private String fluoride;
    private String sulphate;
    private String chloride;
    private String hardness;
    private String alkalinity;

    public Food(String name, byte[] image, int id, String city, String state, String monsoon, String sampleType, String latLon, String ph, String ece, String tds, String arsenic,
                String nitrate, String fluoride, String sulphate, String chloride, String hardness, String alkalinity) {// String price,
        this.name = name;
//        this.price = price;
        this.image = image;
        this.id = id;
        this.city = city;
        this.state = state;
        this.monsoon = monsoon;
        this.sampleType = sampleType;
        this.latLon = latLon;
        this.ph = ph;
        this.ec = ec;
        this.tds = tds;
        this.sampleType = arsenic;
        this.nitrate = nitrate;
        this.fluoride = fluoride;
        this.sulphate = sulphate;
        this.chloride = chloride;
        this.hardness = hardness;
        this.alkalinity = alkalinity;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getMonsoon() { return monsoon; }

    public void setMonsoon(String monsoon) { this.monsoon = monsoon; }

    public String getSampleType() { return sampleType; }

    public void setSampleType(String sampleType) { this.sampleType = sampleType; }

    public String getLatLon() { return latLon; }

    public void setLatLon(String latLon) { this.latLon = latLon; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public byte[] getImage() { return image; }

    public void setImage(byte[] image) { this.image = image; }

    public String getPh() { return ph; }

    public void setPh(String ph) { this.ph = ph; }

    public String getEc() { return ec; }

    public void setEc(String ec) { this.ec = ec; }

    public String getTds() { return tds; }

    public void setTds(String tds) { this.tds = tds; }

    public String getArsenic() { return arsenic; }

    public void setArsenic(String arsenic) { this.arsenic = arsenic; }

    public String getNitrate() { return nitrate; }

    public void setNitrate(String nitrate) { this.nitrate = nitrate; }

    public String getFluoride() { return fluoride; }

    public void setFluoride(String fluoride) { this.fluoride = fluoride; }

    public String getSulphate() { return sulphate; }

    public void setSulphate(String sulphate) { this.sulphate = sulphate; }

    public String getChloride() { return chloride; }

    public void setChloride(String chloride) { this.chloride = chloride; }

    public String getHardness() { return hardness; }

    public void setHardness(String hardness) { this.hardness = hardness; }

    public String getAlkalinity() { return alkalinity; }

    public void setAlkalinity(String alkalinity) { this.alkalinity = alkalinity; }

}
