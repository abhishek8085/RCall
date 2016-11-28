package com.jdose.rcall.commons.utils;

import java.util.*;

/**
 * Created by abhishek.i on 5/13/2015.
 */
public class FixedHashSet < E > implements Set< E >
{
    private int fixedSize;
    private Deque < E > deque;
    private Set < E > set;


    public FixedHashSet( int fixedSize )
    {
        this.fixedSize = fixedSize;
        deque = new LinkedList();
        set = new HashSet();
    }

    @Override
    public int size()
    {
        return set.size();
    }

    @Override
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    @Override
    public boolean contains( Object o )
    {
        return set.contains( o );
    }

    @Override
    public Iterator<E> iterator()
    {
        return set.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return set.toArray();
    }

    @Override
    public <T> T[] toArray( T[] a )
    {
        return set.toArray( a );
    }

    @Override
    public boolean add( E e )
    {
        if( set.size() > fixedSize )
        {
            set.remove( deque.pollLast() );
        }

        deque.add( e );
        return set.add( e );
    }

    @Override
    public boolean remove( Object o )
    {
        deque.remove( o );
        return set.remove( o );
    }

    @Override
    public boolean containsAll( Collection< ? > c )
    {
        return set.containsAll( c );
    }

    @Override
    public boolean addAll( Collection< ? extends E > c )
    {
        deque.addAll( c );
        return set.addAll( c );
    }

    @Override
    public boolean retainAll( Collection < ? > c )
    {
        deque.retainAll( c );
        return set.retainAll( c );
    }

    @Override
    public boolean removeAll( Collection < ? > c )
    {
        deque.removeAll( c );
        return set.removeAll( c );
    }

    @Override
    public void clear()
    {
        deque.clear();
        set.clear();
    }
}
