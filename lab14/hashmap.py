"""Implementation of the Map ADT using closed hashing and a probe with 
double hashing.
"""

class HashMap :
  """A closed hashing with a double hashing probe"""

  def __init__( self ):                                     
    """Creates an empty map instance."""

    # Define the size of the table
#    self.SIZE = 23       # a prime number
    self.SIZE = 1277     # a prime number


    # Defines constants to represent the status of each table entry.
    self.UNUSED = None                                 
    self.EMPTY = _MapEntry( None, None )              

    # Create the data storage (empty hash table)
    self._table = [ self.UNUSED for i in range ( self.SIZE ) ]

    # how many items in the table
    self._count = 0
    # set the maximum load factor to be 0.7
    self._loadFactor = 0.7
    # how many collisions so far
    self._collisions = 0
  
      
  def __len__( self ):
    """Returns the number of entries in the map."""
    return self._count

  def _hash1( self, key ):                               
    """The main hash function for mapping keys to table entries."""

    ## Studuents will try different hash function in their lab work
    return key % len(self._table)
    return abs( hash( key ) ) % len( self._table )
    
  def _hash2( self, key, step ):
    """ The second hash function used with double hashing probes."""

    ## Students will try different step functions in their lab work
    return (1 + step) ** 2
    return 1
    return 1 + abs( hash( key ) ) % ( len( self._table ) - 2 )    


  def __contains__( self, key ):
    """ Determines if the map contains the given key."""
    slot, entry , collisions= self._findSlot( key, False )
    # The _findSlot() method returns three values, we use only one here
    return entry is not None
    
  def add( self, key, value ):
    """Adds a new entry to the map if the key does not exist. Otherwise, the
    new value replaces the current value associated with the key.
    """

    adding = True  # whether or not actually adding key/value to the table

    # If the 'key' is already in the map, update its value
    if key in self :
      slot, entry, collisions = self._findSlot( key, not adding )
      self._collisions += collisions
      self._table[ slot ].value = value
      return True     # successful update
    else :
      slot, entry, collisions = self._findSlot( key, adding )
      self._collisions += collisions
      if slot >= 0 :    # found a spot to insert
        self._table[ slot ] = _MapEntry( key, value )
        self._count += 1
        return True     # successful update
      else:
        return False    # failed update, table full
      
  def valueOf( self, key ):
    """ Returns the value associated with the key."""
    slot, entry , collisions = self._findSlot( key, False )
#    assert entry is not None, "Invalid map key for value."
    if entry is None:
      return None
    else:
      return entry.value

  def printStats( self ):
    """Print the number of items in the table and the total
    number of collisions due to insertion."""
    print( 'Entry count : ', self._count )
    print( 'Collision ocunt : ', self._collisions )

  def remove( self, key ):
    """ Removes the entry associated with the key."""

    # If the 'key' is already in the map, update its value
    if key in self :
      slot, entry, collisions = self._findSlot( key, False )
      self._table[ slot ].value = None
      return True     # successful update
    else :
      print("key not in table")
    
  def __iter__( self ):
    """ Returns an iterator for traversing the keys in the map."""
    return _HashIterator( self._table )


  def _findSlot( self, key, forInsert ):         
    """Finds the slot containing the key or where the key can be added.
    forInsert indicates if the search is for an insertion, which locates
    the slot into which the new key can be added.
    """

    # Compute the home slot and the step size.
    slot = self._hash1( key )
  
    # Probe for the key.
    M = len(self._table)
    probCount = 0
    while self._table[ slot ] is not self.UNUSED and probCount < M :
      if (not forInsert) and self._table[ slot ].key == key :
        # we found the right spot
        break
      step = self._hash2( key, probCount )
      slot = (slot + step) % M               
      probCount += 1
    
    if probCount >= M :
      return -1, None, probCount
    else:
      return slot, self._table[ slot ], probCount


# Storage class for holding the key/value pairs.   
class _MapEntry :                       

  def __init__( self, key, value ):
    """Create the entry with key and value """
    self.key = key
    self.value = value 
  
  def __eq__( self, rhs ):
    """Overload __eq__ so items can be compared using '==' sign"""
    if rhs == None:
      return False
    return ( self.key == rhs.key and self.value == rhs.value )


# Hash Iterator
class _HashIterator:

  def __init__( self, theTable ):
    """Create the data set and the count"""
    self._table = theTable
    self._curInd = 0
    ## Students need to complete the constructor
    pass

  def __iter__( self ):
    return self

  def __next__( self ):
    """Return next entry in the hash table """
    while self._curInd != len(self._table) and self._table[self._curInd] == None:
      self._curInd += 1
    if self._curInd == len(self._table):
      raise StopIteration
    else:
        self._curInd += 1
        return self._table[self._curInd - 1]
    ## Students need to complete this function
    pass
