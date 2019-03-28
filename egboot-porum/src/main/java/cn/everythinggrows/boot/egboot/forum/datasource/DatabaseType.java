package cn.everythinggrows.boot.egboot.forum.datasource;


/**
 * 数据源
 */
public enum DatabaseType {
    /**
     * 0库
     */
    everythinggrows_0,
    /**
     * 1库
     */
    everythinggrows_1,
    everythinggrows_2,
    everythinggrows_3,
    everythinggrows_4,
    everythinggrows_5,
    everythinggrows_6,
    everythinggrows_7;

    public static DatabaseType getType(int order){
        switch (order){
            case 0:
                return everythinggrows_0;
            case 1:
                return everythinggrows_1;
            case 2:
                return everythinggrows_2;
            case 3:
                return everythinggrows_3;
            case 4:
                return everythinggrows_4;
            case 5:
                return everythinggrows_5;
            case 6:
                return everythinggrows_6;
            case 7:
                return everythinggrows_7;
                default:
                    return everythinggrows_0;
        }
    }
}
