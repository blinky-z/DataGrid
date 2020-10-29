package me.progbloom.datagrid.app.data.zookeeper;

/**
 * Иерархия файловой системы Zookeeper.
 */
public final class ZooKeeperHierarchy {

    public static final String ROOT = "/";

    public static final class Synchronization {

        public static final String PATH = ZooKeeperHierarchy.ROOT + "sync/";

        public static final class Counter {

            public static final String PATH = Synchronization.PATH + "counter/";

            public static final String WRITE = PATH + "write";
        }
    }
}
