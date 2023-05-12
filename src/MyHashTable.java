public class MyHashTable<K, V> {
    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {

    }

    public MyHashTable(int M) {

    }

    private int hash(K key) {
        String strKey = (String) key;
        int sum = 0;
        for (int i = 0; i < strKey.length(); i++) {
            sum += strKey.charAt(i);
        }

        return sum % M;
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> node = new HashNode<K, V>(key, value);

        if (chainArray[index] == null) {
            chainArray[index] = node;
            size++;
        } else {
            HashNode<K, V> currentNode = chainArray[index];
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = node;
            size++;
        }
        if (M / size > 0.7) {
            M *= 2;
            HashNode<K, V>[] newChainArray = new HashNode[M];
            for (int j = 0; j < chainArray.length; j++) {
                newChainArray[j] = chainArray[j];
            }
            chainArray = newChainArray;
        }
    }

    public V get(K key) {
        int index = hash(key);
        if (chainArray[index] == null) {
            return null;
        } else {
            HashNode<K, V> currentNode = chainArray[index];
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }
            while (currentNode != null) {
                if (currentNode.key.equals(key)) {
                    return currentNode.value;
                }
                currentNode = currentNode.next;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        if (chainArray[index] == null) {
            return null;
        } else {
            HashNode<K, V> currentNode = chainArray[index];
            if (currentNode.key.equals(key)) {
                chainArray[index] = currentNode.next;
                size--;
                return currentNode.value;
            } else {
                HashNode<K, V> previousNode = currentNode;
                currentNode = currentNode.next;
                while (currentNode != null) {
                    if (currentNode.key.equals(key)) {
                        previousNode.next = currentNode.next;
                        size--;
                        return currentNode.value;
                    }
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }
                return null;
            }
        }
    }

    public boolean contains(V value) {
        for (int i = 0; i < chainArray.length; i++) {
            if (chainArray[i] == null) {
                continue;
            }
            HashNode<K, V> currentNode = chainArray[i];
            if (currentNode.value.equals(value)) {
                return true;
            } else {
                while (currentNode.next != null) {
                    if (currentNode.value.equals(value)) {
                        return true;
                    }
                    currentNode = currentNode.next;
                }
            }

        }
        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> node = chainArray[i];
            while (node != null) {
                if (node.value.equals(value)) {
                    return node.key;
                }
                node = node.next;
            }
        }
        return null;
    }
}
