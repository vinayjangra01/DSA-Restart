import java.util.ArrayList;

class Node<K, V>{
    K key;
    V value;
    Node<K, V> next;
    public Node(K key, V val)
    {
        this.key = key;
        this.value = val;
        this.next = null;//it's pointing nowhere till now
    }
}

class HashMap<K, V>{
    //to store values of type V, it needs to store 'Node'
    ArrayList<Node<K, V>> buckets;
    int capacity = 16;
    int size = 0;
    public HashMap()
    {  
        buckets = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            buckets.add(null); // initially, all buckets are empty (null)
        }
    }

    public void put(K key, V val)
    {
        //in which bucket it should go to
        int bucketIndex = Math.abs(key.hashCode() % capacity);

        Node<K, V> target = buckets.get(bucketIndex);
        
        if(target == null)
        {
            Node<K, V> newNode = new Node<>(key, val);
            buckets.set(bucketIndex, newNode);
            size++;
            return;
        }
        while(target != null)
        {
            if(target.key.equals(key))
            {
                target.value = val;
                return;
            }

            if(target.next == null) break; 

            target = target.next;
        }

        //here not found, we are adding new one
        
        //if not found, attach at the end
        target.next = new Node<>(key, val);
        size++;

        //calculate load factor
        double loadFactor = (1.0 * size) / capacity;

        if(loadFactor > 0.75)
        {
            rehash();
        }
    }

    public V get(K key)
    {
        int bucketIndex = Math.abs(key.hashCode() % capacity);

        Node<K, V> curr = buckets.get(bucketIndex);
        
        while(curr != null)
        {
            if(curr.key.equals(key))
            {
                return curr.value;
            }

            curr = curr.next;
        }

        return null;
    }

    //  , , , , 2 3 5 6, 

    public V remove(K key)
    {
        int bucketIndex = Math.abs(key.hashCode() % capacity);

        Node<K, V> curr = buckets.get(bucketIndex);
        Node<K, V> prev = null;

        if(curr == null)
        {
            return null;
        }

        while(curr != null)
        {
            if(curr.key.equals(key))
            {

                if(prev == null)
                {
                    buckets.set(bucketIndex, curr.next);
                }
                else{
                    prev.next = curr.next;
                }

                size--;
                return curr.value;
            }

            prev = curr;
            curr = curr.next;
        }

        return null;
    }

    public boolean containsKey(K key)
    {
        int bucketIndex = Math.abs(key.hashCode() % capacity);

        Node<K, V> curr = buckets.get(bucketIndex);

        while(curr != null)
        {
            if(curr.key.equals(key)) return true;
            curr = curr.next;
        }

        return false;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    private void rehash()
    {
        ArrayList<Node<K, V>> oldBucketList = buckets;
        ArrayList<Node<K, V>> newBucketList = new ArrayList<>();
        size = 0;
        capacity *= 2;

        for(int i = 0; i < capacity; i++)
        {
            newBucketList.add(null);
        }

        buckets = newBucketList;

        for(Node<K, V> node : oldBucketList)
        {

            while(node != null)
            {
                insertWithoutRehash(node.key, node.value);
                node = node.next;
            }
        }
    }

    private void insertWithoutRehash(K key, V val) {
    int bucketIndex = Math.abs(key.hashCode() % capacity);

    Node<K, V> target = buckets.get(bucketIndex);
    if (target == null) {
        buckets.set(bucketIndex, new Node<>(key, val));
        size++;
        return;
    }
    while (target.next != null) {
        target = target.next;
    }
    target.next = new Node<>(key, val);
    size++;
}

}
