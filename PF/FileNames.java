package PF;

public enum FileNames {
    FARMRECORD("data\\FarmRecords.txt"),
    USERINFO("data\\UsersInfo.txt"),
    INVENTORY("data\\Inventory.txt");

    private final String path;
    FileNames(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
}
