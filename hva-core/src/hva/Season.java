package hva;

public enum Season {
    PRIMAVERA(0),
    VERÃO(1),
    OUTONO(2),
    INVERNO(3);

    private int _value;

    Season(int value) {
        _value = value;
    }

    public int getValue(){
        return _value;
    }

    /**
     * Update _value to the next season corresponds value. 
     * @return the new season
     */
    public Season nextSeason() {
        int nextOrdinal=(this.ordinal()+1)%Season.values().length;
        return Season.values()[nextOrdinal];
    }
}
