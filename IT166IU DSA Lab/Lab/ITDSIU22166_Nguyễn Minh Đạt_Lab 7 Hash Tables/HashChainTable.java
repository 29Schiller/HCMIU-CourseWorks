import java.util.LinkedList;
import java.util.List;

class HashChainTable {
    private List<Integer>[] hashArray;
    private int size;
    private int insertProbes = 0;
    private int insertCount = 0;
    private int findProbes = 0;
    private int findCount = 0;

    @SuppressWarnings("unchecked")
    public HashChainTable(int size) {
        this.size = size;
        this.hashArray = new List[size];
        for (int i = 0; i < size; i++) {
            hashArray[i] = new LinkedList<>();
        }
    }

    private int hash(int key) {
        return key % size;
    }

    public void insert(int key) {
        int hashVal = hash(key);
        int probes = hashArray[hashVal].size() + 1;
        hashArray[hashVal].add(key);
        insertProbes += probes;
        insertCount++;
        System.out.println("Key: " + key + ", Hash: " + hashVal + ", Chain Length: " + (probes - 1) + ", Probes: " + probes);
    }

    public boolean find(int key) {
        int hashVal = hash(key);
        int probes = 0;
        for (Integer val : hashArray[hashVal]) {
            probes++;
            if (val == key) break;
        }
        findProbes += probes;
        findCount++;
        System.out.println("Find Key: " + key + ", Hash: " + hashVal + ", Probes: " + probes);
        return probes > 0;
    }

    public double getAverageInsertProbes() {
        return insertCount == 0 ? 0 : (double) insertProbes / insertCount;
    }

    public double getAverageFindProbes() {
        return findCount == 0 ? 0 : (double) findProbes / findCount;
    }
}