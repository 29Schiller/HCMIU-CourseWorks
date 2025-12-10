class HashDoubleTable {
    private Integer[] hashArray;
    private int size;
    private int insertProbes = 0;
    private int insertCount = 0;
    private int findProbes = 0;
    private int findCount = 0;

    public HashDoubleTable(int size) {
        this.size = size;
        this.hashArray = new Integer[size];
    }

    private int hash1(int key) {
        return key % size;
    }

    private int hash2(int key) {
        return 5 - key % 5;
    }

    public void insert(int key) {
        int hashVal = hash1(key);
        int stepSize = hash2(key);
        int probes = 1;
        while (hashArray[hashVal] != null) {
            hashVal = (hashVal + stepSize) % size;
            probes++;
        }
        hashArray[hashVal] = key;
        insertProbes += probes;
        insertCount++;
        System.out.println("Key: " + key + ", Hash: " + hash1(key) + ", Step: " + stepSize + ", Probes: " + probes);
    }

    public boolean find(int key) {
        int hashVal = hash1(key);
        int stepSize = hash2(key);
        int probes = 1;
        while (hashArray[hashVal] != null && !hashArray[hashVal].equals(key)) {
            hashVal = (hashVal + stepSize) % size;
            probes++;
        }
        findProbes += probes;
        findCount++;
        System.out.println("Find Key: " + key + ", Probes: " + probes);
        return hashArray[hashVal] != null;
    }

    public double getAverageInsertProbes() {
        return insertCount == 0 ? 0 : (double) insertProbes / insertCount;
    }

    public double getAverageFindProbes() {
        return findCount == 0 ? 0 : (double) findProbes / findCount;
    }
}
