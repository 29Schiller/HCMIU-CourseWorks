class HashDoubleApp {
    public static void main(String[] args) {
        int size = 13;
        int[] keys = {18, 41, 22, 44, 59, 32, 31, 73};

        HashDoubleTable table = new HashDoubleTable(size);
        System.out.println("Initial key sequence:");
        for (int key : keys) {
            System.out.print(key + " ");
        }
        System.out.println("\n\nInsertions:");
        for (int key : keys) {
            table.insert(key);
        }

        System.out.println("\nAverage Insert Probe Length: " + table.getAverageInsertProbes());

        System.out.println("\nFind operations:");
        for (int key : keys) {
            table.find(key);
        }
        System.out.println("\nAverage Find Probe Length: " + table.getAverageFindProbes());
    }
}

