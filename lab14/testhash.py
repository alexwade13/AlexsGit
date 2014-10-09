from hashmap import HashMap
import random

"""Test program for hashmap implementation.
First run some small tests to make sure basic operations in hashmap
work correctly. Then run a large test to examine the number of collisions
when different hash and collision resolution methods are used.
"""

# Create a hashmap
myMap = HashMap()

# Keys and values for testing
keys = [ 6, 13, 100, 29, 12, 5, 15, 18, 101, 56 ]
values = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]

# Test insertion
for i in range( len( keys ) ):
    print( 'inserting ', keys[ i] , '/', values[ i]  )
    myMap.add( keys[ i ], values[ i ] )    # (key, value) pair

# Test the iterator
print( 'original map : ' )
for v in myMap:
    print( '[', v.key, '/', v.value, ']', end = ' ' )
print()

# Test search
print( 'value of key 6 should be 0, it is ', myMap.valueOf( 6 ) )
print( 'value of key 56 should be 9, it is ', myMap.valueOf( 56 ) )
print( 'value of key 12 should be 4, it is ', myMap.valueOf( 12 ) )

# Test removal
myMap.remove( 100 )
myMap.remove( 56 )

print( 'map after removing keys "100", "56" : ' )
for v in myMap:
    print( '[', v.key, '/', v.value, ']', end = ' ' )
print()

print( 'value of key 6 should be 0, it is ', myMap.valueOf( 6 ) )
print( 'value of key 12 should be 4, it is ', myMap.valueOf( 12 ) )
print( 'value of key 56 should be "not found", it is ', myMap.valueOf( 56 ) )

# Print stats
myMap.printStats()

# Now a large set of random data to test collisions
key = []
value = []
myMap = HashMap()
for i in range ( 1200 ):     # Generate 1,200 pairs of key and value
    key.append( random.randint( 1, 1000000 ) )
    value.append( i )
    myMap.add( key[ i ], value[ i ] )

# Print stats
print( len( key ), ' key/value pairs generated.' )
myMap.printStats()
